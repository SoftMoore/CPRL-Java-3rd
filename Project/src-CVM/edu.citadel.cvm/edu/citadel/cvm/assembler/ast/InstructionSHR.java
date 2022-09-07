package edu.citadel.cvm.assembler.ast;


import edu.citadel.compiler.ConstraintException;
import edu.citadel.cvm.OpCode;
import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import java.util.List;
import java.io.IOException;


/**
 * This class implements the abstract syntax tree for the assembly
 * language instruction SHR.
 */
public class InstructionSHR extends InstructionOneArg
  {
    public InstructionSHR(List<Token> labels, Token opCode, Token arg)
      {
        super(labels, opCode, arg);
      }


    @Override
    public void assertOpCode()
      {
        assertOpCode(Symbol.SHR);
      }


    @Override
    public void checkArgType() throws ConstraintException
      {
        checkArgType(Symbol.intLiteral);

        // check that the value is in the range 0..31
        int argValue = argToInt();
        if (argValue < 0 || argValue > 31)
          {
            String errorMsg = "Shift amount must be be in the range 0..31";
            throw error(getArg().getPosition(), errorMsg);
          }
      }


    @Override
    public int getArgSize()
      {
        return 1;   // 1 byte
      }


    @Override
    public void emit() throws IOException
      {
        emit(OpCode.SHR);
        emit(argToByte());
      }
  }
