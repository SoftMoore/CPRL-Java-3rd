   PROGRAM 4
   LDGADDR 0
   LDCINT 1
   STOREW
   CALL _main
   HALT
_main:
L12:
   LDGADDR 0
   LOADW
   LDCINT 5
   BG L13
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L12
L13:
   LDCSTR "x = "
   PUTSTR 4
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   RET 0