   PROGRAM 20
   CALL _main
   HALT
_main:
L0:
   LDCSTR "Enter value for a (0 to exit):  "
   PUTSTR 32
   LDGADDR 0
   GETINT
   LDCSTR "a = "
   PUTSTR 4
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   LDGADDR 0
   LOADW
   LDCINT 0
   BE L1
   LDCSTR "Enter value for b:  "
   PUTSTR 20
   LDGADDR 4
   GETINT
   LDCSTR "b = "
   PUTSTR 4
   LDGADDR 4
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   LDGADDR 8
   LDGADDR 0
   LOADW
   STOREW
   LDGADDR 12
   LDGADDR 4
   LOADW
   STOREW
   LDGADDR 8
   LOADW
   LDCINT 0
   BGE L6
   LDGADDR 8
   LDGADDR 8
   LOADW
   NEG
   STOREW
L6:
   LDGADDR 12
   LOADW
   LDCINT 0
   BGE L10
   LDGADDR 12
   LDGADDR 12
   LOADW
   NEG
   STOREW
L10:
L12:
   LDGADDR 16
   LDGADDR 8
   LOADW
   STOREW
   LDGADDR 8
   LDGADDR 12
   LOADW
   STOREW
   LDGADDR 12
   LDGADDR 16
   LOADW
   LDGADDR 12
   LOADW
   MOD
   STOREW
   LDGADDR 12
   LOADW
   LDCINT 0
   BE L13
   BR L12
L13:
   LDCSTR "GCD("
   PUTSTR 4
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR ", "
   PUTSTR 2
   LDGADDR 4
   LOADW
   PUTINT
   LDCSTR ") = "
   PUTSTR 4
   LDGADDR 8
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   BR L0
L1:
   LDCSTR "Done."
   PUTSTR 5
   PUTEOL
   RET 0
