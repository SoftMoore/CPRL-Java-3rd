package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.Position;
import edu.citadel.cprl.Type;

/**
 * Base class for all CPRL expressions.
 */
public abstract class Expression extends AST
  {
    /** constant for false */
    public static final String FALSE = "0";

    /** constant for true */
    public static final String TRUE = "1";

    private Type type;
    private Position position;   // position of the expression

    /**
     * Construct an expression with the specified type and position.
     */
    public Expression(Type type, Position position)
      {
        this.type = type;
        this.position = position;
      }

    /**
     * Construct an expression with the specified position.  Initializes
     * the type of the expression to UNKNOWN.
     */
    public Expression(Position position)
      {
        this(Type.UNKNOWN, position);
      }

    /**
     * Returns the type of this expression.
     */
    public Type getType()
      {
        return type;
      }

    /**
     * Sets the type of this expression.
     */
    public void setType(Type exprType)
      {
        this.type = exprType;
      }

    /**
     * Returns the position of this expression.
     */
    public Position getPosition()
      {
        return position;
      }

    /**
     * For Boolean expressions, this method emits the appropriate branch opcode
     * based on the condition.  For example, if the expression is a "&lt;"
     * relational expression and the condition is false, then the opcode "BGE"
     * is emitted.  The method defined in this class works correctly for most
     * Boolean expressions, but it should be overridden for relational expressions.
     *
     * @param condition the condition that determines the branch to be emitted.
     * @param label     the label for the branch destination.
     */
    public void emitBranch(boolean condition, String label) throws CodeGenException
      {
        // default behavior unless overridden; correct for constants and variable expressions
        assert type == Type.Boolean : "Expression type is not Boolean.";
        emit();   // leaves boolean expression value on top of stack
        emit(condition ? "BNZ " + label : "BZ " + label);
      }
  }
