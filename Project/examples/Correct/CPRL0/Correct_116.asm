   PROGRAM 4
   LDGADDR 0
   LDCINT 1
   STOREW
   CALL _main
   HALT
_main:
L162:
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   LDGADDR 0
   LOADW
   LDCINT 6
   BE L163
   BR L162
L163:
   LDCSTR "x = "
   PUTSTR 4
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   RET 0
