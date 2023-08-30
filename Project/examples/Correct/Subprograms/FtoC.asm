   CALL _main
   HALT
_main:
   PROC 4
   LDCSTR "Enter temperature in degrees Fahrenheit: "
   PUTSTR 41
   LDLADDR 8
   GETINT
   LDCSTR "Temperature in degrees Celsius is "
   PUTSTR 34
   ALLOC 4
   LDLADDR 8
   LOADW
   CALL _toC
   PUTINT
   PUTEOL
   RET 0
_toC:
   PROC 4
   LDLADDR 8
   LDLADDR -4
   LOADW
   LDCINT 32
   SUB
   LDCINT 5
   MUL
   LDCINT 9
   DIV
   STOREW
   LDLADDR -4
   LOADW
   LDCINT 32
   SUB
   LDCINT 5
   MUL
   LDCINT 9
   MOD
   LDCINT 4
   BLE L2
   LDLADDR 8
   LDLADDR 8
   LOADW
   LDCINT 1
   ADD
   STOREW
L2:
   LDLADDR -8
   LDLADDR 8
   LOADW
   STOREW
   RET 4
