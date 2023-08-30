   CALL _main
   HALT
_main:
   LDCB 0
   LDCB 0
   LDCB 0
   LDCB 0
   LDCB 0
   LDCB 0
   LDCB 0
   LDCB 1
   BGE L2
   LDCSTR "false < true"
   PUTSTR 12
   PUTEOL
   BR L3
L2:
   LDCSTR "false >= true"
   PUTSTR 13
   PUTEOL
L3:
   LDCB 0
   LDCB 0
   LDCCH 'a'
   LDCB 0
   LDCB 0
   LDCCH 'b'
   BGE L6
   LDCSTR "'a' < 'b'"
   PUTSTR 9
   PUTEOL
   BR L7
L6:
   LDCSTR "'a' >= 'b'"
   PUTSTR 10
   PUTEOL
L7:
   RET 0
