@echo off

rem
rem Run testCorrect on all ".obj" files in the current directory.
rem

echo ...deleting all ".asm", ".obj", and ".tmp" files
del *.asm  2>nul
del *.obj  2>nul
del *.tmp  2>nul

echo ...recompiling all ".cprl" files
call cprlc *.cprl > nul

echo ...reasembling all ".asm" files
call assemble *.asm > nul
echo.

for %%f in (*.obj) do (call testCorrect %%~nf)
