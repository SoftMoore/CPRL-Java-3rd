package edu.citadel.cprl.ast;

import edu.citadel.cprl.Token;

/**
 * An empty subprogram declaration passes constraint checks and emits no code.
 * It is returned from parsing subprogram declarations as an alternative to
 * returning null when parsing errors are encountered. <br>
 * (implements the singleton pattern)
 */
public class EmptySubprogramDecl extends SubprogramDecl
  {
    private static EmptySubprogramDecl instance = new EmptySubprogramDecl();

    private EmptySubprogramDecl()
      {
        super(new Token());
      }

    /**
     * Returns the single instance of this class.
     */
    public static EmptySubprogramDecl getInstance()
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
