   PROGRAM 4
   CALL _main
   HALT
_main:
L52:
   LDCSTR "Enter an integer (0 to exit): "
   PUTSTR 30
   LDGADDR 0
   GETINT
   LDGADDR 0
   LOADW
   LDCINT 0
   BGE L60
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR " is negative"
   PUTSTR 12
   PUTEOL
   BR L61
L60:
   LDGADDR 0
   LOADW
   LDCINT 0
   BNE L58
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR " is zero"
   PUTSTR 8
   PUTEOL
   BR L59
L58:
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR " is positive"
   PUTSTR 12
   PUTEOL
L59:
L61:
   LDGADDR 0
   LOADW
   LDCINT 0
   BE L53
   BR L52
L53:
   RET 0
