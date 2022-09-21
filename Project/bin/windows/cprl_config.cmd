@echo off

rem
rem Configuration settings for the CPRL compiler project.
rem
rem These settings assume an Eclipse workspace with three separate projects named
rem edu.citadel.compiler, edu.citadel.cprl, and edu.citadel.cvm.  Class files are
rem placed in a "classes" directory rather than the Eclipse default "bin" directory.
rem The project directory hierarchy is as follows:
rem  PROJECT_HOME
rem     - edu.citadel.compiler
rem     - edu.citadel.cprl
rem     - edu.citadel.cvm

rem set PROJECT_HOME to the directory for your compiler project
set PROJECT_HOME=C:\Compilers\workspace

set COMPILER_HOME=%PROJECT_HOME%\edu.citadel.compiler
set CPRL_HOME=%PROJECT_HOME%\edu.citadel.cprl
set CVM_HOME=%PROJECT_HOME%\edu.citadel.cvm

rem set CLASSES_DIR to the directory name used for compiled Java classes (e.g., classes or bin)
set CLASSES_DIR=classes
rem set CLASSES_DIR=bin

rem Add all project-related class directories to COMPILER_PROJECT_PATH.
set COMPILER_PROJECT_PATH=%COMPILER_HOME%\%CLASSES_DIR%;%CPRL_HOME%\%CLASSES_DIR%;%CVM_HOME%\%CLASSES_DIR%
