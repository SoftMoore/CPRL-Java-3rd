   CALL _main
   HALT
_main:
   CALL _reverse
   RET 0
_reverse:
   PROC 2
   LDLADDR 8
   GETCH
   LDCB 0
   LDCB 0
   LDLADDR 8
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'E'
   BE L2
   CALL _reverse
L2:
   LDLADDR 8
   LOAD2B
   PUTCH
   RET 0
