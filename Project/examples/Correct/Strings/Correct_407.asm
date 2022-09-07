   CALL _main
   HALT
_main:
   PROC 44
   LDLADDR 8
   ALLOC 44
   CALL _readName
   STORE 44
   LDCSTR "Your name is "
   PUTSTR 13
   LDLADDR 8
   LOAD 44
   PUTSTR 20
   LDCSTR "."
   PUTSTR 1
   PUTEOL
   RET 0
_readName:
   PROC 44
   LDCSTR "What is your name?  "
   PUTSTR 20
   LDLADDR 8
   GETSTR 20
   LDCSTR "You entered "
   PUTSTR 12
   LDLADDR 8
   LOAD 44
   PUTSTR 20
   LDCSTR "."
   PUTSTR 1
   PUTEOL
   LDLADDR -44
   LDLADDR 8
   LOAD 44
   STORE 44
   RET 0
