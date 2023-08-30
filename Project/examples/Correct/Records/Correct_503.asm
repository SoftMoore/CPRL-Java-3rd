   CALL _main
   HALT
_writePoint:
   LDCSTR "Point("
   PUTSTR 6
   LDLADDR -8
   LOADW
   PUTINT
   LDCSTR ", "
   PUTSTR 2
   LDLADDR -8
   LDCINT 4
   ADD
   LOADW
   PUTINT
   LDCSTR ")"
   PUTSTR 1
   RET 8
_lessThan:
   LDLADDR -17
   LDLADDR -16
   LOADW
   LDLADDR -8
   LOADW
   BGE L8
   LDCB 1
   BR L9
L8:
   LDLADDR -16
   LOADW
   LDLADDR -8
   LOADW
   BE L6
   LDCB 0
   BR L7
L6:
   LDLADDR -16
   LDCINT 4
   ADD
   LOADW
   LDLADDR -8
   LDCINT 4
   ADD
   LOADW
   BGE L4
   LDCB 1
   BR L5
L4:
   LDCB 0
L5:
L7:
L9:
   STOREB
   RET 16
_sort:
   PROC 16
   LDLADDR 8
   LDCINT 1
   STOREW
L10:
   LDLADDR 8
   LOADW
   LDCINT 10
   BGE L11
   LDLADDR 16
   LDLADDR -4
   LOADW
   LDLADDR 8
   LOADW
   LDCINT 8
   MUL
   ADD
   LOAD 8
   STORE 8
   LDLADDR 12
   LDLADDR 8
   LOADW
   LDCINT 1
   SUB
   STOREW
L14:
   LDLADDR 12
   LOADW
   LDCINT 0
   BGE L18
   LDCB 0
   BR L19
L18:
   ALLOC 1
   LDLADDR 16
   LOAD 8
   LDLADDR -4
   LOADW
   LDLADDR 12
   LOADW
   LDCINT 8
   MUL
   ADD
   LOAD 8
   CALL _lessThan
L19:
   BZ L15
   LDLADDR -4
   LOADW
   LDLADDR 12
   LOADW
   LDCINT 1
   ADD
   LDCINT 8
   MUL
   ADD
   LDLADDR -4
   LOADW
   LDLADDR 12
   LOADW
   LDCINT 8
   MUL
   ADD
   LOAD 8
   STORE 8
   LDLADDR 12
   LDLADDR 12
   LOADW
   LDCINT 1
   SUB
   STOREW
   BR L14
L15:
   LDLADDR -4
   LOADW
   LDLADDR 12
   LOADW
   LDCINT 1
   ADD
   LDCINT 8
   MUL
   ADD
   LDLADDR 16
   LOAD 8
   STORE 8
   LDLADDR 8
   LDLADDR 8
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L10
L11:
   RET 4
_writeArray:
   PROC 4
   LDLADDR 8
   LDCINT 0
   STOREW
L20:
   LDLADDR 8
   LOADW
   LDCINT 10
   BGE L21
   LDCSTR "   "
   PUTSTR 3
   LDLADDR -4
   LOADW
   LDLADDR 8
   LOADW
   LDCINT 8
   MUL
   ADD
   LOAD 8
   CALL _writePoint
   PUTEOL
   LDLADDR 8
   LDLADDR 8
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L20
L21:
   RET 4
_main:
   PROC 84
   LDLADDR 8
   LDCINT 0
   STOREW
L24:
   LDLADDR 8
   LOADW
   LDCINT 10
   BGE L25
   LDLADDR 12
   LDLADDR 8
   LOADW
   LDCINT 8
   MUL
   ADD
   LDCINT 2
   LDLADDR 8
   LOADW
   MUL
   STOREW
   LDLADDR 12
   LDLADDR 8
   LOADW
   LDCINT 8
   MUL
   ADD
   LDCINT 4
   ADD
   LDCINT 5
   STOREW
   LDLADDR 8
   LDLADDR 8
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L24
L25:
   LDLADDR 12
   LDCINT 2
   LDCINT 8
   MUL
   ADD
   LDCINT 99
   STOREW
   LDLADDR 12
   LDCINT 2
   LDCINT 8
   MUL
   ADD
   LDCINT 4
   ADD
   LDCINT 99
   NEG
   STOREW
   LDLADDR 12
   LDCINT 4
   LDCINT 8
   MUL
   ADD
   LDCINT 6
   NEG
   STOREW
   LDLADDR 12
   LDCINT 4
   LDCINT 8
   MUL
   ADD
   LDCINT 4
   ADD
   LDCINT 8
   NEG
   STOREW
   LDLADDR 12
   LDCINT 5
   LDCINT 8
   MUL
   ADD
   LDCINT 4
   ADD
   LDCINT 0
   STOREW
   LDLADDR 12
   LDCINT 7
   LDCINT 8
   MUL
   ADD
   LDCINT 1
   NEG
   STOREW
   LDLADDR 12
   LDCINT 9
   LDCINT 8
   MUL
   ADD
   LDCINT 0
   STOREW
   LDLADDR 12
   LDCINT 9
   LDCINT 8
   MUL
   ADD
   LDCINT 4
   ADD
   LDCINT 3
   NEG
   STOREW
   LDCSTR "initial array:"
   PUTSTR 14
   PUTEOL
   LDLADDR 12
   CALL _writeArray
   LDLADDR 12
   CALL _sort
   LDCSTR "sorted array:"
   PUTSTR 13
   PUTEOL
   LDLADDR 12
   CALL _writeArray
   RET 0
