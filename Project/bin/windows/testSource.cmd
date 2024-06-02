@echo off

rem
rem Run CPRL TestSource on the specified file.
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" test.compiler.TestSource %1
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.compiler/test.compiler.TestSource %1

rem restore settings
endlocal
