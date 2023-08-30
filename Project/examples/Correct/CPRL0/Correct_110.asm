   PROGRAM 4
   CALL _main
   HALT
_main:
L0:
   LDCSTR "Enter an integer (0 to exit): "
   PUTSTR 30
   LDGADDR 0
   GETINT
   LDGADDR 0
   LOADW
   LDCINT 0
   BGE L8
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR " is negative"
   PUTSTR 12
   PUTEOL
   BR L9
L8:
   LDGADDR 0
   LOADW
   LDCINT 0
   BNE L6
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR " is zero"
   PUTSTR 8
   PUTEOL
   BR L7
L6:
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR " is positive"
   PUTSTR 12
   PUTEOL
L7:
L9:
   LDGADDR 0
   LOADW
   LDCINT 0
   BE L1
   BR L0
L1:
   RET 0
