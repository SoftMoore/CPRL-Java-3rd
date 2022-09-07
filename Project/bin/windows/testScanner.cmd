@echo off

rem
rem Run CPRL TestScanner on the specified file
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" test.cprl.TestScanner %1
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.cprl/test.cprl.TestScanner %1

rem restore settings
endlocal
