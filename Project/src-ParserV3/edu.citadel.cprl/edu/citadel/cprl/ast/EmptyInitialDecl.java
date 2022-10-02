package edu.citadel.cprl.ast;

import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * An empty initial declaration passes constraint checks and emits no code.
 * It is returned from parsing initial declarations as an alternative to
 * returning null when parsing errors are encountered. <br>
 * (implements the singleton pattern)
 */
public class EmptyInitialDecl extends InitialDecl
  {
    private static EmptyInitialDecl instance = new EmptyInitialDecl();

    private EmptyInitialDecl()
      {
        super(new Token(), Type.UNKNOWN);
      }

    /**
     * Returns the single instance of this class. 
     */
    public static EmptyInitialDecl getInstance()
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
