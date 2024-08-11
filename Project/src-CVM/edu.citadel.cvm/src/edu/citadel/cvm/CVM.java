package edu.citadel.cvm;

import edu.citadel.compiler.util.ByteUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class implements a virtual machine for the programming language CPRL.
 * It interprets instructions for a hypothetical CPRL computer.
 */
public class CVM
  {
    private static final boolean DEBUG = false;
    private static final String SUFFIX = ".obj";

    // exit return value for failure
    private static final int FAILURE = -1;

    // 1K = 2**10
    private static final int K = 1024;

    // default memory size for the virtual machine
    private static final int DEFAULT_MEMORY_SIZE = 16*K;

    // virtual machine constant for false
    private static final byte FALSE = (byte) 0;

    // virtual machine constant for true
    private static final byte TRUE = (byte) 1;

    // end of file
    private static final int EOF = -1;

    // scanner for handling integer and string input
    private Scanner scanner = new Scanner(System.in);

    // Reader for handling char input
    private Reader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);

    // PrintStream for handling output
    private PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    // computer memory (for the virtual CPRL machine)
    private byte[] memory;

    // program counter (index of the next instruction in memory)
    private int pc = 0;

    // base pointer
    private int bp = 0;

    // stack pointer (index of the top of the stack)
    private int sp = 0;

    // bottom of the stack
    private int sb = 0;

    // true if the virtual computer is currently running
    private boolean running = false;

    /**
     * This method constructs a CPRL virtual machine, loads into memory the
     * byte code from the file specified by args[0], and runs the byte code.
     *
     * @throws FileNotFoundException if the file specified in args[0] can't be found.
     */
    public static void main(String[] args) throws FileNotFoundException
      {
        if (args.length != 1)
          {
            System.err.println("Usage: java edu.citadel.cvm.CVM filename");
            System.exit(0);
          }

        var filename = args[0];
        var sourceFile = new File(filename);

        if (!sourceFile.isFile())
          {
            // see if we can find the file by appending the suffix
            int index = filename.lastIndexOf('.');

            if (index < 0 || !filename.substring(index).equals(SUFFIX))
              {
                filename  += SUFFIX;
                sourceFile = new File(filename);

                if (!sourceFile.isFile())
                  {
                    System.err.println("*** File " + filename + " not found ***");
                    System.exit(FAILURE);
                  }
              }
            else
              {
                // don't try to append the suffix
                System.err.println("*** File " + filename + " not found ***");
                System.exit(FAILURE);
              }
          }

        FileInputStream codeFile = new FileInputStream(sourceFile);
        var cvm = new CVM(DEFAULT_MEMORY_SIZE);
        cvm.loadProgram(codeFile);
        cvm.run();
      }

    /**
     * Construct a CPRL virtual machine with a given number of bytes of memory.
     *
     * @param numOfBytes the number of bytes in memory of the virtual machine
     */
    public CVM(int numOfBytes)
      {
        // create and zero out memory
        memory = new byte[numOfBytes];
        for (int i = 0; i < memory.length; ++i)
            memory[i] = 0;
      }

    /**
     * Loads the program into memory.
     *
     * @param codeFile the FileInputStream containing the object code
     */
    public void loadProgram(FileInputStream codeFile)
      {
        int address = 0;
        int inByte;

        try
          {
            inByte = codeFile.read();
            while (inByte != -1)
              {
                memory[address++] = (byte) inByte;
                inByte = codeFile.read();
              }

            bp = address;
            sb = address;
            sp = bp - 1;
            codeFile.close();
          }
        catch (IOException ex)
          {
            error(ex.toString());
          }
      }

    /**
     * Prints values of internal registers to standard output.
     */
    private void printRegisters()
      {
        out.println("PC=" + pc + ", BP=" + bp + ", SB=" + sb + ", SP=" + sp);
      }

    /**
     * Prints a view of memory to standard output.
     */
    private void printMemory()
      {
        int  memAddr = 0;
        byte byte0;
        byte byte1;
        byte byte2;
        byte byte3;

        while (memAddr < sb)
          {
            // Prints "PC ->" in front of the correct memory address
            if (pc == memAddr)
                out.print("PC ->");
            else
                out.print("     ");

            var memAddrStr = String.format("%4s", memAddr);
            var opcode = Opcode.toOpcode(memory[memAddr]);

            if (opcode.isZeroOperandOpcode())
              {
                out.println(memAddrStr + ":  " + opcode);
                ++memAddr;
              }
            else if (opcode.isByteOperandOpcode())
              {
                out.print(memAddrStr + ":  " + opcode);
                ++memAddr;
                out.println(" " + memory[memAddr++]);

              }
            else if (opcode.isIntOperandOpcode())
              {
                out.print(memAddrStr + ":  " + opcode);
                ++memAddr;
                byte0 = memory[memAddr++];
                byte1 = memory[memAddr++];
                byte2 = memory[memAddr++];
                byte3 = memory[memAddr++];
                out.println(" " + ByteUtil.bytesToInt(byte0, byte1, byte2, byte3));
              }
            else if (opcode == Opcode.LDCCH)
              {
                // special case: LDCCH
                out.print(memAddrStr + ":  " + opcode);
                ++memAddr;
                byte0 = memory[memAddr++];
                byte1 = memory[memAddr++];
                out.println(" " + ByteUtil.bytesToChar(byte0, byte1));

              }
            else if (opcode == Opcode.LDCSTR)
              {
                // special case: LDCSTR
                out.print(memAddrStr + ":  " + opcode);
                ++memAddr;
                // now print the string
                out.print("  \"");
                byte0 = memory[memAddr++];
                byte1 = memory[memAddr++];
                byte2 = memory[memAddr++];
                byte3 = memory[memAddr++];
                int strLength = ByteUtil.bytesToInt(byte0, byte1, byte2, byte3);
                for (int i = 0; i < strLength; ++i)
                  {
                    byte0 = memory[memAddr++];
                    byte1 = memory[memAddr++];
                    out.print(ByteUtil.bytesToChar(byte0, byte1));
                   }
                 out.println("\"");
              }
            else
                error("*** PrintMemory: Unknown opcode " + opcode + " ***");
          }

        // now print remaining values that compose the stack
        for (memAddr = sb; memAddr <= sp; ++memAddr)
          {
            // Prints "SB ->", "BP ->", and "SP ->" in front of the correct memory address
            if (sb == memAddr)
                out.print("SB ->");
            else if (bp == memAddr)
                out.print("BP ->");
            else if (sp == memAddr)
                out.print("SP ->");
            else
                out.print("     ");

            var memAddrStr = String.format("%4s", memAddr);
            out.println(memAddrStr + ":  " + memory[memAddr]);
          }

        out.println();
      }

    /**
     * Alternative to Console.readline() when CVM runs in an IDE.
     */
    private void readLine()
      {
        int ch;
        try
          {
            do
                ch = System.in.read();
            while (ch != '\n');
          }
        catch (IOException ex)
          {
            error(ex.toString());
          }
      }

    /**
     * Prompt user and wait for user to press the enter key.
     */
    private void pause()
      {
        Console console = System.console();
        if (console == null)
            readLine();
        else
            console.readLine();
      }

    /**
     * Runs the program currently in memory.
     */
    public void run()
      {
        running = true;
        pc = 0;

        while (running)
          {
            if (DEBUG)
              {
                printRegisters();
                printMemory();
                pause();
              }

            switch (Opcode.toOpcode(fetchByte()))
              {
                case ADD      -> add();
                case BITAND   -> bitAnd();
                case BITOR    -> bitOr();
                case BITXOR   -> bitXor();
                case BITNOT   -> bitNot();
                case ALLOC    -> allocate();
                case BR       -> branch();
                case BE       -> branchEqual();
                case BNE      -> branchNotEqual();
                case BG       -> branchGreater();
                case BGE      -> branchGreaterOrEqual();
                case BL       -> branchLess();
                case BLE      -> branchLessOrEqual();
                case BZ       -> branchZero();
                case BNZ      -> branchNonZero();
                case BYTE2INT -> byteToInteger();
                case CALL     -> call();
                case DEC      -> decrement();
                case DIV      -> divide();
                case GETCH    -> getCh();
                case GETINT   -> getInt();
                case GETSTR   -> getString();
                case HALT     -> halt();
                case INC      -> increment();
                case INT2BYTE -> intToByte();
                case LDCB     -> loadConstByte();
                case LDCB0    -> loadConstByteZero();
                case LDCB1    -> loadConstByteOne();
                case LDCCH    -> loadConstCh();
                case LDCINT   -> loadConstInt();
                case LDCINT0  -> loadConstIntZero();
                case LDCINT1  -> loadConstIntOne();
                case LDCSTR   -> loadConstStr();
                case LDLADDR  -> loadLocalAddress();
                case LDGADDR  -> loadGlobalAddress();
                case LOAD     -> load();
                case LOADB    -> loadByte();
                case LOAD2B   -> load2Bytes();
                case LOADW    -> loadWord();
                case MOD      -> modulo();
                case MUL      -> multiply();
                case NEG      -> negate();
                case NOT      -> not();
                case PROC     -> procedure();
                case PROGRAM  -> program();
                case PUTBYTE  -> putByte();
                case PUTCH    -> putChar();
                case PUTEOL   -> putEOL();
                case PUTINT   -> putInt();
                case PUTSTR   -> putString();
                case RET      -> returnInst();
                case RET0     -> returnZero();
                case RET4     -> returnFour();
                case SHL      -> shl();
                case SHR      -> shr();
                case STORE    -> store();
                case STOREB   -> storeByte();
                case STORE2B  -> store2Bytes();
                case STOREW   -> storeWord();
                case SUB      -> subtract();
                default       -> error("invalid machine instruction");
              }
          }
      }

    // Start: internal machine instructions that do NOT correspond to opcodes
    // ------------------------------------------------------------------------

    /**
     * Print an error message and exit with nonzero status code.
     */
    private static void error(String message)
      {
        System.err.println(message);
        System.exit(1);
      }

    /**
     * Pop the top byte off the stack and return its value.
     */
    private byte popByte()
      {
        return memory[sp--];
      }

    /**
     * Pop the top character off the stack and return its value.
     */
    private char popChar()
      {
        byte b1 = popByte();
        byte b0 = popByte();
        return ByteUtil.bytesToChar(b0, b1);
      }

    /**
     * Pop the top integer off the stack and return its value.
     */
    private int popInt()
      {
        byte b3 = popByte();
        byte b2 = popByte();
        byte b1 = popByte();
        byte b0 = popByte();
        return ByteUtil.bytesToInt(b0, b1, b2, b3);
      }

    /**
     * Push a byte onto the stack.
     */
    private void pushByte(byte b)
      {
        if (++sp >= memory.length)
            error("*** Out of memory ***");
        memory[sp] = b;
      }

    /**
     * Push a character onto the stack.
     */
    private void pushChar(char c)
      {
        byte[] bytes = ByteUtil.charToBytes(c);
        pushByte(bytes[0]);
        pushByte(bytes[1]);
      }

    /**
     * Push an integer onto the stack.
     */
    private void pushInt(int n)
      {
        byte[] bytes = ByteUtil.intToBytes(n);
        pushByte(bytes[0]);
        pushByte(bytes[1]);
        pushByte(bytes[2]);
        pushByte(bytes[3]);
      }

    /**
     * Fetch the next instruction/byte from memory.
     */
    private byte fetchByte()
      {
        return memory[pc++];
      }

    /**
     * Fetch the next character operand from memory.
     */
    private char fetchChar()
      {
        byte b0 = fetchByte();
        byte b1 = fetchByte();
        return ByteUtil.bytesToChar(b0, b1);
      }

    /**
     * Fetch the next integer operand from memory.
     */
    private int fetchInt()
      {
        byte b0 = fetchByte();
        byte b1 = fetchByte();
        byte b2 = fetchByte();
        byte b3 = fetchByte();
        return ByteUtil.bytesToInt(b0, b1, b2, b3);
      }

    /**
     * Returns the character at the specified memory address.
     * Does not alter pc, sp, or bp.
     */
    private char getCharAtAddr(int address)
      {
        byte b0 = memory[address + 0];
        byte b1 = memory[address + 1];
        return ByteUtil.bytesToChar(b0, b1);
      }

    /**
     * Returns the integer at the specified memory address.
     * Does not alter pc, sp, or bp.
     */
    private int getIntAtAddr(int address)
      {
        byte b0 = memory[address + 0];
        byte b1 = memory[address + 1];
        byte b2 = memory[address + 2];
        byte b3 = memory[address + 3];
        return ByteUtil.bytesToInt(b0, b1, b2, b3);
      }

    /**
     * Returns the word at the specified memory address.
     * Does not alter pc, sp, or bp.
     */
    private int getWordAtAddr(int address)
      {
        return getIntAtAddr(address);
      }

    /**
     * Writes the char value to the specified memory address.
     * Does not alter pc, sp, or bp.
     */
    private void putCharToAddr(char value, int address)
      {
        byte[] bytes = ByteUtil.charToBytes(value);
        memory[address + 0] = bytes[0];
        memory[address + 1] = bytes[1];
      }

    /**
     * Writes the integer value to the specified memory address.
     * Does not alter pc, sp, or bp.
     */
    private void putIntToAddr(int value, int address)
      {
        byte[] bytes = ByteUtil.intToBytes(value);
        memory[address + 0] = bytes[0];
        memory[address + 1] = bytes[1];
        memory[address + 2] = bytes[2];
        memory[address + 3] = bytes[3];
      }

    /**
     * Writes the word value to the specified memory address.
     * Does not alter pc, sp, or bp.
     */
    private void putWordToAddr(int value, int address)
      {
        putIntToAddr(value, address);
      }

    // ----------------------------------------------------------------------
    // End: internal machine instructions that do NOT correspond to opcodes
    // Start: machine instructions corresponding to opcodes
    // ------------------------------------------------------

    private void add()
      {
        int operand2 = popInt();
        int operand1 = popInt();
        pushInt(operand1 + operand2);
      }

    private void allocate()
      {
        int numBytes = fetchInt();
        sp = sp + numBytes;
        if (sp >= memory.length)
            error("*** Out of memory ***");
      }

    private void bitAnd()
      {
        int operand2 = popInt();
        int operand1 = popInt();
        pushInt(operand1 & operand2);
      }

    private void bitOr()
      {
        int operand2 = popInt();
        int operand1 = popInt();
        pushInt(operand1 | operand2);
      }

    private void bitXor()
      {
        int operand2 = popInt();
        int operand1 = popInt();
        pushInt(operand1 ^ operand2);
      }

    private void bitNot()
      {
        int operand = popInt();
        pushInt(~operand);
      }

    private void branch()
      {
        int displacement = fetchInt();
        pc = pc + displacement;
      }

    private void branchEqual()
      {
        int displacement = fetchInt();
        int operand2 = popInt();
        int operand1 = popInt();

        if (operand1 == operand2)
            pc = pc + displacement;
      }

    private void branchNotEqual()
      {
        int displacement = fetchInt();
        int operand2 = popInt();
        int operand1 = popInt();

        if (operand1 != operand2)
            pc = pc + displacement;
      }

    private void branchGreater()
      {
        int displacement = fetchInt();
        int operand2 = popInt();
        int operand1 = popInt();

        if (operand1 > operand2)
            pc = pc + displacement;
      }

    private void branchGreaterOrEqual()
      {
        int displacement = fetchInt();
        int operand2 = popInt();
        int operand1 = popInt();

        if (operand1 >= operand2)
            pc = pc + displacement;
      }

    private void branchLess()
      {
        int displacement = fetchInt();
        int operand2 = popInt();
        int operand1 = popInt();

        if (operand1 < operand2)
            pc = pc + displacement;
      }

    private void branchLessOrEqual()
      {
        int displacement = fetchInt();
        int operand2 = popInt();
        int operand1 = popInt();

        if (operand1 <= operand2)
            pc = pc + displacement;
      }

    private void branchZero()
      {
        int  displacement = fetchInt();
        byte value = popByte();

        if (value == 0)
            pc = pc + displacement;
      }

    private void branchNonZero()
      {
        int  displacement = fetchInt();
        byte value = popByte();

        if (value != 0)
            pc = pc + displacement;
      }

    private void byteToInteger()
      {
        byte b = popByte();
        pushInt(ByteUtil.byteToInt(b));
      }

    private void call()
      {
        int displacement = fetchInt();

        pushInt(bp);   // dynamic link
        pushInt(pc);   // return address

        // set bp to starting address of new frame
        bp = sp - Constants.BYTES_PER_CONTEXT + 1;

        // set pc to first statement of called procedure
        pc = pc + displacement;
      }

    private void decrement()
      {
        int operand = popInt();
        pushInt(operand - 1);
      }

    private void divide()
      {
        int operand2 = popInt();
        int operand1 = popInt();

        if (operand2 != 0)
            pushInt(operand1/operand2);
        else
            error("*** FAULT: Divide by zero ***");
      }

    private void getCh()
      {
        try
          {
            int destAddr = popInt();
            int ch = reader.read();

            if (ch == EOF)
                error("Invalid input: EOF");

            putCharToAddr((char) ch, destAddr);
          }
        catch (IOException ex)
          {
            error(ex.toString());
          }
      }

    private void getInt()
      {
        try
          {
            int destAddr = popInt();
            int n = scanner.nextInt();
            putIntToAddr(n, destAddr);
          }
        catch (NumberFormatException ex)
          {
            error(ex.toString());
          }
      }

    private void getString()
      {
        try
          {
            int destAddr = popInt();
            int capacity = fetchInt();
            var data = "";

            if (scanner.hasNextLine())
                data = scanner.nextLine();

            int length = data.length() < capacity ? data.length() : capacity;

            putIntToAddr(length, destAddr);
            destAddr = destAddr + Constants.BYTES_PER_INTEGER;
            for (int i = 0; i < length; ++i)
              {
                putCharToAddr(data.charAt(i), destAddr);
                destAddr = destAddr + Constants.BYTES_PER_CHAR;
              }
          }
        catch (Exception ex)
          {
            error(ex.toString());
          }
      }

    private void halt()
      {
        running = false;
      }

    private void increment()
      {
        int operand = popInt();
        pushInt(operand + 1);
      }

    private void intToByte()
      {
        int n = popInt();
        pushByte(ByteUtil.intToByte(n));
      }

    /**
     * Loads a multibyte variable onto the stack.  The number of bytes
     * is an argument of the instruction, and the address of the
     * variable is obtained by popping it off the top of the stack.
     */
    private void load()
      {
        int length  = fetchInt();
        int address = popInt();

        for (int i = 0; i < length; ++i)
            pushByte(memory[address + i]);
      }

    private void loadConstByte()
      {
        byte b = fetchByte();
        pushByte(b);
      }

    private void loadConstByteZero()
      {
        pushByte((byte) 0);
      }

    private void loadConstByteOne()
      {
        pushByte((byte) 1);
      }

    private void loadConstCh()
      {
        char ch = fetchChar();
        pushChar(ch);
      }

    private void loadConstInt()
      {
        int value = fetchInt();
        pushInt(value);
      }

    private void loadConstIntZero()
      {
        pushInt(0);
      }

    private void loadConstIntOne()
      {
        pushInt(1);
      }

    private void loadConstStr()
      {
        int capacity = fetchInt();
        pushInt(capacity);

        // fetch each character and push it onto the stack
        for (int i = 0; i < capacity; ++i)
            pushChar(fetchChar());
      }

    private void loadLocalAddress()
      {
        int displacement = fetchInt();
        pushInt(bp + displacement);
      }

    private void loadGlobalAddress()
      {
        int displacement = fetchInt();
        pushInt(sb + displacement);
      }

    /**
     * Loads a single byte onto the stack.  The address of the
     * byte is obtained by popping it off the top of the stack.
     */
    private void loadByte()
      {
        int  address = popInt();
        byte b = memory[address];
        pushByte(b);
      }

    /**
     * Loads two bytes onto the stack.  The address of the first
     * byte is obtained by popping it off the top of the stack.
     */
    private void load2Bytes()
      {
        int  address = popInt();
        byte b0 = memory[address + 0];
        byte b1 = memory[address + 1];
        pushByte(b0);
        pushByte(b1);
      }

    /**
     * Loads a single word-size variable (four bytes) onto the stack.  The address
     * of the variable is obtained by popping it off the top of the stack.
     */
    private void loadWord()
      {
        int address = popInt();
        int word = getWordAtAddr(address);
        pushInt(word);
      }

    private void modulo()
      {
        int operand2 = popInt();
        int operand1 = popInt();
        pushInt(operand1%operand2);
      }

    private void multiply()
      {
        int operand2 = popInt();
        int operand1 = popInt();
        pushInt(operand1*operand2);
      }

    private void negate()
      {
        int operand1 = popInt();
        pushInt(-operand1);
      }

    private void not()
      {
        byte operand = popByte();

        if (operand == FALSE)
            pushByte(TRUE);
        else
            pushByte(FALSE);
      }

    private void procedure()
      {
        allocate();
      }

    private void program()
      {
        int varLength = fetchInt();

        bp = sb;
        sp = bp + varLength - 1;
        if (sp >= memory.length)
            error("*** Out of memory ***");
      }

    private void putChar()
      {
        out.print(popChar());
      }

    private void putByte()
      {
        out.print((popByte() + 256) % 256);
      }

    private void putInt()
      {
        out.print(popInt());
      }

    private void putEOL()
      {
        out.println();
      }

    private void putString()
      {
        int capacity = fetchInt();

        // number of bytes in the string
        int numBytes = Constants.BYTES_PER_INTEGER + capacity*Constants.BYTES_PER_CHAR;

        int addr = sp - numBytes + 1;           // initialize to starting address of string
        int strLength = getIntAtAddr(addr);
        addr = addr + Constants.BYTES_PER_INTEGER;

        for (int i = 0; i < strLength; ++i)
          {
            out.print(getCharAtAddr(addr));
            addr = addr + Constants.BYTES_PER_CHAR;
          }

        // remove (pop) the string off the stack
        sp = sp - capacity;
      }

    private void returnInst()
      {
        int paramLength = fetchInt();
        pc = getIntAtAddr(bp + Constants.BYTES_PER_INTEGER);
        sp = bp - paramLength - 1;
        bp = getIntAtAddr(bp);
      }

    private void returnZero()
      {
        pc = getIntAtAddr(bp + Constants.BYTES_PER_INTEGER);
        sp = bp - 1;
        bp = getIntAtAddr(bp);
      }

    private void returnFour()
      {
        pc = getIntAtAddr(bp + Constants.BYTES_PER_INTEGER);
        sp = bp - 5;
        bp = getIntAtAddr(bp);
      }

    private void shl()
      {
        int operand2 = popInt();
        int operand1 = popInt();

        // zero out all except rightmost 5 bits of shiftAmount
        int shiftAmount = operand2 & 0b11111;

        pushInt(operand1 << shiftAmount);
      }

    private void shr()
      {
        int operand2 = popInt();
        int operand1 = popInt();

        // zero out all except rightmost 5 bits of shiftAmount
        int shiftAmount = operand2 & 0b11111;

        pushInt(operand1 >> shiftAmount);
      }

    private void store()
      {
        int length   = fetchInt();
        int destAddr = getIntAtAddr(sp - length - 3);

        // pop bytes of data, storing in reverse order
        for (int i = length - 1; i >= 0; --i)
            memory[destAddr + i] = popByte();
        popByte();   // remove destAddr from stack
      }

    private void storeByte()
      {
        byte value = popByte();
        int  destAddr = popInt();
        memory[destAddr] = value;
      }

    private void store2Bytes()
      {
        byte byte1 = popByte();
        byte byte0 = popByte();
        int  destAddr = popInt();
        memory[destAddr + 0] = byte0;
        memory[destAddr + 1] = byte1;
      }

    private void storeWord()
      {
        int value = popInt();
        int destAddr = popInt();
        putWordToAddr(value, destAddr);
      }

    private void subtract()
      {
        int operand2 = popInt();
        int operand1 = popInt();
        int result   = operand1 - operand2;
        pushInt(result);
      }
  }
