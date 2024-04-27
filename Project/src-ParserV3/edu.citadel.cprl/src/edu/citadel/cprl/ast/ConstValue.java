package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;
import edu.citadel.cprl.StringType;

/**
 * The abstract syntax tree node for a constant value expression, which is
 * either a literal or a declared constant identifier representing a literal.
 */
public class ConstValue extends Expression
  {
    /**
     * The literal token for the constant.  If the constant value is
     * created from a constant declaration, then the literal is the
     * one contained in the declaration.
     */
    private Token literal;

    // constant declaration containing the constant value
    private ConstDecl decl;   // nonstructural reference

    /**
     * Construct a constant value from a literal token.
     */
    public ConstValue(Token literal)
      {
        super(Type.typeOf(literal), literal.getPosition());
        this.literal = literal;
        this.decl    = null;
      }

    /**
     * Construct a constant value from a constant identifier
     * token and its corresponding constant declaration.
     */
    public ConstValue(Token identifier, ConstDecl decl)
      {
        super(decl.getType(), identifier.getPosition());
        this.literal = decl.getLiteral();
        this.decl    = decl;
      }

    /**
     * Returns an integer value for the declaration literal.  For an integer
     * literal, this method simply returns its integer value.  For a char
     * literal, this method returns the underlying integer value for the
     * character.  For a boolean literal, this method returns 0 for false
     * and 1 for true.  For any other literal, the method returns 0.
     */
    public int getIntValue()
      {
        if (literal.getSymbol() == Symbol.intLiteral)
          {
            try
              {
                return Integer.parseInt(literal.getText());
              }
            catch (NumberFormatException e)
              {
                // error will be reported in checkConstraints()
                return 1;
              }
          }
        else if (literal.getSymbol() == Symbol.trueRW)
            return 1;
        else if (literal.getSymbol() == Symbol.charLiteral)
          {
            char ch = literal.getText().charAt(1);
            return (int) ch;
          }
        else
            return 0;
      }

    @Override
    public void checkConstraints()
      {
        try
          {
            // Check that an integer literal can be converted to an integer.
            // Check is not required for literal in constant declaration.
            if (literal.getSymbol() == Symbol.intLiteral && decl == null)
              {
                try
                  {
                    Integer.parseInt(literal.getText());
                  }
                catch (NumberFormatException e)
                  {
                    var errorMsg = "The number \"" + literal.getText()
                                 + "\" cannot be converted to an integer in CPRL.";
                    throw error(literal.getPosition(), errorMsg);
                  }
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
        var exprType = getType();

        if (exprType == Type.Integer)
            emit("LDCINT " + getIntValue());
        else if (exprType == Type.Boolean)
            emit("LDCB " + getIntValue());
        else if (exprType == Type.Char)
            emit("LDCCH " + literal.getText());
        else if (exprType instanceof StringType)
            emit("LDCSTR " + literal.getText());
        else
          {
            var errorMsg = "Invalid type for constant value.";
            throw new CodeGenException(literal.getPosition(), errorMsg);
          }
      }
  }
