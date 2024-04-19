   CALL _main
   HALT
_p:
   LDLADDR -4
   LOADW
   LOADW
   LDCINT 10
   BGE L2
   RET 4
L2:
   LDLADDR -4
   LOADW
   LDCINT 10
   STOREW
   RET 4
_main:
   PROC 4
   LDLADDR 8
   LDCINT 18
   STOREW
   LDCSTR "x = "
   PUTSTR 4
   LDLADDR 8
   LOADW
   PUTINT
   LDLADDR 8
   CALL _p
   LDCSTR "; after call to p(x), x = "
   PUTSTR 26
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   LDLADDR 8
   LDCINT 7
   STOREW
   LDCSTR "x = "
   PUTSTR 4
   LDLADDR 8
   LOADW
   PUTINT
   LDLADDR 8
   CALL _p
   LDCSTR ";  after call to p(x), x = "
   PUTSTR 27
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   RET 0
