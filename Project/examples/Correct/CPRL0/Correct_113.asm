   PROGRAM 14
   CALL _main
   HALT
_main:
   LDGADDR 0
   LDCINT 5
   NEG
   STOREW
   LDGADDR 4
   LDCINT 2
   STOREW
   LDGADDR 8
   LDCINT 7
   STOREW
   LDGADDR 12
   LDCB 0
   STOREB
   LDGADDR 13
   LDCB 1
   STOREB
   LDGADDR 0
   LOADW
   LDGADDR 4
   LOADW
   BL L82
   LDCB 0
   BR L83
L82:
   LDGADDR 4
   LOADW
   LDGADDR 8
   LOADW
   BGE L80
   LDCB 1
   BR L81
L80:
   LDCB 0
L81:
L83:
   BZ L84
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
   BR L85
L84:
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
L85:
   LDGADDR 0
   LOADW
   LDGADDR 4
   LOADW
   BL L90
   LDCB 0
   BR L91
L90:
   LDGADDR 0
   LOADW
   LDGADDR 8
   LOADW
   BL L88
   LDCB 1
   BR L89
L88:
   LDCB 0
L89:
L91:
   BZ L92
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
   BR L93
L92:
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
L93:
   LDGADDR 4
   LOADW
   LDGADDR 0
   LOADW
   BLE L98
   LDCB 0
   BR L99
L98:
   LDGADDR 4
   LOADW
   LDGADDR 8
   LOADW
   BGE L96
   LDCB 1
   BR L97
L96:
   LDCB 0
L97:
L99:
   BZ L100
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
   BR L101
L100:
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
L101:
   LDGADDR 4
   LOADW
   LDGADDR 0
   LOADW
   BL L106
   LDCB 0
   BR L107
L106:
   LDGADDR 8
   LOADW
   LDGADDR 0
   LOADW
   NEG
   BG L104
   LDCB 1
   BR L105
L104:
   LDCB 0
L105:
L107:
   BZ L108
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
   BR L109
L108:
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
L109:
   LDGADDR 0
   LOADW
   LDGADDR 4
   LOADW
   BGE L114
   LDCB 1
   BR L115
L114:
   LDGADDR 4
   LOADW
   LDGADDR 8
   LOADW
   BGE L112
   LDCB 1
   BR L113
L112:
   LDCB 0
L113:
L115:
   BZ L116
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
   BR L117
L116:
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
L117:
   LDGADDR 0
   LOADW
   LDGADDR 4
   LOADW
   BGE L122
   LDCB 1
   BR L123
L122:
   LDGADDR 4
   LOADW
   NEG
   LDGADDR 8
   LOADW
   BLE L120
   LDCB 1
   BR L121
L120:
   LDCB 0
L121:
L123:
   BZ L124
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
   BR L125
L124:
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
L125:
   LDGADDR 4
   LOADW
   LDGADDR 0
   LOADW
   BG L130
   LDCB 1
   BR L131
L130:
   LDGADDR 4
   LOADW
   LDGADDR 8
   LOADW
   BGE L128
   LDCB 1
   BR L129
L128:
   LDCB 0
L129:
L131:
   BZ L132
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
   BR L133
L132:
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
L133:
   LDGADDR 0
   LOADW
   NEG
   LDGADDR 4
   LOADW
   BGE L138
   LDCB 1
   BR L139
L138:
   LDGADDR 8
   LOADW
   LDGADDR 4
   LOADW
   BG L136
   LDCB 1
   BR L137
L136:
   LDCB 0
L137:
L139:
   BZ L140
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
   BR L141
L140:
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
L141:
   LDGADDR 8
   LDCINT 0
   STOREW
   LDGADDR 12
   LOADB
   BNZ L144
   LDCB 0
   BR L145
L144:
   LDGADDR 4
   LOADW
   LDGADDR 8
   LOADW
   DIV
   LDCINT 2
   BL L142
   LDCB 1
   BR L143
L142:
   LDCB 0
L143:
L145:
   BZ L146
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
   BR L147
L146:
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
L147:
   LDGADDR 13
   LOADB
   BZ L150
   LDCB 1
   BR L151
L150:
   LDGADDR 4
   LOADW
   LDGADDR 8
   LOADW
   DIV
   LDCINT 0
   BGE L148
   LDCB 1
   BR L149
L148:
   LDCB 0
L149:
L151:
   BZ L152
   LDCSTR "valid"
   PUTSTR 5
   PUTEOL
   BR L153
L152:
   LDCSTR "not valid"
   PUTSTR 9
   PUTEOL
L153:
   RET 0
