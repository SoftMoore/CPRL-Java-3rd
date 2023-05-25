package edu.citadel.cprl.ast;

import edu.citadel.cprl.Token;

/**
 * Base class for all binary expressions.  A binary expression is an expression
 * of the form "expression operator expression".  The first expression is called
 * the left operand, and the second expression is called the right operand.
 */
public abstract class BinaryExpr extends Expression
  {
    private Expression leftOperand;
    private Token      operator;
    private Expression rightOperand;

    /**
     * Construct a binary expression with its left operand, operator, and right operand.
     */
    public BinaryExpr(Expression leftOperand, Token operator, Expression rightOperand)
      {
        super(operator.getPosition());

        this.leftOperand  = leftOperand;
        this.operator     = operator;
        this.rightOperand = rightOperand;
      }

    /**
     * Returns the left operand of the binary expression.
     */
    public Expression getLeftOperand()
      {
        return leftOperand;
      }

    /**
     * Returns a token representing the operator of the binary expression.
     */
    public Token getOperator()
      {
        return operator;
      }

    /**
     * Returns the right operand of the binary expression.
     */
    public Expression getRightOperand()
      {
        return rightOperand;
      }
  }
