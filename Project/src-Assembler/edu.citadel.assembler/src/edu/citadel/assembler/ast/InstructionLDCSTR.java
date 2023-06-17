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
 * language instruction LDCSTR. <br>
 *
 * Note: Only one argument (the string literal) is specified for this instruction
 * in assembly language, but two args are generated for the CVM machine code.
 */
public class InstructionLDCSTR extends InstructionOneArg
  {
    public InstructionLDCSTR(List<Token> labels, Token opcode, Token arg)
      {
        super(labels, opcode, arg);
      }

    @Override
    public void assertOpcode()
      {
        assertOpcode(Symbol.LDCSTR);
      }

    @Override
    public void checkArgType() throws ConstraintException
      {
        checkArgType(Symbol.stringLiteral);
      }

    private int getStrLength()
      {
        // need to subtract 2 to handle the opening and closing quotes
        return getArg().getText().length() - 2;
      }

    @Override
    protected int getArgSize()
      {
        // Note: We must return the size for both the integer arg and
        // the string arg that will be generated in machine code.
        return Constants.BYTES_PER_INTEGER + Constants.BYTES_PER_CHAR*getStrLength();
      }

    @Override
    public void emit() throws IOException
      {
        int strLength = getStrLength();

        emit(Opcode.LDCSTR);
        emit(strLength);

        String text = getArg().getText();

        // omit opening and closing quotes
        for (int i = 1; i <= strLength; ++i)
            emit(text.charAt(i));
      }
  }
