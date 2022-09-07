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
   BG L166
   LDCB 1
   BR L167
L166:
   LDCB 0
L167:
   PUTBYTE
   RET 0
