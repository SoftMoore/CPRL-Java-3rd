package edu.citadel.cprl.ast;


import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.Position;


/**
 * The abstract syntax tree node for a return statement.
 */
public class ReturnStmt extends Statement
  {
    private SubprogramDecl subprogramDecl;   // nonstructural reference
    private Expression     returnExpr;       // may be null

    // position of the return token (needed for error reporting)
    private Position returnPosition;


    /**
     * Construct a return statement with a reference to the enclosing subprogram
     * and the expression for the value being returned, which may be null.
     */
    public ReturnStmt(SubprogramDecl subprogramDecl, Expression returnExpr, Position returnPosition)
      {
        this.returnExpr     = returnExpr;
        this.subprogramDecl = subprogramDecl;
        this.returnPosition = returnPosition;
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
