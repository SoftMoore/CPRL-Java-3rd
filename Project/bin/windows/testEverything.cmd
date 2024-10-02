@echo off

rem
rem Test all correct and incorrect CPRL examples.
rem

setlocal
call cprl_config.cmd

rem ----------------------------------------
rem test all correct examples
rem ----------------------------------------

pushd %EXAMPLES_HOME%\Correct
for /d %%i in (*) do (
    pushd %cd%\%%i
    echo Testing %cd%\%%i
    call testCorrect_all
    popd
)
popd

rem ----------------------------------------
rem test all incorrect examples
rem ----------------------------------------

pushd %EXAMPLES_HOME%\Incorrect
for /d %%i in (*) do (
    pushd %cd%\%%i
    echo Testing %cd%\%%i
    call testIncorrect_all
    echo;
    popd
)
popd
