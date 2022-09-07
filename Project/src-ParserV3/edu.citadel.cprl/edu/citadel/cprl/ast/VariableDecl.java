package edu.citadel.cprl.ast;


import edu.citadel.cprl.ScopeLevel;
import edu.citadel.cprl.Type;


/**
 * Interface for a variable declaration, which can be either a
 * "single" variable declaration or a parameter declaration.
 */
sealed public interface VariableDecl permits SingleVarDecl, ParameterDecl
  {
    /**
     * Returns the type of this declaration.
     */
    public Type getType();


    /**
     * Returns the size (number of bytes) of the variable
     * declared with this declaration.
     */
    public int getSize();


    /**
     * Returns the scope level for this declaration.
     */
    public ScopeLevel getScopeLevel();


    /**
     * Sets the relative address (offset) of the variable
     * declared with this declaration.
     */
    public void setRelAddr(int relAddr);


    /**
     * Returns the relative address (offset) of the variable
     * declared with this declaration.
     */
    public int getRelAddr();
  }
