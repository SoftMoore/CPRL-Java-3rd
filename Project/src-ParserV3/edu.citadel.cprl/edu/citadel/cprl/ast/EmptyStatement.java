package edu.citadel.cprl.ast;

/**
 * An empty statement passes constraint checks and emits no code.
 * It is returned from parsing statements as an alternative to
 * returning null when parsing errors are encountered. <br>
 * (implements the singleton pattern)
 */
public class EmptyStatement extends Statement
  {
    private static EmptyStatement instance = new EmptyStatement();
    
    private EmptyStatement()
      {
        super();
      }

    /**
     * Returns the single instance of this class. 
     */
    public static EmptyStatement getInstance()
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
