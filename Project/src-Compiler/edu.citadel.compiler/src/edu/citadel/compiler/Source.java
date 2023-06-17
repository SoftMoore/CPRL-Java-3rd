package edu.citadel.compiler;

import java.io.IOException;
import java.io.Reader;

/**
 * This class encapsulates the source file reader.  It maintains
 * the position of each character in the source file.
 */
public final class Source
  {
    // The reader used to read characters from the source file.
    private Reader sourceReader;

    // An integer representing the current character in the source file.
    // This field has the value EOF (-1) when the end of file has been reached.
    private int currentChar = 0;

    // The source line number of the current character.
    private int lineNumber = 1;

    // The offset of the current character within its line.
    private int charNumber = 0;

    /**
     * A constant representing end of file.
     */
    public static final int EOF = -1;

    /**
     * Initialize source with a reader and advance to the first character.
     */
    public Source(Reader sourceReader) throws IOException
      {
        this.sourceReader = sourceReader;
        advance();   // advance to the first character
      }

    /**
     * Returns an integer representing the current character in the source
     * file.  Returns EOF (-1) if the end of file has been reached.
     */
    public int getChar()
      {
        return currentChar;
      }

    /**
     * Returns the position (line number, char number) of the
     * current character in the source file.
     */
    public Position getCharPosition()
      {
        return new Position(lineNumber, charNumber);
      }

    /**
     * Advance to the next character in the source file.
     */
    public void advance() throws IOException
      {
        if (currentChar == '\n')
          {
            ++lineNumber;
            charNumber = 1;
          }
        else
            ++charNumber;

        currentChar = sourceReader.read();
      }
  }
