   PROGRAM 4
   LDGADDR 0
   LDCINT 1
   STOREW
   CALL _main
   HALT
_main:
L46:
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   LDGADDR 0
   LOADW
   LDCINT 6
   BNE L50
   BR L47
L50:
   BR L46
L47:
   LDCSTR "x = "
   PUTSTR 4
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   RET 0
