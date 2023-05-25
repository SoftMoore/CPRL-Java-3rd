package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a not expression.  A not expression is a unary
 * expression of the form "not expr".  A simple example would be "not isEmpty()".
 */
public class NotExpr extends UnaryExpr
  {
    /**
     * Construct a not expression with the specified operator ("not") and operand.
     */
    public NotExpr(Token operator, Expression operand)
      {
        super(operator, operand);
        setType(Type.Boolean);
        assert operator.getSymbol() == Symbol.notRW :
            "Operator is not the reserved word \"not\".";
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
