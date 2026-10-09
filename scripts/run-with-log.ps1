$ErrorActionPreference = 'Continue'
Set-Location (Split-Path -Parent $PSScriptRoot)
New-Item -ItemType Directory -Force -Path 'logs' | Out-Null
$log = Join-Path (Get-Location) 'logs\launcher.log'
"`r`n============================================================" | Out-File -FilePath $log -Append -Encoding utf8
("LAUNCH " + (Get-Date -Format 'yyyy-MM-dd HH:mm:ss') + " profile=" + $env:SPRING_PROFILES_ACTIVE + " port=" + $env:APP_PORT) | Out-File -FilePath $log -Append -Encoding utf8
"============================================================" | Out-File -FilePath $log -Append -Encoding utf8

if (-not $env:MVN_CMD) {
    "MVN_CMD environment variable is missing." | Tee-Object -FilePath $log -Append
    exit 1
}

& $env:MVN_CMD --no-transfer-progress spring-boot:run 2>&1 | Tee-Object -FilePath $log -Append
$code = $LASTEXITCODE
("EXIT CODE: " + $code + " at " + (Get-Date -Format 'yyyy-MM-dd HH:mm:ss')) | Out-File -FilePath $log -Append -Encoding utf8
exit $code
