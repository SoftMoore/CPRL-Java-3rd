package edu.citadel.cvm;

import edu.citadel.compiler.util.ByteUtil;
import edu.citadel.compiler.util.StringUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Translates CVM machine code into CVM assembly language.
 */
public class Disassembler
  {
    private static final String SUFFIX   = ".obj";
    private static final int FIELD_WIDTH = 4;

    /**
     * Translates CVM machine code into CVM assembly language
     * for each object code file named in args.
     */
    public static void main(String[] args) throws IOException
      {
        if (args.length == 0)
              printUsageMessageAndExit();

        for (String fileName : args)
          {
            FileInputStream file = new FileInputStream(fileName);

            // get object code file name minus the suffix
            int suffixIndex = fileName.lastIndexOf(SUFFIX);
            String baseName = fileName.substring(0, suffixIndex);

            String outputFileName = baseName + ".dis.txt";
            FileWriter writer = new FileWriter(outputFileName, StandardCharsets.UTF_8);
            PrintWriter out = new PrintWriter(writer, true);

            System.out.println("Disassembling " + fileName + " to " + outputFileName);

            int inByte;
            int opCodeAddr = 0;
            int strLength  = 0;

            char c;

            inByte = file.read();
            while (inByte != -1)
              {
                byte opCode = (byte) inByte;

                switch (opCode)
                  {
                    // opcodes with zero operands
                    case OpCode.ADD,     OpCode.DEC,     OpCode.DIV,
                         OpCode.GETCH,   OpCode.GETINT,  OpCode.HALT,
                         OpCode.LOADB,   OpCode.LOAD2B,  OpCode.LOADW,
                         OpCode.LOADSTR, OpCode.LDCB0,   OpCode.LDCB1,
                         OpCode.LDCINT0, OpCode.LDCINT1, OpCode.INC,
                         OpCode.MOD,     OpCode.MUL,     OpCode.NEG,
                         OpCode.NOT,     OpCode.PUTBYTE, OpCode.PUTCH,
                         OpCode.PUTINT,  OpCode.PUTEOL,  OpCode.RET0,
                         OpCode.RET4,    OpCode.STOREB,  OpCode.STORE2B,
                         OpCode.STOREW,  OpCode.STOREST, OpCode.SUB  ->
                      {
                        out.println(StringUtil.format(opCodeAddr, FIELD_WIDTH) + ":  "
                                                    + OpCode.toString(opCode));
                        opCodeAddr = opCodeAddr + 1;
                      }

                    // opcodes with one byte operand
                    case OpCode.SHL,     OpCode.SHR,     OpCode.LDCB ->
                      {
                        out.print(StringUtil.format(opCodeAddr, FIELD_WIDTH) + ":  "
                                                    + OpCode.toString(opCode));
                        out.println(" " + readByte(file));
                        opCodeAddr = opCodeAddr + 2;  // one byte for opcode and one byte for shift amount
                      }

                    // opcodes with one int operand
                    case OpCode.ALLOC,   OpCode.BR,      OpCode.BE,
                         OpCode.BNE,     OpCode.BG,      OpCode.BGE,
                         OpCode.BL,      OpCode.BLE,     OpCode.BZ,
                         OpCode.BNZ,     OpCode.CALL,    OpCode.GETSTR,
                         OpCode.LOAD,    OpCode.LDCINT,  OpCode.LDLADDR,
                         OpCode.LDGADDR, OpCode.PROC,    OpCode.PROGRAM,
                         OpCode.PUTSTR,  OpCode.RET,     OpCode.STORE  ->
                       {
                         out.print(StringUtil.format(opCodeAddr, FIELD_WIDTH) + ":  "
                                                   + OpCode.toString(opCode));
                         out.println(" " + readInt(file));
                         opCodeAddr = opCodeAddr + 1 + Constants.BYTES_PER_INTEGER;
                       }

                    // special case: LDCCH
                    case OpCode.LDCCH ->
                      {
                        out.print(StringUtil.format(opCodeAddr, FIELD_WIDTH) + ":  "
                                                  + OpCode.toString(opCode));
                        out.print(" \'");

                        c = readChar(file);
                        if (c == '\b' || c == '\t' || c == '\n' || c == '\f'
                         || c == '\r' || c == '\"' || c == '\'' || c == '\\')
                            out.print(getUnescapedChar(c));
                        else
                            out.print(c);

                        out.println("\'");
                        opCodeAddr = opCodeAddr + 1 + Constants.BYTES_PER_CHAR;
                      }

                    // special case: LDCSTR
                    case OpCode.LDCSTR  ->
                      {
                        out.print(StringUtil.format(opCodeAddr, FIELD_WIDTH) + ":  "
                                                  + OpCode.toString(opCode));
                        // now print the string
                        out.print("  \"");
                        strLength = readInt(file);
                        for (int i = 0;  i < strLength;  ++i)
                          {
                            c = readChar(file);
                            if (c == '\b' || c == '\t' || c == '\n' || c == '\f'
                             || c == '\r' || c == '\"' || c == '\'' || c == '\\')
                                out.print(getUnescapedChar(c));
                            else
                                out.print(c);
                          }
                        out.println("\"");
                        opCodeAddr = opCodeAddr + 1 + Constants.BYTES_PER_INTEGER
                                   + strLength*Constants.BYTES_PER_CHAR;
                      }

                    default ->
                        System.err.println("*** Unknown opCode in file " + fileName + " ***");
                  }

                inByte = file.read();
              }

            out.close();
          }
      }

    /**
     * Reads an integer argument from the stream.
     */
    private static int readInt(InputStream in) throws IOException
      {
        byte b0 = (byte) in.read();
        byte b1 = (byte) in.read();
        byte b2 = (byte) in.read();
        byte b3 = (byte) in.read();

        return ByteUtil.bytesToInt(b0, b1, b2, b3);
      }

    /**
     * Reads a char argument from the stream.
     */
    private static char readChar(InputStream in) throws IOException
      {
        byte b0 = (byte) in.read();
        byte b1 = (byte) in.read();

        return ByteUtil.bytesToChar(b0, b1);
      }

    /**
     * Reads a byte argument from the stream.
     */
    private static byte readByte(InputStream in) throws IOException
      {
        return (byte) in.read();
      }

    /**
     * Unescapes characters.  For example, if the parameter c is a tab,
     * this method will return "\\t"
     *
     * @return the string for an escaped character.
     */
    private static String getUnescapedChar(char c)
      {
        switch (c)
          {
            case '\b' : return "\\b";    // backspace
            case '\t' : return "\\t";    // tab
            case '\n' : return "\\n";    // linefeed (a.k.a. newline)
            case '\f' : return "\\f";    // form feed
            case '\r' : return "\\r";    // carriage return
            case '\"' : return "\\\"";   // double quote
            case '\'' : return "\\\'";   // single quote
            case '\\' : return "\\\\";   // backslash
            default   : return Character.toString(c);
          }
      }

    private static void printUsageMessageAndExit()
      {
        System.out.println("Usage: java edu.citadel.cvm.Disassembler filename");
        System.out.println();
        System.exit(0);
      }
  }
