package edu.citadel.compiler;

/**
 * Superclass for all compiler exceptions.
 */
public abstract class CompilerException extends Exception
  {
    private static final long serialVersionUID = -6999301636930707946L;

    /**
     * Construct a compiler exception with information about the compilation phase,
     * position, and error message.
     *
     * @param errorType the name of compilation phase in which the error was detected.
     * @param position  the position in the source file where the error was detected.
     * @param errorMsg  a brief message about the nature of the error.
     */
    public CompilerException(String errorType, Position position, String errorMsg)
      {
        super("*** " + errorType + " error detected near " + position
            + ":\n    " + errorMsg);
      }

    /**
     * Construct a compiler exception with information about the compilation phase
     * and error message, but not its position.
     *
     * @param errorType the name of compilation phase in which the error was detected.
     * @param errorMsg  a brief message about the nature of the error.
     */
    public CompilerException(String errorType, String errorMsg)
      {
        super("*** " + errorType + " error detected:\n    " + errorMsg);
      }

    /**
     * Construct a compiler exception with the specified error message.
     *
     * @param errorMsg a brief message about the nature of the error.
     */
    public CompilerException(String errorMsg)
      {
        super(errorMsg);
      }
  }
