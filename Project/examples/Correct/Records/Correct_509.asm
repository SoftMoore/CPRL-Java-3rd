   CALL _main
   HALT
_main:
   PROC 16
   LDLADDR 8
   LDCINT 5
   STOREW
   LDLADDR 8
   LDCINT 4
   ADD
   LDCINT 2
   STOREW
   LDCSTR "Point("
   PUTSTR 6
   LDLADDR 8
   LOADW
   PUTINT
   LDCSTR ", "
   PUTSTR 2
   LDLADDR 8
   LDCINT 4
   ADD
   LOADW
   PUTINT
   LDCSTR ")"
   PUTSTR 1
   PUTEOL
   LDLADDR 16
   LDLADDR 8
   LOAD 8
   STORE 8
   LDCSTR "Point("
   PUTSTR 6
   LDLADDR 16
   LOADW
   PUTINT
   LDCSTR ", "
   PUTSTR 2
   LDLADDR 16
   LDCINT 4
   ADD
   LOADW
   PUTINT
   LDCSTR ")"
   PUTSTR 1
   PUTEOL
   RET 0