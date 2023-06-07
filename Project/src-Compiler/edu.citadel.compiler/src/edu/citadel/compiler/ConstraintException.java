package edu.citadel.compiler;

/**
 * Class for exceptions encountered during constraint analysis.
 */
public class ConstraintException extends CompilerException
  {
    private static final long serialVersionUID = 5793802201497958837L;

    /**
     * Construct a constraint exception with the specified position
     * and error message.
     *
     * @param position the position in the source file where the error was detected.
     * @param errorMsg a brief message about the nature of the error.
     */
    public ConstraintException(Position position, String errorMsg)
      {
        super("Constraint", position, errorMsg);
      }

    /**
     * Construct a constraint exception with the specified error message.
     *
     * @param errorMsg a brief message about the nature of the error.
     */
    public ConstraintException(String errorMsg)
      {
        super("Constraint", errorMsg);
      }
  }
