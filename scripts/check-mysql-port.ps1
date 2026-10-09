param(
  [string]$DbHost = '127.0.0.1',
  [int]$DbPort = 3306
)
$ErrorActionPreference='SilentlyContinue'
$c = New-Object System.Net.Sockets.TcpClient
try {
  $a = $c.BeginConnect($DbHost,$DbPort,$null,$null)
  if(-not $a.AsyncWaitHandle.WaitOne(1500)){ exit 1 }
  $c.EndConnect($a)
  exit 0
} catch { exit 1 } finally { try{$c.Close()}catch{} }
