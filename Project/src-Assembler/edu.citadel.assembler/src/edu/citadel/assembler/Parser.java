package edu.citadel.assembler;

import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.ParserException;
import edu.citadel.compiler.Position;
import edu.citadel.assembler.ast.*;

import java.io.IOException;

import java.util.EnumSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class uses recursive descent to perform syntax analysis of the source language.
 */
public class Parser
  {
    private Scanner scanner;
    private ErrorHandler errorHandler;

    // Symbols that can follow an assembly language instruction.
    private Set<Symbol> instructionFollowers = makeInstructionFollowers();

    /**
     * Returns a set of symbols that can follow an instruction.
     */
    private Set<Symbol> makeInstructionFollowers()
      {
        var followers = EnumSet.noneOf(Symbol.class);

        // add all opcodes
        for (Symbol symbol : Symbol.values())
          {
            if (symbol.isOpcode())
                followers.add(symbol);
          }

        // add labelId and EOF
        followers.add(Symbol.labelId);
        followers.add(Symbol.EOF);

        return followers;
      }

    /**
     * Construct a parser with the specified scanner and error handler.
     */
    public Parser(Scanner scanner, ErrorHandler errorHandler)
      {
        this.scanner = scanner;
        this.errorHandler = errorHandler;
      }

    // program = { instruction } .
    public Program parseProgram() throws IOException
      {
        var program = new Program();

        try
          {
            // NOTE: Identifier is not a valid starter for an instruction,
            // but we handle it as a special case in order to give better
            // error reporting/recovery when an opcode mnemonic is misspelled.
            var symbol = scanner.getSymbol();
            while (symbol.isOpcode() || symbol == Symbol.labelId || symbol == Symbol.identifier)
              {
                Instruction instruction = parseInstruction();
                program.addInstruction(instruction);
                symbol = scanner.getSymbol();
              }

            matchEOF();
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            EnumSet<Symbol> followers = EnumSet.of(Symbol.EOF);
            scanner.advanceTo(followers);
          }

        return program;
      }

    // instruction = { labelId } opcodeMnemonic [ arg ] .
    private Instruction parseInstruction() throws IOException
      {
        try
          {
            var labels = new ArrayList<Token>();

            while (scanner.getSymbol() == Symbol.labelId)
              {
                labels.add(scanner.getToken());
                matchCurrentSymbol();
              }

            if (scanner.getSymbol() == Symbol.EOF)
              {
                // return HALT when a label is followed by EOF
                return makeInstruction(labels, new Token(Symbol.HALT), null);
              }
            else
              {
                checkOpcode();
                var opcode = scanner.getToken();
                matchCurrentSymbol();

                Token arg   = null;
                int numArgs = opcode.getSymbol().getNumArgs();
                if (numArgs == 1)
                  {
                    arg = scanner.getToken();
                    matchCurrentSymbol();
                  }

                return makeInstruction(labels, opcode, arg);
              }
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            scanner.advanceTo(instructionFollowers);
            return null;
          }
      }

    private Instruction makeInstruction(List<Token> labels, Token opcode, Token arg)
        throws ParserException
      {
        checkArgs(opcode, arg);

        return switch (opcode.getSymbol())
          {
            case HALT    -> new InstructionHALT(labels, opcode);
            case LOAD    -> new InstructionLOAD(labels, opcode, arg);
            case LOADB   -> new InstructionLOADB(labels, opcode);
            case LOAD2B  -> new InstructionLOAD2B(labels, opcode);
            case LOADW   -> new InstructionLOADW(labels, opcode);
            case LDCB    -> new InstructionLDCB(labels, opcode, arg);
            case LDCB0   -> new InstructionLDCB0(labels, opcode);
            case LDCB1   -> new InstructionLDCB1(labels, opcode);
            case LDCCH   -> new InstructionLDCCH(labels, opcode, arg);
            case LDCINT  -> new InstructionLDCINT(labels, opcode, arg);
            case LDCINT0 -> new InstructionLDCINT0(labels, opcode);
            case LDCINT1 -> new InstructionLDCINT1(labels, opcode);
            case LDCSTR  -> new InstructionLDCSTR(labels, opcode, arg);
            case LDLADDR -> new InstructionLDLADDR(labels, opcode, arg);
            case LDGADDR -> new InstructionLDGADDR(labels, opcode, arg);
            case STORE   -> new InstructionSTORE(labels, opcode, arg);
            case STOREB  -> new InstructionSTOREB(labels, opcode);
            case STORE2B -> new InstructionSTORE2B(labels, opcode);
            case STOREW  -> new InstructionSTOREW(labels, opcode);
            case BR      -> new InstructionBR(labels, opcode, arg);
            case BE      -> new InstructionBE(labels, opcode, arg);
            case BNE     -> new InstructionBNE(labels, opcode, arg);
            case BG      -> new InstructionBG(labels, opcode, arg);
            case BGE     -> new InstructionBGE(labels, opcode, arg);
            case BL      -> new InstructionBL(labels, opcode, arg);
            case BLE     -> new InstructionBLE(labels, opcode, arg);
            case BZ      -> new InstructionBZ(labels, opcode, arg);
            case BNZ     -> new InstructionBNZ(labels, opcode, arg);
            case SHL     -> new InstructionSHL(labels, opcode, arg);
            case SHR     -> new InstructionSHR(labels, opcode, arg);
            case NOT     -> new InstructionNOT(labels, opcode);
            case ADD     -> new InstructionADD(labels, opcode);
            case SUB     -> new InstructionSUB(labels, opcode);
            case MUL     -> new InstructionMUL(labels, opcode);
            case DIV     -> new InstructionDIV(labels, opcode);
            case MOD     -> new InstructionMOD(labels, opcode);
            case NEG     -> new InstructionNEG(labels, opcode);
            case INC     -> new InstructionINC(labels, opcode);
            case DEC     -> new InstructionDEC(labels, opcode);
            case GETCH   -> new InstructionGETCH(labels, opcode);
            case GETINT  -> new InstructionGETINT(labels, opcode);
            case GETSTR  -> new InstructionGETSTR(labels, opcode, arg);
            case PUTBYTE -> new InstructionPUTBYTE(labels, opcode);
            case PUTCH   -> new InstructionPUTCH(labels, opcode);
            case PUTINT  -> new InstructionPUTINT(labels, opcode);
            case PUTEOL  -> new InstructionPUTEOL(labels, opcode);
            case PUTSTR  -> new InstructionPUTSTR(labels, opcode, arg);
            case PROGRAM -> new InstructionPROGRAM(labels, opcode, arg);
            case PROC    -> new InstructionPROC(labels, opcode, arg);
            case CALL    -> new InstructionCALL(labels, opcode, arg);
            case RET     -> new InstructionRET(labels, opcode, arg);
            case RET0    -> new InstructionRET0(labels, opcode);
            case RET4    -> new InstructionRET4(labels, opcode);
            case ALLOC   -> new InstructionALLOC(labels, opcode, arg);
            default      -> // force an exception
                            throw new IllegalArgumentException("Parser.makeInstruction(): "
                                + "opcode not handled at position " + opcode.getPosition());
          };
      }

    // utility parsing methods

    private void checkOpcode() throws ParserException
      {
        if (!scanner.getSymbol().isOpcode())
          {
            var errorMsg = "Expecting an opcode but found \"" + scanner.getSymbol() + "\" instead";
            throw error(errorMsg);
          }
      }

    private void checkArgs(Token opcode, Token arg) throws ParserException
      {
        Symbol symbol  = opcode.getSymbol();
        int    numArgs = symbol.getNumArgs();

        if (numArgs == 0)
          {
            if (arg != null)
              {
                var errorMsg = "No arguments allowed for this opcode.";
                throw error(opcode.getPosition(), errorMsg);
              }
          }
        else if (numArgs == 1)
          {
            if (arg == null)
              {
                var errorMsg = "One argument is required for this opcode.";
                throw error(opcode.getPosition(), errorMsg);
              }
          }
        else
          {
            var errorMsg = "Invalid number of arguments for opcode " + opcode + ".";
            throw error(opcode.getPosition(), errorMsg);
          }
      }

    private void matchEOF() throws ParserException
      {
        if (scanner.getSymbol() != Symbol.EOF)
          {
            var errorMsg = "Expecting \"" + Symbol.EOF + "\" but found \""
                         + scanner.getSymbol() + "\" instead";
            throw error(errorMsg);
          }
      }

    private void matchCurrentSymbol() throws IOException
      {
        scanner.advance();
      }

    /**
     * Create a parser exception with the specified message and the
     * current scanner position.
     */
    private ParserException error(String errorMsg)
      {
        var errorPos = scanner.getPosition();
        return new ParserException(errorPos, errorMsg);
      }

    /**
     * Create a parser exception with the specified error position and message.
     */
    private ParserException error(Position errorPos, String errorMsg)
      {
        return new ParserException(errorPos, errorMsg);
      }
  }
