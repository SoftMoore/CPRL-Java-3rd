   CALL _main
   HALT
_inc:
   LDLADDR -8
   LOADW
   LOADW
   LDLADDR -4
   LOADW
   BGE L10
   LDLADDR -8
   LOADW
   LDLADDR -8
   LOADW
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L11
L10:
   LDCSTR "bounds check failed"
   PUTSTR 19
   PUTEOL
L11:
   RET 8
_main:
   PROC 4
   LDLADDR 8
   LDCINT 3
   STOREW
   LDLADDR 8
   LDCINT 10
   CALL _inc
   LDCSTR "n = "
   PUTSTR 4
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   LDLADDR 8
   LDCINT 13
   STOREW
   LDLADDR 8
   LDCINT 10
   CALL _inc
   LDCSTR "n = "
   PUTSTR 4
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   RET 0
