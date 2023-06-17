package edu.citadel.assembler.ast;

import edu.citadel.compiler.ConstraintException;

import edu.citadel.cvm.Constants;
import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This abstract class implements common methods for the abstract
 * syntax tree of a single assembly language instruction.
 */
public abstract class Instruction extends AST
  {
    // Maps label text (type String) to an address (type Integer).
    // Note that the label text always includes the colon (:) at the end.
    protected static Map<String, Integer> labelMap = new HashMap<>();

    // Maps identifier text (type String) to a stack address (type Integer).
    protected static Map<String, Integer> idMap = new HashMap<>();

    private List<Token> labels;
    private Token opcode;

    private int address;

    /**
     * Construct an instruction with a list of labels and an opcode.
     */
    public Instruction(List<Token> labels, Token opcode)
      {
        this.labels = labels;
        this.opcode = opcode;
      }

    /**
     * Initialize static maps.  These maps are shared with all instructions,
     * but they must be re-initialized if the assembler is run on more than
     * one file; e.g., via a command like assemble *.asm.
     */
    public static void initMaps()
      {
        labelMap = new HashMap<>();
        idMap    = new HashMap<>();
      }

    public List<Token> getLabels()
      {
        return labels;
      }

    public Token getOpcode()
      {
        return opcode;
      }

    /**
     * Sets the memory address and defines label values for this instruction.
     */
    public void setAddress(int address) throws ConstraintException
      {
        this.address = address;

        // define addresses for labels
        for (Token label : labels)
          {
            if (labelMap.containsKey(label.getText()))
              {
                var errorMsg = "Label \"" + label + "\" has already been defined.";
                throw error(label.getPosition(), errorMsg);
              }
            else
                labelMap.put(label.getText(), Integer.valueOf(address));
          }
      }

    /**
     * Returns the address of this instruction.
     */
    public int getAddress()
      {
        return address;
      }

    /**
     * Returns the stack address associated with an identifier.
     */
    protected int getIdAddress(Token identifier)
      {
        assert identifier != null : "Identifier can't be null.";
        assert identifier.getSymbol() == Symbol.identifier :
            "Expecting an identifier but found " + identifier.getSymbol() + ".";
        assert idMap.containsKey(identifier.getText()) :
            "Identifier " + identifier.getText() + " not found.";

        var idAddress = (Integer) idMap.get(identifier.getText());

        return idAddress.intValue();
      }

    /**
     * Returns the number of bytes in memory occupied by the argument.
     */
    protected abstract int getArgSize();

    /**
     * Returns the number of bytes in memory occupied by the instruction,
     * computed as 1 (for the opcode) plus the sizes of the operands.
     */
    public int getSize()
      {
        return Constants.BYTES_PER_OPCODE + getArgSize();
      }

    /**
     * Checks that each label has a value defined in the label map.  This method
     * should not be called for an instruction before method setAddress().
     *
     * @throws ConstraintException  if the instruction has a label that
     *                              is not defined in the label map.
     */
    protected void checkLabels() throws ConstraintException
      {
        for (Token label : labels)
          {
            if (!labelMap.containsKey(label.getText()))
              {
                var errorMsg = "label \"" + label.getText() + "\" has not been defined.";
                throw error(label.getPosition(), errorMsg);
              }
          }
      }

    /**
     * Calculates the displacement between an instruction's address and
     * a label (computed as label's address - instruction's address).
     * This method is used by branching and call instructions.
     */
    protected int getDisplacement(Token labelArg)
      {
        var labelId = labelArg.getText() + ":";

        assert labelMap.containsKey(labelId) : "Label " + labelArg.getText() + " not found.";

        var labelAddress = (Integer) labelMap.get(labelId);
        return labelAddress - (address + getSize());
      }

    /**
     * Asserts that the opcode token of the instruction has
     * the correct Symbol.  Implemented in each instruction
     * by calling the method assertOpcode(Symbol).
     */
    protected abstract void assertOpcode();

    protected void assertOpcode(Symbol opcode)
      {
        assert this.opcode != null : "Null opcode.";
        assert this.opcode.getSymbol() == opcode : "Wrong opcode.";
      }

    @Override
    public String toString()
      {
        var buffer = new StringBuffer(100);

        // for now simply print the instruction
        if (labels != null)
          {
            for (Token label : labels)
                buffer.append(label.getText() + "\n");
          }

        buffer.append("   " + opcode.getText());
        return buffer.toString();
      }
  }
