   CALL _main
   HALT
_getName:
   LDLADDR -24
   LDCSTR "Gaby"
   STORE 12
   RET 0
_main:
   PROC 24
   LDLADDR 8
   ALLOC 24
   CALL _getName
   STORE 24
   LDLADDR 8
   LOAD 24
   PUTSTR 10
   PUTEOL
   RET 0
