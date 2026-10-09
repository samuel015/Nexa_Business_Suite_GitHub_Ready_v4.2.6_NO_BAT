# Backward-compatible java detection, no download.
# The only detection implementation is ensure-java11.ps1.
& (Join-Path $PSScriptRoot 'ensure-java11.ps1') -NoInstall
exit $LASTEXITCODE
