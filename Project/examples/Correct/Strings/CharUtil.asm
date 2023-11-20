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
   ALLOC 14
   ALLOC 1
   LDCCH '\t'
   CALL _isEscapeChar
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '\n'
   CALL _isEscapeChar
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '\"'
   CALL _isEscapeChar
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'u'
   CALL _isEscapeChar
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH 'r'
   CALL _isEscapeChar
   CALL _bToStr
   PUTSTR 5
   LDCSTR " "
   PUTSTR 1
   ALLOC 14
   ALLOC 1
   LDCCH '-'
   CALL _isEscapeChar
   CALL _bToStr
   PUTSTR 5
   PUTEOL
   ALLOC 8
   LDCCH '\t'
   CALL _getUnescapedChar
   PUTSTR 2
   LDCSTR "   "
   PUTSTR 3
   ALLOC 8
   LDCCH '\n'
   CALL _getUnescapedChar
   PUTSTR 2
   LDCSTR "   "
   PUTSTR 3
   ALLOC 8
   LDCCH '\r'
   CALL _getUnescapedChar
   PUTSTR 2
   LDCSTR "   "
   PUTSTR 3
   ALLOC 8
   LDCCH '\"'
   CALL _getUnescapedChar
   PUTSTR 2
   LDCSTR "    "
   PUTSTR 4
   ALLOC 8
   LDCCH '\''
   CALL _getUnescapedChar
   PUTSTR 2
   LDCSTR "    "
   PUTSTR 4
   ALLOC 8
   LDCCH '\\'
   CALL _getUnescapedChar
   PUTSTR 2
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
_isEscapeChar:
   LDLADDR -3
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\t'
   BNE L50
   LDCB 1
   BR L51
L50:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\n'
   BNE L48
   LDCB 1
   BR L49
L48:
   LDCB 0
L49:
L51:
   BZ L54
   LDCB 1
   BR L55
L54:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\r'
   BNE L52
   LDCB 1
   BR L53
L52:
   LDCB 0
L53:
L55:
   BZ L58
   LDCB 1
   BR L59
L58:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\"'
   BNE L56
   LDCB 1
   BR L57
L56:
   LDCB 0
L57:
L59:
   BZ L62
   LDCB 1
   BR L63
L62:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\''
   BNE L60
   LDCB 1
   BR L61
L60:
   LDCB 0
L61:
L63:
   BZ L66
   LDCB 1
   BR L67
L66:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\\'
   BNE L64
   LDCB 1
   BR L65
L64:
   LDCB 0
L65:
L67:
   STOREB
   RET 2
_getUnescapedChar:
   PROC 8
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\t'
   BNE L90
   LDLADDR -10
   LDCSTR "\\t"
   STORE 8
   RET 2
   BR L91
L90:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\n'
   BNE L88
   LDLADDR -10
   LDCSTR "\\n"
   STORE 8
   RET 2
   BR L89
L88:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\r'
   BNE L86
   LDLADDR -10
   LDCSTR "\\r"
   STORE 8
   RET 2
   BR L87
L86:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\"'
   BNE L84
   LDLADDR -10
   LDCSTR "\\\""
   STORE 8
   RET 2
   BR L85
L84:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\''
   BNE L82
   LDLADDR -10
   LDCSTR "\\\'"
   STORE 8
   RET 2
   BR L83
L82:
   LDCB 0
   LDCB 0
   LDLADDR -2
   LOAD2B
   LDCB 0
   LDCB 0
   LDCCH '\\'
   BNE L80
   LDLADDR -10
   LDCSTR "\\\\"
   STORE 8
   RET 2
   BR L81
L80:
   LDLADDR 8
   LDCINT 4
   ADD
   LDCINT 0
   LDCINT 2
   MUL
   ADD
   LDLADDR -2
   LOAD2B
   STORE2B
   LDLADDR 8
   LDCINT 1
   STOREW
   LDLADDR -10
   LDLADDR 8
   LOAD 8
   STORE 8
   RET 2
L81:
L83:
L85:
L87:
L89:
L91:
