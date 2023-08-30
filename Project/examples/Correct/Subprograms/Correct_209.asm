   CALL _main
   HALT
_print:
   LDLADDR -1
   LOADB
   BZ L0
   LDCSTR "true"
   PUTSTR 4
   PUTEOL
   BR L1
L0:
   LDCSTR "false"
   PUTSTR 5
   PUTEOL
L1:
   RET 1
_main:
   PROC 2
   LDLADDR 8
   LDCB 1
   STOREB
   LDLADDR 9
   LDCB 1
   STOREB
   LDLADDR 8
   LOADB
   CALL _print
   LDLADDR 8
   LOADB
   NOT
   CALL _print
   LDLADDR 8
   LOADB
   BNZ L2
   LDCB 0
   BR L3
L2:
   LDLADDR 9
   LOADB
L3:
   CALL _print
   LDLADDR 8
   LOADB
   BNZ L4
   LDCB 0
   BR L5
L4:
   LDLADDR 9
   LOADB
   NOT
L5:
   CALL _print
   LDLADDR 8
   LOADB
   NOT
   BNZ L6
   LDCB 0
   BR L7
L6:
   LDLADDR 9
   LOADB
   NOT
L7:
   CALL _print
   LDLADDR 8
   LOADB
   BZ L8
   LDCB 1
   BR L9
L8:
   LDLADDR 9
   LOADB
L9:
   CALL _print
   LDLADDR 8
   LOADB
   BZ L10
   LDCB 1
   BR L11
L10:
   LDLADDR 9
   LOADB
   NOT
L11:
   CALL _print
   LDLADDR 8
   LOADB
   NOT
   BZ L12
   LDCB 1
   BR L13
L12:
   LDLADDR 9
   LOADB
   NOT
L13:
   CALL _print
   RET 0
