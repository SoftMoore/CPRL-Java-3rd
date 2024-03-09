package edu.citadel.cvm;

import java.util.*;

/**
 * The set of opcodes for the CPRL virtual machine.
 */
public enum Opcode
  {
    // halt opcode
    HALT(0),

    // load opcodes (move data from memory to top of stack)
    LOAD(10),
    LOADB(11),
    LOAD2B(12),
    LOADW(13),
    LDCB(14),
    LDCCH(15),
    LDCINT(16),
    LDCSTR(17),
    LDLADDR(18),
    LDGADDR(19),

    // optimized loads for special constants
    LDCB0(20),
    LDCB1(21),
    LDCINT0(22),
    LDCINT1(23),

    // store opcodes (move data from top of stack to memory)
    STORE(30),
    STOREB(31),
    STORE2B(32),
    STOREW(33),

    // branch opcodes
    BR(40),
    BE(41),
    BNE(42),
    BG(43),
    BGE(44),
    BL(45),
    BLE(46),
    BZ(47),
    BNZ(48),

    // type conversion opcodes
    INT2BYTE(50),
    BYTE2INT(51),

    // logical NOT opcode
    NOT(60),

    // bitwise and shift opcodes
    BITAND(61),
    BITOR(62),
    BITXOR(63),
    BITNOT(64),
    SHL(65),
    SHR(66),

    // arithmetic opcodes
    ADD(70),
    SUB(71),
    MUL(72),
    DIV(73),
    MOD(74),
    NEG(75),
    INC(76),
    DEC(77),

    // I/O opcodes
    GETCH(80),
    GETINT(81),
    GETSTR(82),
    PUTBYTE(83),
    PUTCH(84),
    PUTINT(85),
    PUTEOL(86),
    PUTSTR(87),

    // program/procedure opcodes
    PROGRAM(90),
    PROC(91),
    CALL(92),
    RET(93),
    ALLOC(94),

    // optimized returns for special constants
    RET0(100),
    RET4(101);

    private final byte value;

    /**
     * Construct an opcode with its machine instruction value.
     */
    private Opcode(int value)
      {
        this.value = (byte) value;
      }

    /**
     * Returns true if this opcode has no operands.
     */
    public boolean isZeroOperandOpcode ()
      {
        return switch (this)
          {
            case ADD,      BITAND,  BITNOT, BITOR,   BITXOR, BYTE2INT,
                 DEC,      DIV,     GETCH,  GETINT,  HALT,   INC,
                 INT2BYTE, LOADB,   LOAD2B, LOADW,   LDCB0,  LDCB1,
                 LDCINT0,  LDCINT1, MOD,    MUL,     NEG,    NOT,
                 PUTBYTE,  PUTCH,   PUTINT, PUTEOL,  RET0,   RET4,
                 SHL,      SHR,     STOREB, STORE2B, STOREW, SUB   -> true;
            default -> false;
          };
      }

    /**
     * Returns true if this opcode has a byte operand.
     */
    public boolean isByteOperandOpcode()
      {
        return this == LDCB;
      }

    /**
     * Returns true if this opcode has an int operand.
     */
    public boolean isIntOperandOpcode()
      {
        return switch (this)
          {
            case ALLOC,   BR,      BE,   BNE,     BG,     BGE,  BL,
                 BLE,     BZ,      BNZ,  CALL,    GETSTR, LOAD, LDCINT,
                 LDLADDR, LDGADDR, PROC, PROGRAM, PUTSTR, RET,  STORE  -> true;
            default -> false;
          };
      }

    public byte toByte()
      {
        return value;
      }

    /**
     * Returns the opcode for the specified integer value,
     * or null if the byte does not correspond to an opcode.
     */
    public static Opcode toOpcode(int n)
      {
        return (n < 0 || n > 256) ? null : toOpcode((byte) n);
      }

    /**
     * Returns the opcode for the specified byte value,
     * or null if the byte does not correspond to an opcode.
     */
    public static Opcode toOpcode(byte b)
      {
        return valueOpcodeMap.get(b);
      }

    private static Map<Byte, Opcode> valueOpcodeMap = new HashMap<>();

    static
      {
        for (Opcode opcode: values())
            valueOpcodeMap.put(opcode.value, opcode);
      }
  }
