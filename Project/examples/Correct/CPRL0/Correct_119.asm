   CALL _main
   HALT
_main:
   LDCSTR "character with \\n: "
   PUTSTR 19
   LDCCH '\n'
   PUTCH
   LDCSTR "remaining text."
   PUTSTR 15
   PUTEOL
   LDCSTR "character with \\t: "
   PUTSTR 19
   LDCCH '\t'
   PUTCH
   LDCSTR "remaining text."
   PUTSTR 15
   PUTEOL
   RET 0
