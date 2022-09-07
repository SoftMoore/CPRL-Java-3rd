   CALL _main
   HALT
_main:
   PROC 44
   LDCSTR "Enter a name: "
   PUTSTR 14
   LDLADDR 8
   GETSTR 20
   LDLADDR 8
   LOAD 44
   CALL _writeName
   RET 0
_writeName:
   LDCSTR "Hello, "
   PUTSTR 7
   LDLADDR -44
   LOAD 44
   PUTSTR 20
   LDCSTR "."
   PUTSTR 1
   RET 44
