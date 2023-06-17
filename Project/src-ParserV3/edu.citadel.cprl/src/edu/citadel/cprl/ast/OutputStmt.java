package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.StringType;
import edu.citadel.cprl.Type;

import java.util.List;

/**
 * This class implements both write and writeln statements.
 */
public class OutputStmt extends Statement
  {
    private List<Expression> expressions;
    private boolean isWriteln;

    /**
     * Construct an output statement with the list of expressions and isWriteln flag.
     */
    public OutputStmt(List<Expression> expressions, boolean isWriteln)
      {
        this.expressions = expressions;
        this.isWriteln   = isWriteln;
      }

    /**
     * Construct an output statement with the list of expressions.
     * The isWriteln flag is initialized to false.
     */
    public OutputStmt(List<Expression> expressions)
      {
        this(expressions, false);
      }

    /**
     * Returns the list of expressions for this output statement.
     */
    public List<Expression> getExpressions()
      {
        return expressions;
      }

    @Override
    public void checkConstraints()
      {
        for (Expression expr : expressions)
          {
            expr.checkConstraints();

            try
              {
                Type type = expr.getType();

                if (type != Type.Integer && type != Type.Boolean
                    && type != Type.Char && !(type instanceof StringType))
                  {
                    var errorMsg = "Output supported only for integers, "
                                 + "characters, booleans, and strings.";
                    throw error(expr.getPosition(), errorMsg);
                  }
              }
            catch (ConstraintException e)
              {
                getErrorHandler().reportError(e);
              }
          }
      }

    @Override
    public void emit() throws CodeGenException
      {
        for (Expression expr : expressions)
          {
            expr.emit();

            var type = expr.getType();

            if (type == Type.Integer)
                emit("PUTINT");
            else if (type == Type.Boolean)
                emit("PUTBYTE");
            else if (type == Type.Char)
                emit("PUTCH");
            else   // must be a string type
                emit("PUTSTR " + ((StringType) type).getCapacity());
          }

        if (isWriteln)
            emit("PUTEOL");
      }
  }
