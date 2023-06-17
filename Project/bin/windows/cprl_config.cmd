@echo off

rem
rem Configuration settings for the CPRL compiler project.
rem
rem These settings assume an Eclipse workspace with four separate projects named
rem edu.citadel.assembler, edu.citadel.compiler, edu.citadel.cprl, and edu.citadel.cvm.
rem Class files are placed in the Eclipse default "bin" directory.  The project
rem directory hierarchy is as follows:
rem  PROJECT_HOME
rem    - edu.citadel.assembler
rem    - edu.citadel.compiler
rem    - edu.citadel.cprl
rem    - edu.citadel.cvm

rem set PROJECT_HOME to the directory for your compiler project
set PROJECT_HOME=C:\Compilers\workspace

set ASSEMBLER_HOME=%PROJECT_HOME%\edu.citadel.assembler
set COMPILER_HOME=%PROJECT_HOME%\edu.citadel.compiler
set CPRL_HOME=%PROJECT_HOME%\edu.citadel.cprl
set CVM_HOME=%PROJECT_HOME%\edu.citadel.cvm

rem set BIN to the directory name used for compiled Java classes (e.g., bin)
set BIN=bin

rem Add all project-related class directories to COMPILER_PROJECT_PATH.
set COMPILER_PROJECT_PATH=%ASSEMBLER_HOME%\%BIN%;%COMPILER_HOME%\%BIN%;%CPRL_HOME%\%BIN%;%CVM_HOME%\%BIN%
