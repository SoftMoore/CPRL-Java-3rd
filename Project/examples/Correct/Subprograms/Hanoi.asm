   CALL _main
   HALT
_main:
   PROC 4
   LDCSTR "Enter number of disks to be moved:  "
   PUTSTR 36
   LDLADDR 8
   GETINT
   LDLADDR 8
   LOADW
   LDCCH 'A'
   LDCCH 'B'
   LDCCH 'C'
   CALL _move
   RET 0
_move:
   LDLADDR -10
   LOADW
   LDCINT 0
   BLE L56
   LDLADDR -10
   LOADW
   LDCINT 1
   SUB
   LDLADDR -6
   LOAD2B
   LDLADDR -2
   LOAD2B
   LDLADDR -4
   LOAD2B
   CALL _move
   LDCSTR "Move a disk from "
   PUTSTR 17
   LDLADDR -6
   LOAD2B
   PUTCH
   LDCSTR " to "
   PUTSTR 4
   LDLADDR -4
   LOAD2B
   PUTCH
   PUTEOL
   LDLADDR -10
   LOADW
   LDCINT 1
   SUB
   LDLADDR -2
   LOAD2B
   LDLADDR -4
   LOAD2B
   LDLADDR -6
   LOAD2B
   CALL _move
L56:
   RET 10
