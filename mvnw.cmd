@echo off
setlocal
set "MAVEN_PROJECTBASEDIR=%~dp0"
set "WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper"
set "MAVEN_ZIP=https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
set "MAVEN_DIR=%WRAPPER_DIR%\apache-maven-3.9.6"

if not exist "%MAVEN_DIR%\bin\mvn.cmd" (
  if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
  powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0.mvn\wrapper\download-maven.ps1"
  if errorlevel 1 (
    echo Failed to download Maven. Please install Maven manually or check your network.
    exit /b 1
  )
)

set "MAVEN_HOME=%MAVEN_DIR%"
set "PATH=%MAVEN_HOME%\bin;%PATH%"
call "%MAVEN_HOME%\bin\mvn.cmd" %*
endlocal
