package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a loop statement.
 */
public class LoopStmt extends Statement
  {
    private Expression whileExpr;
    private Statement  statement;

    // labels used during code generation
    private String L1 = getNewLabel();    // label for start of loop
    private String L2 = getNewLabel();    // label for end of loop

    /**
     * Default constructor.  Construct a loop statement with a null "while"
     * expression and an empty statement for the loop body.
     */
    public LoopStmt()
      {
        this.whileExpr = null;
        this.statement = EmptyStatement.getInstance();
      }

    /**
     * Set the while expression for this loop statement.
     */
    public void setWhileExpr(Expression whileExpr)
      {
        this.whileExpr = whileExpr;
      }

    /**
     * Returns the statement for the body of this loop statement.
     */
    public Statement getStatement()
      {
        return statement;
      }

    /**
     * Set the statement for the body of this loop statement.
     */
    public void setStatement(Statement statement)
      {
        this.statement = statement;
      }

    /**
     * Returns the label for the end of the loop statement.
     */
    public String getExitLabel()
      {
        return L2;
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
