   CALL _main
   HALT
_main:
   LDCSTR "max(51, 3) = "
   PUTSTR 13
   ALLOC 4
   LDCINT 51
   LDCINT 3
   CALL _max
   PUTINT
   PUTEOL
   LDCSTR "max(9, 9) = "
   PUTSTR 12
   ALLOC 4
   LDCINT 9
   LDCINT 9
   CALL _max
   PUTINT
   PUTEOL
   LDCSTR "max(-1, 7) = "
   PUTSTR 13
   ALLOC 4
   LDCINT 1
   NEG
   LDCINT 7
   CALL _max
   PUTINT
   PUTEOL
   LDCSTR "max(-1, -5) = "
   PUTSTR 14
   ALLOC 4
   LDCINT 1
   NEG
   LDCINT 5
   NEG
   CALL _max
   PUTINT
   PUTEOL
   RET 0
_max:
   LDLADDR -8
   LOADW
   LDLADDR -4
   LOADW
   BL L2
   LDLADDR -12
   LDLADDR -8
   LOADW
   STOREW
   RET 8
   BR L3
L2:
   LDLADDR -12
   LDLADDR -4
   LOADW
   STOREW
   RET 8
L3:
