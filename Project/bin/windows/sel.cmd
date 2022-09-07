@echo off

rem
rem Run SEL interpreter
rem

rem set config environment variables locally
setlocal
call cprl_config.cmd

set SEL_HOME=%WORKSPACE_HOME%\edu.citadel.sel

set COMPILER_PROJECT_PATH=%SEL_HOME%\%CLASSES_DIR%;%COMPILER_PROJECT_PATH%

rem use either CLASSPATH or MODULEPATH
rem set CLASSPATH=%COMPILER_PROJECT_PATH%
rem java -ea -cp "%CLASSPATH%" edu.citadel.sel.Interpreter %1
set MODULEPATH=%COMPILER_PROJECT_PATH%
java -ea -p "%MODULEPATH%" -m edu.citadel.sel/edu.citadel.sel.Interpreter %1

rem restore settings
endlocal
