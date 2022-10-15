package edu.citadel.cprl.ast;

import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * Base class for all initial declarations.
 */
public abstract class InitialDecl extends Declaration
  {
    /**
     * Construct an initial declaration with its identifier and type.
     */
    public InitialDecl(Token identifier, Type declType)
      {
        super(identifier, declType);
      }
  }
