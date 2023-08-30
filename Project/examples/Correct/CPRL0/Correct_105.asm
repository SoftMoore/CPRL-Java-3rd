   PROGRAM 8
   CALL _main
   HALT
_main:
   LDCSTR "Enter value for x: "
   PUTSTR 19
   LDGADDR 0
   GETINT
   LDCSTR "Enter value for y: "
   PUTSTR 19
   LDGADDR 4
   GETINT
   LDCSTR "x = "
   PUTSTR 4
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   LDCSTR "y = "
   PUTSTR 4
   LDGADDR 4
   LOADW
   PUTINT
   PUTEOL
   LDGADDR 0
   LOADW
   LDGADDR 4
   LOADW
   BG L2
   LDCSTR "x <= y"
   PUTSTR 6
   PUTEOL
   BR L3
L2:
   LDCSTR "x > y"
   PUTSTR 5
   PUTEOL
L3:
   RET 0
