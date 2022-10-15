package edu.citadel.cprl;

import edu.citadel.compiler.AbstractToken;
import edu.citadel.compiler.Position;

/**
 * This class instantiates the generic class AbstractToken using CPRL symbols.
 */
public class Token extends AbstractToken<Symbol>
  {
    /**
     * Construct a new token with the given symbol, position, and text.
     */
    public Token(Symbol symbol, Position position, String text)
      {
        super(symbol, position, text);
      }

    /**
     * Construct a new token with symbol = Symbol.unknown.
     * Position and text are initialized to default values.
     */
    public Token()
      {
        this(Symbol.unknown, new Position(), "");
      }
  }
