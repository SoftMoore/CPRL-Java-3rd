@echo off

rem
rem Run CPRL TestParser on one or more ".cprl" files.
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" test.cprl.TestParser %*
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.cprl/test.cprl.TestParser %*

rem restore settings
endlocal
