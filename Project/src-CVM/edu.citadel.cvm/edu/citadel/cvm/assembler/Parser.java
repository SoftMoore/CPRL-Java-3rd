package edu.citadel.cvm.assembler;


import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.ParserException;
import edu.citadel.compiler.Position;
import edu.citadel.cvm.assembler.ast.*;

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
    /**
     * Symbols that can follow an assembly language instruction.
     */
    private Set<Symbol> instructionFollowers = makeInstructionFollowers();

    private Scanner scanner;
    private ErrorHandler errorHandler;


    /**
     * Returns a set of symbols that can follow an instruction.
     */
    private Set<Symbol> makeInstructionFollowers()
      {
        EnumSet<Symbol> followers = EnumSet.noneOf(Symbol.class);

        // add all opcodes
        for (Symbol symbol : Symbol.values())
          {
            if (symbol.isOpCode())
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
        Program program = new Program();

        try
          {
            // NOTE: Identifier is not a valid starter for an instruction,
            // but we handle it as a special case in order to give better
            // error reporting/recovery when an opcode mnemonic is misspelled
            Symbol symbol = scanner.getSymbol();
            while (symbol.isOpCode()
                || symbol == Symbol.labelId
                || symbol == Symbol.identifier)
              {
                Instruction instruction = parseInstruction();
                if (instruction != null)
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


    // instruction = { labelId } opCodeMnemonic [ arg ] .
    private Instruction parseInstruction() throws IOException
      {
        try
          {
            List<Token> labels = new ArrayList<>();

            if (scanner.getSymbol() == Symbol.labelId)
              {
                while (scanner.getSymbol() == Symbol.labelId)
                  {
                    labels.add(scanner.getToken());
                    matchCurrentSymbol();
                  }
              }

            // return null when a label is followed by EOF
            if (scanner.getSymbol() == Symbol.EOF)
                return null;
            else
              {
                checkOpCode();
                Token opCode = scanner.getToken();
                matchCurrentSymbol();

                Token arg = null;
                int numArgs = opCode.getSymbol().getNumArgs();
                if (numArgs == 1)
                  {
                    arg = scanner.getToken();
                    matchCurrentSymbol();
                  }

                return makeInstruction(labels, opCode, arg);
              }
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            scanner.advanceTo(instructionFollowers);
            return null;
          }
      }


    private Instruction makeInstruction(List<Token> labels, Token opCode, Token arg)
        throws ParserException
      {
        checkArgs(opCode, arg);

        switch (opCode.getSymbol())
          {
            case HALT:    return new InstructionHALT(labels, opCode);
            case LOAD:    return new InstructionLOAD(labels, opCode, arg);
            case LOADB:   return new InstructionLOADB(labels, opCode);
            case LOAD2B:  return new InstructionLOAD2B(labels, opCode);
            case LOADW:   return new InstructionLOADW(labels, opCode);
            case LOADSTR: return new InstructionLOADSTR(labels, opCode);
            case LDCB:    return new InstructionLDCB(labels, opCode, arg);
            case LDCB0:   return new InstructionLDCB0(labels, opCode);
            case LDCB1:   return new InstructionLDCB1(labels, opCode);
            case LDCCH:   return new InstructionLDCCH(labels, opCode, arg);
            case LDCINT:  return new InstructionLDCINT(labels, opCode, arg);
            case LDCINT0: return new InstructionLDCINT0(labels, opCode);
            case LDCINT1: return new InstructionLDCINT1(labels, opCode);
            case LDCSTR:  return new InstructionLDCSTR(labels, opCode, arg);
            case LDLADDR: return new InstructionLDLADDR(labels, opCode, arg);
            case LDGADDR: return new InstructionLDGADDR(labels, opCode, arg);
            case STORE:   return new InstructionSTORE(labels, opCode, arg);
            case STOREB:  return new InstructionSTOREB(labels, opCode);
            case STORE2B: return new InstructionSTORE2B(labels, opCode);
            case STOREW:  return new InstructionSTOREW(labels, opCode);
            case STOREST: return new InstructionSTOREST(labels, opCode);
            case BR:      return new InstructionBR(labels, opCode, arg);
            case BE:      return new InstructionBE(labels, opCode, arg);
            case BNE:     return new InstructionBNE(labels, opCode, arg);
            case BG:      return new InstructionBG(labels, opCode, arg);
            case BGE:     return new InstructionBGE(labels, opCode, arg);
            case BL:      return new InstructionBL(labels, opCode, arg);
            case BLE:     return new InstructionBLE(labels, opCode, arg);
            case BZ:      return new InstructionBZ(labels, opCode, arg);
            case BNZ:     return new InstructionBNZ(labels, opCode, arg);
            case SHL:     return new InstructionSHL(labels, opCode, arg);
            case SHR:     return new InstructionSHR(labels, opCode, arg);
            case NOT:     return new InstructionNOT(labels, opCode);
            case ADD:     return new InstructionADD(labels, opCode);
            case SUB:     return new InstructionSUB(labels, opCode);
            case MUL:     return new InstructionMUL(labels, opCode);
            case DIV:     return new InstructionDIV(labels, opCode);
            case MOD:     return new InstructionMOD(labels, opCode);
            case NEG:     return new InstructionNEG(labels, opCode);
            case INC:     return new InstructionINC(labels, opCode);
            case DEC:     return new InstructionDEC(labels, opCode);
            case GETCH:   return new InstructionGETCH(labels, opCode);
            case GETINT:  return new InstructionGETINT(labels, opCode);
            case GETSTR:  return new InstructionGETSTR(labels, opCode, arg);
            case PUTBYTE: return new InstructionPUTBYTE(labels, opCode);
            case PUTCH:   return new InstructionPUTCH(labels, opCode);
            case PUTINT:  return new InstructionPUTINT(labels, opCode);
            case PUTEOL:  return new InstructionPUTEOL(labels, opCode);
            case PUTSTR:  return new InstructionPUTSTR(labels, opCode, arg);
            case PROGRAM: return new InstructionPROGRAM(labels, opCode, arg);
            case PROC:    return new InstructionPROC(labels, opCode, arg);
            case CALL:    return new InstructionCALL(labels, opCode, arg);
            case RET:     return new InstructionRET(labels, opCode, arg);
            case RET0:    return new InstructionRET0(labels, opCode);
            case RET4:    return new InstructionRET4(labels, opCode);
            case ALLOC:   return new InstructionALLOC(labels, opCode, arg);
            default:
                // force an exception
                throw new IllegalArgumentException("Parser.makeInstruction(): "
                        + "opcode not handled at position " + opCode.getPosition());
          }
      }


    // utility parsing methods

    private void checkOpCode() throws ParserException
      {
        if (!scanner.getSymbol().isOpCode())
          {
            String errorMsg = "Expecting an opcode but found \""
                + scanner.getSymbol() + "\" instead";
            throw error(errorMsg);
          }
      }


    private void checkArgs(Token opCode, Token arg) throws ParserException
      {
        Symbol symbol = opCode.getSymbol();
        int numArgs = symbol.getNumArgs();

        if (numArgs == 0)
          {
            if (arg != null)
              {
                String errorMsg = "No arguments allowed for this opcode.";
                throw error(opCode.getPosition(), errorMsg);
              }
          }
        else if (numArgs == 1)
          {
            if (arg == null)
              {
                String errorMsg = "One argument is required for this opcode.";
                throw error(opCode.getPosition(), errorMsg);
              }
          }
        else
          {
            String errorMsg = "Invalid number of arguments for opcode " + opCode  +".";
            throw error(opCode.getPosition(), errorMsg);
          }
      }


    private void matchEOF() throws ParserException
      {
        if (scanner.getSymbol() != Symbol.EOF)
          {
            String errorMsg = "Expecting \"" + Symbol.EOF + "\" but found \""
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
        Position errorPos = scanner.getPosition();
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
