package edu.citadel.cvm.assembler.ast;

import edu.citadel.cvm.OpCode;
import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import java.util.List;
import java.io.IOException;

/**
 * This class implements the abstract syntax tree for the assembly
 * language instruction PUTEOL.
 */
public class InstructionPUTEOL extends InstructionNoArgs
  {
    public InstructionPUTEOL(List<Token> labels, Token opCode)
      {
        super(labels, opCode);
      }

    @Override
    public void assertOpCode()
      {
        assertOpCode(Symbol.PUTEOL);
      }

    @Override
    public void emit() throws IOException
      {
        emit(OpCode.PUTEOL);
      }
  }
