   CALL _main
   HALT
_main:
   PROC 44
   LDLADDR 8
   LDCSTR "John"
   STORE 12
   LDCSTR "Hello, "
   PUTSTR 7
   LDLADDR 8
   LOAD 44
   PUTSTR 20
   LDCSTR "."
   PUTSTR 1
   PUTEOL
   RET 0
