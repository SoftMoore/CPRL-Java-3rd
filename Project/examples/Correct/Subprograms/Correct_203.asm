   PROGRAM 2
   LDGADDR 0
   LDCCH 'A'
   STORE2B
   CALL _main
   HALT
_main:
   LDGADDR 0
   CALL _p
   LDGADDR 0
   LOAD2B
   PUTCH
   PUTEOL
   RET 0
_p:
   LDLADDR -4
   LOADW
   LOAD2B
   PUTCH
   LDCSTR "   "
   PUTSTR 3
   LDLADDR -4
   LOADW
   LDCCH 'B'
   STORE2B
   LDLADDR -4
   LOADW
   LOAD2B
   PUTCH
   LDCSTR "   "
   PUTSTR 3
   LDLADDR -4
   LOADW
   LDCCH 'C'
   STORE2B
   RET 4
