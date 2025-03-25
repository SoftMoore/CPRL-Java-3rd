package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;

import edu.citadel.cprl.ScopeLevel;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a parameter declaration.
 */
public final class ParameterDecl extends Declaration implements VariableDecl
  {
    private int     relAddr;      // relative address for this declaration
    private boolean isVarParam;   // true if this is a variable parameter

    /**
     * Construct a parameter declaration with its identifier, type, and a boolean
     * value that indicates if it is a variable parameter declaration.
     */
    public ParameterDecl(Token paramId, Type type, boolean isVarParam)
      {
        super(paramId, type);
        this.isVarParam = isVarParam;
      }

    /**
     * Returns the size (number of bytes) associated with this parameter
     * declaration.  The size of a value parameter declaration is the number
     * of bytes associated with its type.  For variable parameters, the
     * size is the number of bytes needed for a memory address.
     */
    public int getSize()
      {
        return isVarParam ? Type.Address.getSize() : getType().getSize();
      }

    @Override
    public ScopeLevel getScopeLevel()
      {
        return ScopeLevel.LOCAL;   // always LOCAL for a parameter
      }

    /**
     * Sets the relative address for this declaration. <br>
     * Note: This method should be called before calling method getRelAddr().
     */
    public void setRelAddr(int relAddr)
      {
        this.relAddr = relAddr;
      }

    /**
     * Returns the relative address (offset) associated with this parameter declaration.
     */
    public int getRelAddr()
      {
        return relAddr;
      }

    /**
     * Returns true if this parameter is a variable parameter.
     */
    public boolean isVarParam()
      {
        return isVarParam;
      }

    @Override
    public void checkConstraints()
      {
        assert getType() != null && getType() != Type.UNKNOWN && getType() != Type.none
            : "Invalid CPRL type in parameter declaration.";
      }

    @Override
    public void emit() throws CodeGenException
      {
        // nothing to emit for parameter declarations
      }
  }
