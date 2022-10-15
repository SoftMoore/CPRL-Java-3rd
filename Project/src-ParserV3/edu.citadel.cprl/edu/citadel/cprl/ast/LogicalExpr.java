package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a logical expression.  A logical expression
 * is a binary expression where the operator is either "and" or "or".  A simple
 * example would be "(x &gt; 5) and (y &lt; 0)".
 */
public class LogicalExpr extends BinaryExpr
  {
    // labels used during code generation for short-circuit version
    private String L1 = getNewLabel();   // label at start of right operand
    private String L2 = getNewLabel();   // label at end of logical expression

    /**
     * Construct a logical expression with the operator ("and" or "or")
     * and the two operands.
     */
    public LogicalExpr(Expression leftOperand, Token operator, Expression rightOperand)
      {
        super(leftOperand, operator, rightOperand);
        setType(Type.Boolean);
        assert operator.getSymbol().isLogicalOperator() : "Operator is not a logical operator.";
      }

    @Override
    public void checkConstraints()
      {
        try
          {
            Expression leftOperand  = getLeftOperand();
            Expression rightOperand = getRightOperand();

            leftOperand.checkConstraints();
            rightOperand.checkConstraints();

            if (leftOperand.getType() != Type.Boolean)
              {
                String errorMsg = "Left operand for a logical expression "
                                + "should have type Boolean.";
                throw error(leftOperand.getPosition(), errorMsg);
              }

            if (rightOperand.getType() != Type.Boolean)
              {
                String errorMsg = "Right operand for a logical expression "
                                + "should have type Boolean.";
                throw error(rightOperand.getPosition(), errorMsg);
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
        // Uses short-circuit evaluation for logical expressions.
        Expression leftOperand  = getLeftOperand();
        Expression rightOperand = getRightOperand();
        Token      operator     = getOperator();

        Symbol operatorSym = operator.getSymbol();

        if (operatorSym == Symbol.andRW)
          {
            // if left operand evaluates to true, branch
            // to code that will evaluate right operand
            leftOperand.emitBranch(true, L1);

            // otherwise, place "false" back on top of stack as value
            // for the compound "and" expression
            emit("LDCB " + FALSE);
          }
        else   // operatorSym must be Symbol.orRW
          {
            // if left operand evaluates to false, branch
            // to code that will evaluate right operand
            leftOperand.emitBranch(false, L1);

            // otherwise, place "true" back on top of stack as value
            // for the compound "or" expression
            emit("LDCB " + TRUE);
          }

        // branch to code following the expression
        emit("BR " + L2);

        // L1:
        emitLabel(L1);

        // evaluate the right operand and leave result on
        // top of stack as value for compound expression
        rightOperand.emit();

        // L2:
        emitLabel(L2);
      }
  }
