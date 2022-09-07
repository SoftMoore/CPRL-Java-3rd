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
   LDCSTR "x + y = "
   PUTSTR 8
   LDGADDR 0
   LOADW
   LDGADDR 4
   LOADW
   ADD
   PUTINT
   PUTEOL
   RET 0
