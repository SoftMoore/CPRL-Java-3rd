package edu.citadel.assembler.ast;

import edu.citadel.compiler.ConstraintException;
import edu.citadel.cvm.Opcode;
import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import java.util.List;
import java.io.IOException;

/**
 * This class implements the abstract syntax tree for the assembly
 * language instruction SHL.
 */
public class InstructionSHL extends InstructionOneArg
  {
    public InstructionSHL(List<Token> labels, Token opcode, Token arg)
      {
        super(labels, opcode, arg);
      }

    @Override
    public void assertOpcode()
      {
        assertOpcode(Symbol.SHL);
      }

    @Override
    public void checkArgType() throws ConstraintException
      {
        checkArgType(Symbol.intLiteral);

        // check that the value is in the range 0..31
        int argValue = argToInt();
        if (argValue < 0 || argValue > 31)
          {
            var errorMsg = "Shift amount must be be in the range 0..31";
            throw error(getArg().getPosition(), errorMsg);
          }
      }

    @Override
    protected int getArgSize()
      {
        return 1; // 1 byte
      }

    @Override
    public void emit() throws IOException
      {
        emit(Opcode.SHL);
        emit(argToByte());
      }
  }
