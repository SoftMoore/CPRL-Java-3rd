package edu.citadel.compiler;

/**
 * This class encapsulates the concept of a position in a source file, where
 * the position is characterized by an ordered pair of integers: a line number
 * relative to the source file and a character number relative to that line.
 * Note: Position objects are immutable.
 */
public final class Position
  {
    /** The current line number of the position. */
    private int lineNumber;

    /** The current character number of the position. */
    private int charNumber;

    /**
     * Construct a position with the default values of 0
     * for both line number and character number.
     */
    public Position()
      {
        this(0, 0);
      }

    /**
     * Construct a position with the given line number and character number.
     */
    public Position(int lineNumber, int charNumber)
      {
        this.lineNumber = lineNumber;
        this.charNumber = charNumber;
      }

    /**
     * Returns the line number of this position.
     */
    public int getLineNumber()
      {
        return lineNumber;
      }

    /**
     * Returns the character number of this position.
     */
    public int getCharNumber()
      {
        return charNumber;
      }

    @Override
    public String toString()
      {
        return "line " + lineNumber + ", character " + charNumber;
      }
  }
