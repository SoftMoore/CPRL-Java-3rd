   CALL _main
   HALT
_f:
   LDLADDR -4
   LDCINT 42
   STOREW
   RET 0
_main:
   ALLOC 4
   CALL _f
   PUTINT
   PUTEOL
   RET 0
