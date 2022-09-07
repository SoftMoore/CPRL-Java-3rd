package edu.citadel.compiler;


/**
 * Class for exceptions encountered during code generation.
 */
public class CodeGenException extends CompilerException
  {
    private static final long serialVersionUID = 5227233416865236056L;


    /**
     * Construct a code generation exception with the specified
     * position and error message.
     */
    public CodeGenException(Position position, String errorMsg)
      {
        super("Code Generation", position, errorMsg);
      }
  }
