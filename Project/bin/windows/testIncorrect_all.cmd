@echo off

rem
rem Compile all ".cprl" files in the current directory
rem and compare the results with the expected results.
rem

echo ...deleting "cprlc_all-Results.tmp"
del cprlc_all-Results.tmp  2>nul

echo ...compiling all ".cprl" files
call cprlc *.cprl > cprlc_all-Results.tmp 2>&1

echo ...comparing files cprlc_all-Results.tmp and cprlc_all-Results.txt
fc /n cprlc_all-Results.txt cprlc_all-Results.tmp > nul
if %errorlevel% equ 0 (echo Test Passed) else (echo *** Test Failed ***)
