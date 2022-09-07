   CALL _main
   HALT
_main:
   PROC 44
   LDLADDR 8
   LDCSTR "John"
   STORE 12
   LDLADDR 8
   LOAD 44
   PUTSTR 20
   PUTEOL
   LDLADDR 8
   CALL _changeName
   LDLADDR 8
   LOAD 44
   PUTSTR 20
   PUTEOL
   RET 0
_changeName:
   LDLADDR -4
   LOADW
   LDCINT 4
   ADD
   LDCINT 2
   LDCINT 2
   MUL
   ADD
   LDCCH 'a'
   STORE2B
   RET 4
