package edu.citadel.assembler.ast;

import edu.citadel.compiler.ConstraintException;
import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import java.util.List;

/**
 * This class serves as a base class for the abstract syntax
 * tree for an assembly language instruction with one argument.
 */
public abstract class InstructionOneArg extends Instruction
  {
    protected Token arg;

    /**
     * Construct a one-argument instruction with a list of labels, an opcode,
     * and an argument.
     */
    public InstructionOneArg(List<Token> labels, Token opcode, Token arg)
      {
        super(labels, opcode);
        this.arg = arg;
        assert arg != null : "Argument should never be null for this instruction.";
      }

    public Token getArg()
      {
        return arg;
      }

    /**
     * check semantic/contextual constraints
     */
    @Override
    public void checkConstraints()
      {
        try
          {
            assertOpcode();
            checkLabels();
            checkArgType();
          }
        catch (ConstraintException e)
          {
            getErrorHandler().reportError(e);
          }
      }

    /**
     * This method is called by instructions that have an argument that
     * references a label.  It verifies that the referenced label exists.
     */
    protected void checkLabelArgDefined() throws ConstraintException
      {
        if (arg.getSymbol() != Symbol.identifier)
          {
            var errorMsg = "Expecting a label identifier but found " + arg.getSymbol();
            throw error(arg.getPosition(), errorMsg);
          }

        String label = arg.getText() + ":";
        if (!labelMap.containsKey(label))
          {
            var errorMsg = "Label \"" + arg.getText() + "\" has not been defined.";
            throw error(arg.getPosition(), errorMsg);
          }
      }

    /**
     * This method is called by instructions to verify the type of its argument.
     */
    protected void checkArgType(Symbol argType) throws ConstraintException
      {
        if (arg.getSymbol() != argType)
          {
            var errorMsg = "Invalid type for argument -- should be " + argType + ".";
            throw error(arg.getPosition(), errorMsg);
          }
      }

    /**
     * Returns the argument as converted to an integer.  Valid
     * only for instructions with arguments of type intLiteral.
     */
    public int argToInt()
      {
        assert getArg().getSymbol() == Symbol.intLiteral :
            "Can't convert argument " + getArg() + " to an integer.";
        return Integer.parseInt(getArg().getText());
      }

    /**
     * Returns the argument as converted to a byte.  Valid
     * only for instructions with arguments of type intLiteral.
     */
    public byte argToByte()
      {
        assert getArg().getSymbol() == Symbol.intLiteral :
            "Can't convert argument " + getArg() + " to a byte.";
        return Byte.parseByte(getArg().getText());
      }

    @Override
    public String toString()
      {
        return super.toString() + " " + arg.getText();
      }

    /**
     * Checks that the argument of the instruction has
     * the correct type.  Implemented in each instruction
     * by calling the method checkArgType(Symbol).
     */
    protected abstract void checkArgType() throws ConstraintException;
  }
