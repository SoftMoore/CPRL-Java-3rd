   CALL _main
   HALT
_main:
   ALLOC 14
   ALLOC 1
   LDCCH 'A'
   CALL _isLetter
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'C'
   CALL _isLetter
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'z'
   CALL _isLetter
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '<'
   CALL _isLetter
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '~'
   CALL _isLetter
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '7'
   CALL _isLetter
   CALL _bToStr
   PUTSTR 5
   PUTEOL
   ALLOC 14
   ALLOC 1
   LDCCH '0'
   CALL _isDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '4'
   CALL _isDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '9'
   CALL _isDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'D'
   CALL _isDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '*'
   CALL _isDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '?'
   CALL _isDigit
   CALL _bToStr
   PUTSTR 5
   PUTEOL
   ALLOC 14
   ALLOC 1
   LDCCH 'S'
   CALL _isLetterOrDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'n'
   CALL _isLetterOrDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '9'
   CALL _isLetterOrDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '@'
   CALL _isLetterOrDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '$'
   CALL _isLetterOrDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '\''
   CALL _isLetterOrDigit
   CALL _bToStr
   PUTSTR 5
   PUTEOL
   ALLOC 14
   ALLOC 1
   LDCCH '0'
   CALL _isBinaryDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '1'
   CALL _isBinaryDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '0'
   CALL _isBinaryDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '8'
   CALL _isBinaryDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'F'
   CALL _isBinaryDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '#'
   CALL _isBinaryDigit
   CALL _bToStr
   PUTSTR 5
   PUTEOL
   ALLOC 14
   ALLOC 1
   LDCCH '3'
   CALL _isHexDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'C'
   CALL _isHexDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'f'
   CALL _isHexDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'G'
   CALL _isHexDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH ';'
   CALL _isHexDigit
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '.'
   CALL _isHexDigit
   CALL _bToStr
   PUTSTR 5
   PUTEOL
   RET 0
_bToStr:
   LDLADDR -1
   LOADB
   BZ L0
   LDLADDR -15
   LDCSTR "true"
   STORE 12
   RET 1
   BR L1
L0:
   LDLADDR -15
   LDCSTR "false"
   STORE 14
   RET 1
L1:
_isLetter:
   LDLADDR -3
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'a'
   BGE L6
   LDCB 0
   BR L7
L6:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'z'
   BG L4
   LDCB 1
   BR L5
L4:
   LDCB 0
L5:
L7:
   BZ L14
   LDCB 1
   BR L15
L14:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'A'
   BGE L12
   LDCB 0
   BR L13
L12:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'Z'
   BG L10
   LDCB 1
   BR L11
L10:
   LDCB 0
L11:
L13:
L15:
   STOREB
   RET 2
_isDigit:
   LDLADDR -3
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '0'
   BGE L20
   LDCB 0
   BR L21
L20:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '9'
   BG L18
   LDCB 1
   BR L19
L18:
   LDCB 0
L19:
L21:
   STOREB
   RET 2
_isLetterOrDigit:
   LDLADDR -3
   ALLOC 1
   LDLADDR -2
   LOAD2B
   CALL _isLetter
   BZ L22
   LDCB 1
   BR L23
L22:
   ALLOC 1
   LDLADDR -2
   LOAD2B
   CALL _isDigit
L23:
   STOREB
   RET 2
_isBinaryDigit:
   LDLADDR -3
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '0'
   BNE L28
   LDCB 1
   BR L29
L28:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '1'
   BNE L26
   LDCB 1
   BR L27
L26:
   LDCB 0
L27:
L29:
   STOREB
   RET 2
_isHexDigit:
   ALLOC 1
   LDLADDR -2
   LOAD2B
   CALL _isDigit
   BZ L44
   LDLADDR -3
   LDCB 1
   STOREB
   RET 2
   BR L45
L44:
   LDLADDR -3
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'A'
   BGE L34
   LDCB 0
   BR L35
L34:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'F'
   BG L32
   LDCB 1
   BR L33
L32:
   LDCB 0
L33:
L35:
   BZ L42
   LDCB 1
   BR L43
L42:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'a'
   BGE L40
   LDCB 0
   BR L41
L40:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH 'f'
   BG L38
   LDCB 1
   BR L39
L38:
   LDCB 0
L39:
L41:
L43:
   STOREB
   RET 2
L45:
