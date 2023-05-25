package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for an if statement.
 */
public class IfStmt extends Statement
  {
    private Expression booleanExpr;
    private Statement  thenStmt;      // statement following "then"
    private Statement  elseStmt;      // statement following "else"

    // labels used during code generation
    private String L1 = getNewLabel();   // label of address at end of then statement
    private String L2 = getNewLabel();   // label of address at end of if statement

    /**
     * Construct an if statement with the specified boolean expression,
     * "then" statement, and "else" statement (which can be null).
     *
     * @param booleanExpr the boolean expression that, if true, will result
     *                    in the execution of the list of "then" statements.
     * @param thenStmt    the statement to be executed when the boolean
     *                    expression evaluates to true.
     * @param elseStmt    the statement to be executed when the boolean
     *                    expression evaluates to false (can be null).
     */
    public IfStmt(Expression booleanExpr, Statement thenStmt, Statement elseStmt)
      {
        this.booleanExpr = booleanExpr;
        this.thenStmt    = thenStmt;
        this.elseStmt    = elseStmt;
      }

    /**
     * Returns the "then" statement for this if statement.
     */
    public Statement getThenStmt()
      {
        return thenStmt;
      }

    /**
     * Returns the "else" statement for this if statement.
     */
    public Statement getElseStmt()
      {
        return elseStmt;
      }

    @Override
    public void checkConstraints()
      {
        try
          {
            booleanExpr.checkConstraints();
            thenStmt.checkConstraints();
            if (elseStmt != null)
                elseStmt.checkConstraints();

            if (booleanExpr.getType() != Type.Boolean)
              {
                String errorMsg = "An \"if\" condition should have type Boolean.";
                throw error(booleanExpr.getPosition(), errorMsg);
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
        // if expression evaluates to false, branch to L1
        booleanExpr.emitBranch(false, L1);

        thenStmt.emit();

        // if there is an else part, branch to end of if statement
        if (elseStmt != null)
            emit("BR " + L2);

        // L1:
        emitLabel(L1);

        if (elseStmt != null)
          {
            elseStmt.emit();

            // L2:
            emitLabel(L2);
          }
      }
  }
