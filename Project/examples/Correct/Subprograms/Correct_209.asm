   CALL _main
   HALT
_print:
   LDLADDR -1
   LOADB
   BZ L12
   LDCSTR "true"
   PUTSTR 4
   PUTEOL
   BR L13
L12:
   LDCSTR "false"
   PUTSTR 5
   PUTEOL
L13:
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
   BNZ L14
   LDCB 0
   BR L15
L14:
   LDLADDR 9
   LOADB
L15:
   CALL _print
   LDLADDR 8
   LOADB
   BNZ L16
   LDCB 0
   BR L17
L16:
   LDLADDR 9
   LOADB
   NOT
L17:
   CALL _print
   LDLADDR 8
   LOADB
   NOT
   BNZ L18
   LDCB 0
   BR L19
L18:
   LDLADDR 9
   LOADB
   NOT
L19:
   CALL _print
   LDLADDR 8
   LOADB
   BZ L20
   LDCB 1
   BR L21
L20:
   LDLADDR 9
   LOADB
L21:
   CALL _print
   LDLADDR 8
   LOADB
   BZ L22
   LDCB 1
   BR L23
L22:
   LDLADDR 9
   LOADB
   NOT
L23:
   CALL _print
   LDLADDR 8
   LOADB
   NOT
   BZ L24
   LDCB 1
   BR L25
L24:
   LDLADDR 9
   LOADB
   NOT
L25:
   CALL _print
   RET 0
