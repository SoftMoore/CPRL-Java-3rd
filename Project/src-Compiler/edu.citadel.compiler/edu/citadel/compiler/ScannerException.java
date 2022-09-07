package edu.citadel.compiler;


/**
 * Class for exceptions encountered during scanning.
 */
public class ScannerException extends CompilerException
  {
    private static final long serialVersionUID = 4897417646057044051L;


    /**
     * Construct a scanner exception with the specified position and error message.
     *
     * @param position the position in the source file where the error was detected.
     * @param errorMsg a brief message about the nature of the error.
     */
    public ScannerException(Position position, String errorMsg)
      {
        super("Lexical", position, errorMsg);
      }
  }
