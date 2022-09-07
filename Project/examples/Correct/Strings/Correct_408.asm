   CALL _main
   HALT
_main:
   PROC 44
   LDLADDR 8
   LDCSTR "Mary"
   STORE 12
   LDCSTR "length of \"Mary\" = "
   PUTSTR 19
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   LDLADDR 8
   LDCINT 5
   STOREW
   LDLADDR 8
   LDCINT 4
   ADD
   LDCINT 3
   LDCINT 2
   MUL
   ADD
   LDCCH 'i'
   STORE2B
   LDLADDR 8
   LDCINT 4
   ADD
   LDCINT 4
   LDCINT 2
   MUL
   ADD
   LDCCH 'e'
   STORE2B
   LDCSTR "name changed to "
   PUTSTR 16
   LDLADDR 8
   LOAD 44
   PUTSTR 20
   PUTEOL
   RET 0
