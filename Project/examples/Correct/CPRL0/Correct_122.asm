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
   BGE L182
   LDCSTR "false < true"
   PUTSTR 12
   PUTEOL
   BR L183
L182:
   LDCSTR "false >= true"
   PUTSTR 13
   PUTEOL
L183:
   LDCB 0
   LDCB 0
   LDCCH 'a'
   LDCB 0
   LDCB 0
   LDCCH 'b'
   BGE L186
   LDCSTR "'a' < 'b'"
   PUTSTR 9
   PUTEOL
   BR L187
L186:
   LDCSTR "'a' >= 'b'"
   PUTSTR 10
   PUTEOL
L187:
   RET 0
