   CALL _main
   HALT
_main:
   PROC 104
   LDLADDR 8
   LDCSTR "before tab \t after tab"
   STORE 48
   LDLADDR 8
   LOAD 104
   PUTSTR 50
   PUTEOL
   LDCSTR "length of testString = "
   PUTSTR 23
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   LDLADDR 8
   LDCSTR "before carriage return \r after carriage return"
   STORE 96
   LDLADDR 8
   LOAD 104
   PUTSTR 50
   PUTEOL
   LDCSTR "length of testString = "
   PUTSTR 23
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   LDLADDR 8
   LDCSTR "before newline \n after newline"
   STORE 64
   LDLADDR 8
   LOAD 104
   PUTSTR 50
   PUTEOL
   LDCSTR "length of testString = "
   PUTSTR 23
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   LDLADDR 8
   LDCSTR "before single quote \' after single quote"
   STORE 84
   LDLADDR 8
   LOAD 104
   PUTSTR 50
   PUTEOL
   LDCSTR "length of testString = "
   PUTSTR 23
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   LDLADDR 8
   LDCSTR "before double quote \" after double quote"
   STORE 84
   LDLADDR 8
   LOAD 104
   PUTSTR 50
   PUTEOL
   LDCSTR "length of testString = "
   PUTSTR 23
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   LDLADDR 8
   LDCSTR "before backslash \\ after backslash"
   STORE 72
   LDLADDR 8
   LOAD 104
   PUTSTR 50
   PUTEOL
   LDCSTR "length of testString = "
   PUTSTR 23
   LDLADDR 8
   LOADW
   PUTINT
   PUTEOL
   PUTEOL
   RET 0
