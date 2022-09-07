@echo off

rem
rem Run CPRL compiler on one or more ".cprl" files.
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" edu.citadel.cprl.Compiler %*
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.cprl/edu.citadel.cprl.Compiler %*

rem restore settings
endlocal
