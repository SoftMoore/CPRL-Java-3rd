   PROGRAM 4
   CALL _main
   HALT
_p:
   LDLADDR -4
   LDLADDR -4
   LOADW
   LDCINT 1
   ADD
   STOREW
   LDCSTR "during p:  n = "
   PUTSTR 15
   LDLADDR -4
   LOADW
   PUTINT
   PUTEOL
   RET 4
_main:
   LDGADDR 0
   LDCINT 5
   STOREW
   LDCSTR "before p:  n = "
   PUTSTR 15
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   LDGADDR 0
   LOADW
   CALL _p
   LDCSTR " after p:  n = "
   PUTSTR 15
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   RET 0
