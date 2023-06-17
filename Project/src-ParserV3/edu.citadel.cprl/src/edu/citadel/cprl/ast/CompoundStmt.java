package edu.citadel.cprl.ast;

import java.util.List;
import edu.citadel.compiler.CodeGenException;

/**
 * The abstract syntax tree node for a compound statement.
 */
public class CompoundStmt extends Statement
  {
    // the list of statements in the compound statement
    private List<Statement> statements;

    public CompoundStmt(List<Statement> statements)
      {
        this.statements = statements;
      }

    public List<Statement> getStatements()
      {
        return statements;
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
