package edu.citadel.cprl.ast;

import edu.citadel.compiler.Position;

/**
 * An empty expression passes constraint checks and emits no code.
 * It is returned from parsing statements as an alternative to
 * returning null when parsing errors are encountered. <br>
 * (implements the singleton pattern)
 */
public class EmptyExpression extends Expression
  {
    private static EmptyExpression instance = new EmptyExpression();

    private EmptyExpression()
      {
        super(new Position());
      }

    /**
     * Returns the single instance of this class. 
     */
    public static EmptyExpression getInstance()
      {
        return instance;
      }

    @Override
    public void checkConstraints()
      {
        // nothing to check
      }

    @Override
    public void emit()
      {
        // nothing to emit
      }
  }
