   CALL _main
   HALT
_main:
   PROC 4
L0:
   LDCSTR "Enter an integer: "
   PUTSTR 18
   LDLADDR 8
   GETINT
   LDCSTR "abs("
   PUTSTR 4
   LDLADDR 8
   LOADW
   PUTINT
   LDCSTR ") = "
   PUTSTR 4
   ALLOC 4
   LDLADDR 8
   LOADW
   CALL _abs
   PUTINT
   PUTEOL
   LDLADDR 8
   LOADW
   LDCINT 0
   BE L1
   BR L0
L1:
   RET 0
_abs:
   LDLADDR -4
   LOADW
   LDCINT 0
   BL L6
   LDLADDR -8
   LDLADDR -4
   LOADW
   STOREW
   RET 4
   BR L7
L6:
   LDLADDR -8
   LDLADDR -4
   LOADW
   NEG
   STOREW
   RET 4
L7:
