package edu.citadel.cvm.assembler.ast;

import edu.citadel.compiler.ConstraintException;
import edu.citadel.cvm.Constants;
import edu.citadel.cvm.OpCode;
import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import java.util.List;
import java.io.IOException;

/**
 * This class implements the abstract syntax tree for the assembly
 * language instruction GETSTR.
 */
public class InstructionGETSTR extends InstructionOneArg
  {
    public InstructionGETSTR(List<Token> labels, Token opCode, Token arg)
      {
        super(labels, opCode, arg);
      }

    @Override
    public void assertOpCode()
      {
        assertOpCode(Symbol.GETSTR);
      }

    @Override
    public void checkArgType() throws ConstraintException
      {
        checkArgType(Symbol.intLiteral);
      }

    @Override
    protected int getArgSize()
      {
        return Constants.BYTES_PER_INTEGER;
      }

    @Override
    public void emit() throws IOException
      {
        emit(OpCode.GETSTR);
        emit(argToInt());
      }
  }
