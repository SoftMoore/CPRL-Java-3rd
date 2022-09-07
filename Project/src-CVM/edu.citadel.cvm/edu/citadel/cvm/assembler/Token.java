package edu.citadel.cvm.assembler;


import edu.citadel.compiler.AbstractToken;
import edu.citadel.compiler.Position;


/**
 * Instantiates the generic class AbstractToken for CVM assembly language symbols.
 */
public final class Token extends AbstractToken<Symbol>
  {
    /**
     * Constructs a new token with the given symbol, position, and text.
     */
    public Token(Symbol symbol, Position position, String text)
      {
        super(symbol, position, text);
      }

    
    /**
     * Constructs a new Token with the specified symbol.  Position
     * and text are initialized to null  This constructor is
     * useful when replacing instructions during optimization.
     */
    public Token(Symbol symbol)
      {
        super(symbol, null, null);
      }


    /**
     * Constructs a new Token with the specified symbol and text.
     * Position is initialized to null.  This constructor is
     * useful when replacing instructions during optimization.
     */
    public Token(Symbol symbol, String text)
      {
        super(symbol, null, text);
      }
  }
