@echo off

rem
rem Test a CPRL program for correct execution.

rem Command-line arguments are as follows:
rem - %0 : the name of this file
rem - %1 : the base name of the file to test without an extension
rem Example: testCorrect Correct_101

if "%~1"=="" (
  echo missing base name of the file to test
  exit /b
)

if %1==calledFromTestCorrect_all (
    shift
) else (
    echo ...deleting files %1.asm, %1.obj, and %1.tmp
    del %1.asm  2>nul
    del %1.obj  2>nul
    del %1.tmp  2>nul

    echo ...recompiling %1.cprl
    call cprlc %1.cprl > nul

    echo ...reasembling %1.asm
    call assemble %1.asm > nul
    echo;
)

echo Testing Program %1

if not exist %1.obj (
    echo Can't find %1.obj
    echo;
    goto :eof
)

if not exist %1.out.txt (
    echo Can't find %1.out.txt
    echo;
    goto :eof
)

if exist %1.in.txt (
    call cprl %1.obj < %1.in.txt > %1.out.tmp
) else (
    call cprl %1.obj > %1.out.tmp
)

rem comparing output files
echo ...comparing files %1.out.tmp and %1.out.txt
fc /n %1.out.tmp %1.out.txt > nul
if errorlevel 1 (echo *** Test Failed ***) else (echo Test Passed)
echo;
