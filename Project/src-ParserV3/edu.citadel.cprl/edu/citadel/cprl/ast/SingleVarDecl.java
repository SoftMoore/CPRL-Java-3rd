package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;

import edu.citadel.cprl.ScopeLevel;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a single variable declaration.
 * A single variable declaration has the form
 * <code>
 *    var x : Integer;
 * </code>
 * Note: A variable declaration where more than one variable is declared
 * is simply a container for multiple single variable declarations.
 */
public final class SingleVarDecl extends InitialDecl implements VariableDecl
  {
    private ConstValue initialValue;
    private ScopeLevel scopeLevel;
    private int relAddr;     // relative address for variable
                             // introduced by this declaration

    /**
     * Construct a single variable declaration with its identifier, type, initial
     * value, and scope level.
     */
    public SingleVarDecl(Token identifier, Type varType,
                         ConstValue initialValue, ScopeLevel scopeLevel)
      {
        super(identifier, varType);
        this.initialValue = initialValue;
        this.scopeLevel   = scopeLevel;
      }

    /**
     * Returns the size (number of bytes) associated with this single
     * variable declaration, which is simply the number of bytes associated
     * with its type.
     */
    public int getSize()
      {
        return getType().getSize();
      }

    @Override
    public ScopeLevel getScopeLevel()
      {
        return scopeLevel;
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
     * Returns the relative address (offset) associated with this single
     * variable declaration.
     */
    public int getRelAddr()
      {
        return relAddr;
      }

    @Override
    public void checkConstraints()
      {
        // nothing to do for now
      }

    @Override
    public void emit() throws CodeGenException
      {
        if (initialValue != null)
          {
            if (scopeLevel == ScopeLevel.GLOBAL)
                emit("LDGADDR " + relAddr);
            else
                emit("LDLADDR " + relAddr);

            initialValue.emit();
            emitStoreInst(initialValue.getType());
          }
      }
  }
