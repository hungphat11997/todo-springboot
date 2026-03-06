@echo off
REM Chay file nay bang quyen Administrator (click phai -> Run as administrator)
REM De cai dat va start MySQL Windows service

set "MYSQL_BIN=C:\Program Files\MySQL\MySQL Server 8.0\bin"
set "MYSQL_SERVICE=MySQL80"

echo Checking MySQL...
if not exist "%MYSQL_BIN%\mysqld.exe" (
    echo Khong tim thay MySQL tai %MYSQL_BIN%
    echo Hay cai dat MySQL Server truoc: https://dev.mysql.com/downloads/installer/
    pause
    exit /b 1
)

echo Installing MySQL Windows service (neu chua co)...
"%MYSQL_BIN%\mysqld.exe" --install %MYSQL_SERVICE%
if errorlevel 1 (
    echo Service co the da ton tai. Thu start...
)

echo Starting MySQL service...
net start %MYSQL_SERVICE%
if errorlevel 1 (
    echo.
    echo Neu loi "service name is invalid": service chua duoc cai.
    echo Thu: "%MYSQL_BIN%\mysqld.exe" --install
    echo Sau do: net start MySQL80
    echo.
    echo Neu loi khac, kiem tra Event Viewer hoac file log trong data dir cua MySQL.
) else (
    echo MySQL da start thanh cong.
    echo Co the chay app voi MySQL: mvnw.cmd spring-boot:run
)

pause
