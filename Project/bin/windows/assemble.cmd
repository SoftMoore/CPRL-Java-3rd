@echo off

rem
rem Run CPRL Assembler on one or more ".asm" files.
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

rem The assembler permits the command-line switch -opt:off/-opt:on.

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" edu.citadel.assembler.Assembler %*
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.assembler/edu.citadel.assembler.Assembler %*

rem restore settings
endlocal
