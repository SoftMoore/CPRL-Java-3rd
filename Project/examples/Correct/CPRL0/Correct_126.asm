   PROGRAM 4
   CALL _main
   HALT
_main:
   LDGADDR 0
   LDCINT 5
   STOREW
   LDCSTR "n = "
   PUTSTR 4
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   RET 0