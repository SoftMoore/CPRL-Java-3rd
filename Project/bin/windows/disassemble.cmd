@echo off

rem
rem Run CVM disassembler on one or more ".obj" files.
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" edu.citadel.cvm.Disassembler %*
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.cvm/edu.citadel.cvm.Disassembler %*

rem restore settings
endlocal
