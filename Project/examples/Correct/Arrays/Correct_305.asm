   CALL _main
   HALT
_inc:
   LDLADDR -4
   LOADW
   LDLADDR -4
   LOADW
   LOADW
   LDCINT 1
   ADD
   STOREW
   RET 4
_printArray:
   PROC 4
   LDLADDR 8
   LDCINT 0
   STOREW
L0:
   LDLADDR 8
   LOADW
   LDCINT 5
   BGE L1
   LDLADDR -4
   LOADW
   LDLADDR 8
   LOADW
   LDCINT 4
   MUL
   ADD
   LOADW
   PUTINT
   LDCSTR "  "
   PUTSTR 2
   LDLADDR 8
   LDLADDR 8
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L0
L1:
   PUTEOL
   RET 4
_main:
   PROC 24
   LDLADDR 28
   LDCINT 0
   STOREW
L4:
   LDLADDR 28
   LOADW
   LDCINT 5
   BGE L5
   LDLADDR 8
   LDLADDR 28
   LOADW
   LDCINT 4
   MUL
   ADD
   LDCINT 2
   LDLADDR 28
   LOADW
   MUL
   STOREW
   LDLADDR 28
   LDLADDR 28
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L4
L5:
   LDCSTR "initial array:"
   PUTSTR 14
   PUTEOL
   LDLADDR 8
   CALL _printArray
   LDLADDR 28
   LDCINT 0
   STOREW
L8:
   LDLADDR 28
   LOADW
   LDCINT 5
   BGE L9
   LDLADDR 8
   LDLADDR 28
   LOADW
   LDCINT 4
   MUL
   ADD
   CALL _inc
   LDLADDR 28
   LDLADDR 28
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L8
L9:
   LDCSTR "incremented array:"
   PUTSTR 18
   PUTEOL
   LDLADDR 8
   CALL _printArray
   RET 0
