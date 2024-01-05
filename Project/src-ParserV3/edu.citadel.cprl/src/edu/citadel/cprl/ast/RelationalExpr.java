package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a relational expression.  A relational
 * expression is a binary expression where the operator is a relational
 * operator such as "&lt;=" or "&gt;".  A simple example would be "x &lt; 5".
 */
public class RelationalExpr extends BinaryExpr
  {
    // labels used during code generation
    private String L1 = getNewLabel();   // label at start of right operand
    private String L2 = getNewLabel();   // label at end of the relational expression

    /**
     * Construct a relational expression with the operator ("=", "&lt;=", etc.)
     * and the two operands.
     */
    public RelationalExpr(Expression leftOperand, Token operator, Expression rightOperand)
      {
        super(leftOperand, operator, rightOperand);
        setType(Type.Boolean);
        assert operator.getSymbol().isRelationalOperator() :
               "Operator is not a relational operator.";
      }

    @Override
    public void checkConstraints()
      {
        try
          {
            var leftOperand  = getLeftOperand();
            var rightOperand = getRightOperand();
            var operator     = getOperator();

            leftOperand.checkConstraints();
            rightOperand.checkConstraints();

            if (!leftOperand.getType().equals(rightOperand.getType()))
              {
                var errorMsg = "Type mismatch for left and right operands "
                             + "of a relational expression.";
                throw error(operator.getPosition(), errorMsg);
              }

            // check to make sure that we are comparing only operands with scalar types
            Type type = leftOperand.getType();
            if (!type.isScalar())
              {
                var errorMsg = "Operand types not permitted for a relational expression.";
                throw error(operator.getPosition(), errorMsg);
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
        emitBranch(false, L1);
        emit("LDCB " + TRUE);    // push true back on the stack
        emit("BR " + L2);        // jump over code to emit false
        emitLabel(L1);
        emit("LDCB " + FALSE);   // push false onto the stack
        emitLabel(L2);
      }

    @Override
    public void emitBranch(boolean condition, String label) throws CodeGenException
      {
        emitOperands();

        // rewritten to use switch instead of if/else if
        switch (getOperator().getSymbol())
          {
            case equals         -> emit(condition ? "BE "  + label : "BNE " + label);
            case notEqual       -> emit(condition ? "BNE " + label : "BE "  + label);
            case lessThan       -> emit(condition ? "BL "  + label : "BGE " + label);
            case lessOrEqual    -> emit(condition ? "BLE " + label : "BG "  + label);
            case greaterThan    -> emit(condition ? "BG "  + label : "BLE " + label);
            case greaterOrEqual -> emit(condition ? "BGE " + label : "BL "  + label);
            default ->
              {
                var position = getOperator().getPosition();
                var errorMsg = "Invalid relational operator.";
                throw new CodeGenException(position, errorMsg);
              }
          }
      }

    private void emitOperands() throws CodeGenException
      {
        var leftOperand  = getLeftOperand();
        var rightOperand = getRightOperand();

        // Relational operators compare integers only, so we need to make
        // sure that we have enough bytes on the stack.  Pad with zero bytes.
        for (int n = 1; n <= (Type.Integer.getSize() - leftOperand.getType().getSize()); ++n)
            emit("LDCB 0");

        leftOperand.emit();

        for (int n = 1; n <= (Type.Integer.getSize() - rightOperand.getType().getSize()); ++n)
            emit("LDCB 0");

        rightOperand.emit();
      }
  }
