   CALL _main
   HALT
_main:
   PROC 44
   LDCSTR "What is your name?  "
   PUTSTR 20
   LDLADDR 8
   GETSTR 20
   LDCSTR "Hello, "
   PUTSTR 7
   LDLADDR 8
   LOAD 44
   PUTSTR 20
   LDCSTR "."
   PUTSTR 1
   PUTEOL
   RET 0
