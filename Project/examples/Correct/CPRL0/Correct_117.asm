   PROGRAM 4
   CALL _main
   HALT
_main:
   LDCSTR "Enter value for x: "
   PUTSTR 19
   LDGADDR 0
   GETINT
   LDGADDR 0
   LOADW
   LDCINT 5
   BG L0
   LDCB 1
   BR L1
L0:
   LDCB 0
L1:
   PUTBYTE
   RET 0
