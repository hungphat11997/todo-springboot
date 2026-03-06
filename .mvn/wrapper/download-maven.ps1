$ErrorActionPreference = 'Stop'
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$ProgressPreference = 'SilentlyContinue'
$zipUrl = 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip'
$wrapperDir = Join-Path $PSScriptRoot '.'
$zipPath = Join-Path $wrapperDir 'maven.zip'
Write-Host 'Downloading Maven 3.9.6...'
Invoke-WebRequest -Uri $zipUrl -OutFile $zipPath -UseBasicParsing
Expand-Archive -Path $zipPath -DestinationPath $wrapperDir -Force
Remove-Item $zipPath
