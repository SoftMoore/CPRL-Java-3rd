package edu.citadel.compiler;

/**
 * This class encapsulates the properties of a language token.  A token
 * consists of a symbol (a.k.a., the token type), a position, and a string
 * that contains the text of the token.
 */
public abstract class AbstractToken<Symbol extends Enum<Symbol>>
  {
    private Symbol   symbol;
    private Position position;
    private String   text;

    /**
     * Constructs a new token with the given symbol, position, and text.
     */
    public AbstractToken(Symbol symbol, Position position, String text)
      {
        this.symbol   = symbol;
        this.position = position;
        this.text     = text == null || text.length() == 0 ? symbol.toString() : text;
      }

    /**
     * Returns the token's symbol.
     */
    public Symbol getSymbol()
      {
        return symbol;
      }

    /**
     * Returns the token's position within the source file.
     */
    public Position getPosition()
      {
        return position;
      }

    /**
     * Returns the string representation for the token.
     */
    public String getText()
      {
        return text;
      }

    /**
     * Set the string representation for the token.
     */
    public void setText(String text)
      {
        this.text = text;
      }

    @Override
    public String toString()
      {
        return getText();
      }
  }
