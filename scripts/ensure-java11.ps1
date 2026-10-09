param(
    [switch]$NoInstall
)

# Windows PowerShell 5.1 compatible. Do not assign to automatic variables
# such as $HOME, $Host, $PID (PowerShell identifiers are case-insensitive).
$ErrorActionPreference = 'Stop'
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$projectRoot = Split-Path -Parent $PSScriptRoot
$toolsRoot = Join-Path $projectRoot '.tools'
$portableJdkRoot = Join-Path $toolsRoot 'jdk11'
$downloadRoot = Join-Path $toolsRoot 'downloads'
$zipPath = Join-Path $downloadRoot 'temurin-jdk11.zip'
$extractRoot = Join-Path $toolsRoot 'jdk11-extract'

function Write-Info([string]$message) { Write-Output ('[INFO] ' + $message) }
function Write-Warn([string]$message) { Write-Output ('[WARN] ' + $message) }
function Write-Ok([string]$message) { Write-Output ('[OK] ' + $message) }

# java -version writes to STDERR. Invoke it via ProcessStartInfo so
# PowerShell 5.1 cannot mistake native STDERR for a terminating error.
# FileName is passed separately, so paths containing spaces are safe.
function Read-JavaVersion([string]$exePath) {
    try {
        $psi = New-Object System.Diagnostics.ProcessStartInfo
        $psi.FileName = $exePath
        $psi.Arguments = '-version'
        $psi.UseShellExecute = $false
        $psi.CreateNoWindow = $true
        $psi.RedirectStandardError = $true
        $psi.RedirectStandardOutput = $true
        $process = New-Object System.Diagnostics.Process
        $process.StartInfo = $psi
        if (-not $process.Start()) { return $null }
        $stderrText = $process.StandardError.ReadToEnd()
        $stdoutText = $process.StandardOutput.ReadToEnd()
        if (-not $process.WaitForExit(15000)) {
            try { $process.Kill() } catch {}
            return $null
        }
        if ($process.ExitCode -ne 0) { return $null }
        $versionOutput = $stderrText + "`n" + $stdoutText
        if ($versionOutput -match '(?:openjdk|java)\s+version\s+"([0-9][^"]*)"') {
            return $Matches[1]
        }
        return $null
    } catch {
        return $null
    } finally {
        if ($null -ne $process) { $process.Dispose() }
    }
}

function Get-JavaInfo([string]$exePath) {
    if ([string]::IsNullOrWhiteSpace($exePath)) { return $null }
    if (-not (Test-Path -LiteralPath $exePath -PathType Leaf)) { return $null }
    $javaBinDir = Split-Path -Parent $exePath
    $jdkRoot = Split-Path -Parent $javaBinDir
    $compilerExe = Join-Path $jdkRoot 'bin\javac.exe'
    if (-not (Test-Path -LiteralPath $compilerExe -PathType Leaf)) { return $null }
    $version = Read-JavaVersion $exePath
    if (-not $version) { return $null }
    $parts = $version -split '\.'
    $majorVersion = $parts[0]
    if ($majorVersion -eq '1' -and $parts.Count -gt 1) { $majorVersion = $parts[1] }
    if ($majorVersion -ne '11') { return $null }
    return [PSCustomObject]@{
        Java = (Resolve-Path -LiteralPath $exePath).Path
        Home = (Resolve-Path -LiteralPath $jdkRoot).Path
        Version = $version
    }
}

function Add-Candidate([System.Collections.Generic.List[string]]$list, [string]$path) {
    if ([string]::IsNullOrWhiteSpace($path)) { return }
    try { $full = [System.IO.Path]::GetFullPath($path) } catch { $full = $path }
    if (-not $list.Contains($full)) { [void]$list.Add($full) }
}

function Find-Jdk11 {
    $candidates = New-Object 'System.Collections.Generic.List[string]'
    Add-Candidate $candidates (Join-Path $portableJdkRoot 'bin\java.exe')
    if ($env:JAVA_HOME) { Add-Candidate $candidates (Join-Path $env:JAVA_HOME 'bin\java.exe') }
    try {
        $foundCommand = Get-Command java.exe -ErrorAction SilentlyContinue
        if ($foundCommand -and $foundCommand.Source) { Add-Candidate $candidates $foundCommand.Source }
    } catch {}

    $registryPaths = @(
        'HKLM:\SOFTWARE\JavaSoft\JDK',
        'HKLM:\SOFTWARE\JavaSoft\Java Development Kit',
        'HKLM:\SOFTWARE\Eclipse Adoptium\JDK',
        'HKLM:\SOFTWARE\WOW6432Node\JavaSoft\JDK'
    )
    foreach ($registryPath in $registryPaths) {
        if (Test-Path -LiteralPath $registryPath) {
            Get-ChildItem -LiteralPath $registryPath -ErrorAction SilentlyContinue | ForEach-Object {
                try {
                    $properties = Get-ItemProperty -LiteralPath $_.PSPath -ErrorAction SilentlyContinue
                    foreach ($propertyName in @('JavaHome','Path','InstallationPath')) {
                        $pathValue = $properties.$propertyName
                        if ($pathValue) { Add-Candidate $candidates (Join-Path $pathValue 'bin\java.exe') }
                    }
                } catch {}
            }
        }
    }

    $roots = @(
        'C:\Program Files\Eclipse Adoptium',
        'C:\Program Files\Microsoft',
        'C:\Program Files\Java',
        'C:\Program Files\Amazon Corretto',
        'C:\Program Files\BellSoft',
        'C:\Program Files\Zulu',
        'C:\Program Files\Semeru',
        'C:\Program Files (x86)\Java'
    )
    if ($env:LOCALAPPDATA) { $roots += (Join-Path $env:LOCALAPPDATA 'Programs\Eclipse Adoptium') }
    foreach ($root in $roots) {
        if (-not (Test-Path -LiteralPath $root -PathType Container)) { continue }
        try {
            Get-ChildItem -LiteralPath $root -Directory -ErrorAction SilentlyContinue | ForEach-Object {
                Add-Candidate $candidates (Join-Path $_.FullName 'bin\java.exe')
            }
        } catch {}
    }

    foreach ($candidate in $candidates) {
        $javaInfo = Get-JavaInfo $candidate
        if ($javaInfo) { return $javaInfo }
    }
    return $null
}

function Install-PortableJdk11 {
    Write-Warn 'JDK 11 belum ditemukan. Akan mencoba menyiapkan Temurin 11 portable.'
    Write-Info 'JDK disimpan di .tools\jdk11; tidak memerlukan Administrator.'
    Write-Info 'Internet dibutuhkan untuk unduhan pertama.'

    New-Item -ItemType Directory -Force -Path $toolsRoot | Out-Null
    New-Item -ItemType Directory -Force -Path $downloadRoot | Out-Null
    if (Test-Path -LiteralPath $extractRoot) { Remove-Item -LiteralPath $extractRoot -Recurse -Force }
    New-Item -ItemType Directory -Force -Path $extractRoot | Out-Null

    $machineArch = if ($env:PROCESSOR_ARCHITEW6432) { $env:PROCESSOR_ARCHITEW6432 } else { $env:PROCESSOR_ARCHITECTURE }
    $adoptiumArch = if ($machineArch -match 'ARM64') { 'aarch64' } else { 'x64' }
    $downloadUrl = 'https://api.adoptium.net/v3/binary/latest/11/ga/windows/' + $adoptiumArch + '/jdk/hotspot/normal/eclipse'
    if (Test-Path -LiteralPath $zipPath) { Remove-Item -LiteralPath $zipPath -Force }
    Write-Info ('Mengunduh Temurin Java 11 (' + $adoptiumArch + ') ...')

    $downloaded = $false
    try {
        $ProgressPreference = 'SilentlyContinue'
        Invoke-WebRequest -Uri $downloadUrl -OutFile $zipPath -UseBasicParsing -TimeoutSec 240
        if ((Test-Path -LiteralPath $zipPath) -and ((Get-Item -LiteralPath $zipPath).Length -gt 50000000)) {
            $downloaded = $true
        }
    } catch {
        Write-Warn ('Unduhan PowerShell gagal: ' + $_.Exception.Message)
    }
    if (-not $downloaded) {
        $curlExe = Get-Command curl.exe -ErrorAction SilentlyContinue
        if ($curlExe) {
            Write-Info 'Mencoba curl.exe sebagai fallback...'
            & $curlExe.Source -L --fail --retry 2 --connect-timeout 20 --max-time 600 -o $zipPath $downloadUrl
            if (($LASTEXITCODE -eq 0) -and (Test-Path -LiteralPath $zipPath) -and ((Get-Item -LiteralPath $zipPath).Length -gt 50000000)) {
                $downloaded = $true
            }
        }
    }
    if (-not $downloaded) { throw 'Unduh JDK 11 gagal. Periksa koneksi internet, proxy, atau antivirus.' }

    Write-Info 'Mengekstrak JDK portable (dapat memerlukan beberapa menit)...'
    Expand-Archive -LiteralPath $zipPath -DestinationPath $extractRoot -Force
    $exe = Get-ChildItem -LiteralPath $extractRoot -Filter java.exe -File -Recurse -ErrorAction SilentlyContinue |
        Where-Object { $_.FullName -match '\\bin\\java\.exe$' } | Select-Object -First 1
    if (-not $exe) { throw 'Arsip JDK tidak valid: bin\java.exe tidak ditemukan.' }

    $jdkExtractDir = Split-Path -Parent (Split-Path -Parent $exe.FullName)
    if (Test-Path -LiteralPath $portableJdkRoot) { Remove-Item -LiteralPath $portableJdkRoot -Recurse -Force }
    Move-Item -LiteralPath $jdkExtractDir -Destination $portableJdkRoot -Force
    if (Test-Path -LiteralPath $extractRoot) { Remove-Item -LiteralPath $extractRoot -Recurse -Force }
    if (Test-Path -LiteralPath $zipPath) { Remove-Item -LiteralPath $zipPath -Force }

    $installed = Get-JavaInfo (Join-Path $portableJdkRoot 'bin\java.exe')
    if (-not $installed) { throw 'File sudah diekstrak, tetapi validasi JDK 11 gagal.' }
    Write-Ok ('JDK portable valid: ' + $installed.Home)
    return $installed
}

try {
    Write-Info 'Mencari JDK 11 terpasang (termasuk C:\Program Files\Eclipse Adoptium)...'
    $javaInfo = Find-Jdk11
    if (-not $javaInfo) {
        if ($NoInstall) { throw 'JDK 11 tidak ditemukan; mode NoInstall dipakai.' }
        $javaInfo = Install-PortableJdk11
    }
    Write-Ok ('Java versi ' + $javaInfo.Version + ' siap: ' + $javaInfo.Java)
    # Last output is machine-readable for scripts\detect-java.bat.
    Write-Output ('NEXA_JAVA|' + $javaInfo.Java + '|' + $javaInfo.Home + '|' + $javaInfo.Version)
    exit 0
} catch {
    Write-Output ('[ERROR] ' + $_.Exception.Message)
    Write-Output '[INFO] Periksa logs\bootstrap.log atau jalankan TEST-JAVA.bat.'
    exit 1
}
