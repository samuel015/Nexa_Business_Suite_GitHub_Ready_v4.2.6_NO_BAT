param([Parameter(Mandatory=$true)][string]$JarPath)
$ErrorActionPreference='Continue'
Set-Location (Split-Path -Parent $PSScriptRoot)
New-Item -ItemType Directory -Force -Path 'logs' | Out-Null
$log=Join-Path (Get-Location) 'logs\launcher.log'
"`r`n============================================================" | Out-File $log -Append -Encoding utf8
("PRESENTATION JAR LAUNCH " + (Get-Date -Format 'yyyy-MM-dd HH:mm:ss') + " port=" + $env:APP_PORT) | Out-File $log -Append -Encoding utf8
"============================================================" | Out-File $log -Append -Encoding utf8
if(-not $env:JAVA_CMD){"JAVA_CMD missing" | Tee-Object -FilePath $log -Append; exit 1}
& $env:JAVA_CMD -jar $JarPath --spring.profiles.active=mariadb 2>&1 | Tee-Object -FilePath $log -Append
$code=$LASTEXITCODE
("EXIT CODE: " + $code) | Out-File $log -Append -Encoding utf8
exit $code
