param([string]$BaseUrl = "http://127.0.0.1:8080")
$ErrorActionPreference = "Stop"
function Login-Test($Username,$Password,$ExpectedPath) {
  $s = New-Object Microsoft.PowerShell.Commands.WebRequestSession
  $csrf = Invoke-RestMethod -Uri "$BaseUrl/api/auth/csrf" -WebSession $s -Method Get -TimeoutSec 5
  $headers = @{}; $headers[$csrf.headerName] = $csrf.token
  $me = Invoke-RestMethod -Uri "$BaseUrl/api/auth/login" -WebSession $s -Method Post -Headers $headers -Body @{username=$Username;password=$Password} -TimeoutSec 5
  if ($me.redirect -ne $ExpectedPath) { throw "$Username redirect salah: $($me.redirect)" }
  $dash = Invoke-RestMethod -Uri "$BaseUrl/api/dashboard" -WebSession $s -Method Get -TimeoutSec 5
  if ($null -eq $dash.products) { throw "$Username dashboard payload tidak valid" }
  Write-Host "[PASS] $Username -> $ExpectedPath | products=$($dash.products)" -ForegroundColor Green
}
$health=Invoke-RestMethod -Uri "$BaseUrl/actuator/health" -TimeoutSec 5
if($health.status -ne 'UP'){throw 'Health bukan UP'}
Write-Host "[PASS] Health UP" -ForegroundColor Green
Login-Test 'admin' 'admin123' '/admin'
Login-Test 'owner' 'owner123' '/owner'
Login-Test 'manager' 'manager123' '/manager'
Login-Test 'kasir' 'kasir123' '/cashier'
Login-Test 'gudang' 'gudang123' '/warehouse'
Write-Host "`n[SUCCESS] 5 role + dashboard verification PASSED" -ForegroundColor Cyan
