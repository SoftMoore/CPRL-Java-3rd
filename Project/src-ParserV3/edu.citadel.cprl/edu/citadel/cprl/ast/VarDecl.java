package edu.citadel.cprl.ast;


import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.cprl.ScopeLevel;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

import java.util.List;
import java.util.ArrayList;


/**
 * The abstract syntax tree node for a variable declaration.  Note that a
 * variable declaration is simply a container for a list of single variable
 * declarations (SingleVarDecls).
 */
public class VarDecl extends InitialDecl
  {
    private ConstValue initialValue;

    // the list of single variable declarations for this variable declaration
    private List<SingleVarDecl> singleVarDecls;


    /**
     * Construct a variable declaration with its list of identifier tokens,
     * type, initial value, and scope level
     */
    public VarDecl(List<Token> identifiers, Type varType,
                   ConstValue initialValue, ScopeLevel scopeLevel)
      {
        super(new Token(), varType);
        this.initialValue = initialValue;
        
        singleVarDecls = new ArrayList<>(identifiers.size());
        for (Token id : identifiers)
            singleVarDecls.add(new SingleVarDecl(id, varType, initialValue, scopeLevel));
      }


    /**
     * Returns the list of single variable declarations for this variable declaration.
     */
    public List<SingleVarDecl> getSingleVarDecls()
      {
        return singleVarDecls;
      }


    @Override
    public void checkConstraints()
      {
        try
          {
            for (SingleVarDecl singleVarDecl : singleVarDecls)
                singleVarDecl.checkConstraints();

            if (initialValue != null && !matchTypes(getType(), initialValue))
              {
                String errorMsg = "Type mismatch for variable initialization.";
                throw error(initialValue.getPosition(), errorMsg);
              }
          }
        catch (ConstraintException e)
          {
            getErrorHandler().reportError(e);
          }
      }
    
    
    @Override
    public void emit() throws CodeGenException
      {
        for (SingleVarDecl singleVarDecl : singleVarDecls)
            singleVarDecl.emit();
      }
  }
