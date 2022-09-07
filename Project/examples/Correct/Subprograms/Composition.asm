   CALL _main
   HALT
_main:
   ALLOC 4
   ALLOC 4
   LDCINT 3
   CALL _g
   CALL _f
   PUTINT
   PUTEOL
   ALLOC 4
   ALLOC 4
   LDCINT 3
   CALL _f
   CALL _g
   PUTINT
   PUTEOL
   RET 0
_f:
   LDLADDR -8
   LDCINT 3
   LDLADDR -4
   LOADW
   MUL
   LDCINT 1
   SUB
   STOREW
   RET 4
_g:
   LDLADDR -8
   LDLADDR -4
   LOADW
   LDCINT 4
   ADD
   STOREW
   RET 4
