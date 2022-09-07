package edu.citadel.compiler;


/**
 * Class for fatal exceptions encountered during compilation.
 * A fatal exception is one for which compilation of the source
 * file should be abandoned.
 */
public class FatalException extends RuntimeException
  {
    private static final long serialVersionUID = -8956164495781049096L;

    /**
     * Construct a fatal exception with the specified error message.
     *
     * @param errorMsg  a brief message about the nature of the error.
     */
    public FatalException(String errorMsg)
      {
        super("*** Fatal exception: " + errorMsg);
      }
  }
