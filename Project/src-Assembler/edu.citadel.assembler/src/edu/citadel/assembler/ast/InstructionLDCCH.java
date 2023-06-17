package edu.citadel.assembler.ast;

import edu.citadel.compiler.ConstraintException;
import edu.citadel.cvm.Constants;
import edu.citadel.cvm.Opcode;
import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import java.util.List;
import java.io.IOException;

/**
 * This class implements the abstract syntax tree for the assembly
 * language instruction LDCCH.
 */
public class InstructionLDCCH extends InstructionOneArg
  {
    public InstructionLDCCH(List<Token> labels, Token opcode, Token arg)
      {
        super(labels, opcode, arg);
      }

    @Override
    public void assertOpcode()
      {
        assertOpcode(Symbol.LDCCH);
      }

    @Override
    public void checkArgType() throws ConstraintException
      {
        checkArgType(Symbol.charLiteral);
      }

    @Override
    protected int getArgSize()
      {
        return Constants.BYTES_PER_CHAR;
      }

    @Override
    public void emit() throws IOException
      {
        char arg = getArg().getText().charAt(1);
        emit(Opcode.LDCCH);
        emit(arg);
      }
  }
