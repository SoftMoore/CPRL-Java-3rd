   PROGRAM 10
   CALL _main
   HALT
_main:
   LDCSTR "Enter value for n1:  "
   PUTSTR 21
   LDGADDR 2
   GETINT
   LDCSTR "Enter value for n2:  "
   PUTSTR 21
   LDGADDR 6
   GETINT
   LDCSTR "n1 = "
   PUTSTR 5
   LDGADDR 2
   LOADW
   PUTINT
   LDCSTR ";  n2 = "
   PUTSTR 8
   LDGADDR 6
   LOADW
   PUTINT
   PUTEOL
   LDGADDR 0
   LDGADDR 2
   LOADW
   LDCINT 0
   BE L0
   LDCB 1
   BR L1
L0:
   LDCB 0
L1:
   STOREB
   LDGADDR 1
   LDGADDR 6
   LOADW
   LDCINT 0
   BE L2
   LDCB 1
   BR L3
L2:
   LDCB 0
L3:
   STOREB
   LDCSTR "b1 and b2 = "
   PUTSTR 12
   LDGADDR 0
   LOADB
   BNZ L4
   LDCB 0
   BR L5
L4:
   LDGADDR 1
   LOADB
L5:
   BZ L6
   LDCSTR "true"
   PUTSTR 4
   PUTEOL
   BR L7
L6:
   LDCSTR "false"
   PUTSTR 5
   PUTEOL
L7:
   LDCSTR "b1 or  b2 = "
   PUTSTR 12
   LDGADDR 0
   LOADB
   BZ L8
   LDCB 1
   BR L9
L8:
   LDGADDR 1
   LOADB
L9:
   BZ L10
   LDCSTR "true"
   PUTSTR 4
   PUTEOL
   BR L11
L10:
   LDCSTR "false"
   PUTSTR 5
   PUTEOL
L11:
   RET 0
