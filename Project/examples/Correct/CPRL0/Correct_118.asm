   CALL _main
   HALT
_main:
   LDCSTR "string with \\n: \nremaining text."
   PUTSTR 32
   PUTEOL
   LDCSTR "string with \\t: \tremaining text."
   PUTSTR 32
   PUTEOL
   RET 0
