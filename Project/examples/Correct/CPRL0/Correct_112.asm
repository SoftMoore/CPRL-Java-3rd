   PROGRAM 8
   CALL _main
   HALT
_main:
   LDCB 1
   NOT
   PUTBYTE
   PUTEOL
   LDCB 0
   NOT
   PUTBYTE
   PUTEOL
   LDGADDR 0
   LDCINT 5
   STOREW
   LDGADDR 4
   LDCINT 2
   LDGADDR 0
   LOADW
   MUL
   LDCINT 1
   SUB
   STOREW
L64:
   LDGADDR 0
   LOADW
   LDCINT 2
   LDCINT 5
   MUL
   BG L65
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   LDGADDR 0
   LOADW
   LDCINT 2
   MOD
   LDCINT 0
   BNE L74
   LDCSTR "even"
   PUTSTR 4
   PUTEOL
   BR L75
L74:
   LDGADDR 0
   LOADW
   LDCINT 2
   MOD
   LDCINT 1
   BNE L72
   LDCSTR "odd"
   PUTSTR 3
   PUTEOL
   BR L73
L72:
   LDCSTR "weird"
   PUTSTR 5
   PUTEOL
L73:
L75:
   LDGADDR 0
   LOADW
   LDCINT 9
   BE L65
   BR L64
L65:
   RET 0
