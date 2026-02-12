@echo off
setlocal

REM Check for administrative privileges
net session >nul 2>&1
if %errorlevel% neq 0 (
    echo Please run this script with administrative privileges.
    pause
    exit /b 1
)

cd /d "%~dp0"

REM Set variables for file names and paths
set JAVA_INSTALLER=JavaSetup8u431.exe
set MARIADB_INSTALLER=mariadb-11.5.2-winx64.msi
set SQL_FILE=resource\database\EditorDBQuery.sql

REM Set the installation directory
set INSTALL_DIR=C:\Program Files\RealEditor
set JAR_NAME=RealEditor.jar

REM Paths for the files
set JAR_PATH=%JAR_NAME%
set CONFIG_PATH=config.properties
set LOGS_PATH=log4j2.xml
set MARIADB_PATH=C:\Program Files\MariaDB 11.5\bin

REM Ensure required files exist
if not exist %JAVA_INSTALLER% (
    echo Error: Java installer '%JAVA_INSTALLER%' not found.
    pause
    exit /b 1
)

if not exist %MARIADB_INSTALLER% (
    echo Error: MariaDB installer '%MARIADB_INSTALLER%' not found.
    pause
    exit /b 1
)

if not exist %SQL_FILE% (
    echo Error: SQL file '%SQL_FILE%' not found.
    pause
    exit /b 1
)

REM Step 1: Install Java
echo Installing Java...
start /wait %JAVA_INSTALLER%
if %errorlevel% neq 0 (
    echo Java installation failed.
    pause
    exit /b 1
)
echo Java installed successfully.

REM Step 2: Install MariaDB
if not exist "%MARIADB_PATH%\mysql.exe" (
    echo Installing MariaDB...
    start /wait %MARIADB_INSTALLER%
    if %errorlevel% neq 0 (
        echo MariaDB installation failed.
        pause
        exit /b 1
    )
    echo MariaDB installed successfully.
) else (
    echo MariaDB is already installed.
)

REM Add MariaDB bin folder to PATH
echo %PATH% | find /i "%MARIADB_PATH%" >nul
if errorlevel 1 (
    echo Adding MariaDB to PATH...
    setx PATH "%PATH%;%MARIADB_PATH%"
    echo MariaDB path added successfully. Please restart Command Prompt to apply changes.
) else (
    echo MariaDB path is already in PATH.
)

REM Step 3: Execute SQL file to create database
echo Executing SQL file to create database...
echo Please enter the MariaDB root password when prompted.
"%MARIADB_PATH%\mysql.exe" -u root -p < %SQL_FILE%
if %errorlevel% neq 0 (
    echo Failed to execute SQL file.
    pause
    exit /b 1
)
echo Database setup completed successfully.

REM Create installation directory
if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"
if %errorlevel% neq 0 (
    echo Failed to create installation directory.
    pause
    exit /b 1
)

REM Copy JAR file
copy /Y "%JAR_PATH%" "%INSTALL_DIR%"
if %errorlevel% neq 0 (
    echo Failed to copy JAR file.
    pause
    exit /b 1
)

REM Copy SQL file
if not exist "%INSTALL_DIR%\database" mkdir "%INSTALL_DIR%\database"
copy /Y "%SQL_FILE%" "%INSTALL_DIR%\database"
if %errorlevel% neq 0 (
    echo Failed to copy SQL file.
    pause
    exit /b 1
)

REM Copy CONFIG file
copy /Y "%CONFIG_PATH%" "%INSTALL_DIR%"
if %errorlevel% neq 0 (
    echo Failed to copy CONFIG file.
    pause
    exit /b 1
)

REM Copy log configuration file
if not exist "%INSTALL_DIR%\resource" mkdir "%INSTALL_DIR%\resource"
if not exist "%INSTALL_DIR%\logs" mkdir "%INSTALL_DIR%\logs"
copy /Y "%LOGS_PATH%" "%INSTALL_DIR%\resource"
if %errorlevel% neq 0 (
    echo Failed to copy log configuration file.
    pause
    exit /b 1
)

REM Inform the user of successful installation
echo Installation completed successfully.
echo Please run the application using: java -jar "%INSTALL_DIR%\%JAR_NAME%"
java -jar "%INSTALL_DIR%\%JAR_NAME%"

pause
endlocal
