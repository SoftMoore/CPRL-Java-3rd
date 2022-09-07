package edu.citadel.cprl;


/**
 * Bounded circular buffer for tokens.
 */
public class TokenBuffer
  {
    private int capacity;
    private Token[] buffer;
    private int tokenIndex = 0;   // circular index


    /**
     * Construct buffer with the specified capacity.
     */
    public TokenBuffer(int capacity)
      {
        this.capacity = capacity;
        buffer = new Token[capacity];
      }


    /**
     * Return the token at index i.  Does not remove the token.
     */
    public Token get(int i)
      {
        return buffer[(tokenIndex + i) % capacity];
      }


    /**
     * Add a token to the buffer.  Overwrites if the buffer is full.
     */
    public void add(Token token)
      {
        buffer[tokenIndex] = token;
        tokenIndex = (tokenIndex + 1) % capacity;
      }
  }
