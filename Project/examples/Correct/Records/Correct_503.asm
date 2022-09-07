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
   BGE L62
   LDCB 1
   BR L63
L62:
   LDLADDR -16
   LOADW
   LDLADDR -8
   LOADW
   BE L60
   LDCB 0
   BR L61
L60:
   LDLADDR -16
   LDCINT 4
   ADD
   LOADW
   LDLADDR -8
   LDCINT 4
   ADD
   LOADW
   BGE L58
   LDCB 1
   BR L59
L58:
   LDCB 0
L59:
L61:
L63:
   STOREB
   RET 16
_sort:
   PROC 16
   LDLADDR 8
   LDCINT 1
   STOREW
L64:
   LDLADDR 8
   LOADW
   LDCINT 10
   BGE L65
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
L68:
   LDLADDR 12
   LOADW
   LDCINT 0
   BGE L72
   LDCB 0
   BR L73
L72:
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
L73:
   BZ L69
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
   BR L68
L69:
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
   BR L64
L65:
   RET 4
_writeArray:
   PROC 4
   LDLADDR 8
   LDCINT 0
   STOREW
L74:
   LDLADDR 8
   LOADW
   LDCINT 10
   BGE L75
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
   BR L74
L75:
   RET 4
_main:
   PROC 84
   LDLADDR 8
   LDCINT 0
   STOREW
L78:
   LDLADDR 8
   LOADW
   LDCINT 10
   BGE L79
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
   BR L78
L79:
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