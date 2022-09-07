package edu.citadel.cprl.ast;


import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.Token;
import edu.citadel.cprl.StringType;

import java.util.List;


/**
 * The abstract syntax tree node for a procedure call statement.
 */
public class ProcedureCallStmt extends Statement
  {
    private Token procId;
    private List<Expression> actualParams;

    // declaration of the procedure being called
    private ProcedureDecl procDecl;   // nonstructural reference


    /*
     * Construct a procedure call statement with the procedure name
     * (an identifier token) and the list of actual parameters being
     * passed as part of the call.
     */
    public ProcedureCallStmt(Token procId, List<Expression> actualParams)
      {
        this.procId = procId;
        this.actualParams = actualParams;
      }


    @Override
    public void checkConstraints()
      {
// ...
      }


    @Override
    public void emit() throws CodeGenException
      {
// ...
      }
  }
