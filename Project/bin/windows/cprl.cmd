@echo off

rem
rem Run CPRL Virtual Machine interpreter on a single ".obj" file
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" edu.citadel.cvm.CVM %1
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.cvm/edu.citadel.cvm.CVM %1

rem restore settings
endlocal
