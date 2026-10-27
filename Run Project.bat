@echo off
cd /d "%~dp0"
if not exist out mkdir out
javac -d out src\ConcurrentBankingTransactionSystem.java
if errorlevel 1 (
    echo.
    echo Compilation failed. Make sure a Java JDK is installed and on PATH.
    pause
    exit /b 1
)
echo.
java -cp out ConcurrentBankingTransactionSystem
echo.
pause
