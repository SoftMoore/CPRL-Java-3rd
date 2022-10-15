package edu.citadel.cvm.assembler.ast;

import edu.citadel.compiler.ConstraintException;
import edu.citadel.cvm.OpCode;
import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import java.util.List;
import java.io.IOException;

/**
 * This class implements the abstract syntax tree for the assembly
 * language instruction LDCB.
 */
public class InstructionLDCB extends InstructionOneArg
  {
    public InstructionLDCB(List<Token> labels, Token opCode, Token arg)
      {
        super(labels, opCode, arg);
      }

    @Override
    public void assertOpCode()
      {
        assertOpCode(Symbol.LDCB);
      }

    @Override
    public void checkArgType() throws ConstraintException
      {
        checkArgType(Symbol.intLiteral);
      }

    @Override
    public int getArgSize()
      {
        return 1;
      }

    @Override
    public void emit() throws IOException
      {
        emit(OpCode.LDCB);
        emit(argToByte());
      }
  }
