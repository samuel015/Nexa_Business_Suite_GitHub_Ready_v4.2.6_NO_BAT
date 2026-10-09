param([int]$StartPort=8080,[int]$EndPort=8090)
$ErrorActionPreference='Stop'
for($p=$StartPort;$p -le $EndPort;$p++){
  $listener=$null
  try{
    $listener=[System.Net.Sockets.TcpListener]::new([System.Net.IPAddress]::Loopback,$p)
    $listener.Start()
    $listener.Stop()
    Write-Output $p
    exit 0
  } catch {
    if($listener){try{$listener.Stop()}catch{}}
  }
}
exit 1
