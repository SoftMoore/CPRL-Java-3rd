@echo off

rem
rem Run testCorrect for all ".cprl" files in the current directory.
rem

rem count number of cprl files
set numCprlFiles=0
for %%f in (*.cprl) do set /a numCprlFiles+=1

if %numCprlFiles% == 0 (
   echo no ".cprl" files found in current directory
   exit /b
)

echo ...deleting all ".asm", ".obj", and ".tmp" files
del *.asm  2>nul
del *.obj  2>nul
del *.tmp  2>nul

echo ...recompiling all ".cprl" files
call cprlc *.cprl > nul

echo ...reasembling all ".asm" files
call assemble *.asm > nul
echo.

for %%f in (*.cprl) do (call testCorrect calledFromTestCorrect_all %%~nf)
