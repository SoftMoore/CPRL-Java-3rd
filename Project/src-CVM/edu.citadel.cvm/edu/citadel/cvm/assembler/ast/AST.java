package edu.citadel.cvm.assembler.ast;


import edu.citadel.compiler.Position;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.util.ByteUtil;

import java.io.IOException;
import java.io.OutputStream;


/**
 * Base class for all abstract syntax trees
 */
public abstract class AST
  {
    private static OutputStream out = null;
    private static ErrorHandler errorHandler;


    /**
     * Default constructor.
     */
    public AST()
      {
        super();
      }


    /**
     * Set the output stream to be used for code generation.
     */
    public static void setOutputStream(OutputStream out)
      {
        AST.out = out;
      }


    /**
     * Set the error handler to be used for code generation.
     */
    public static void setErrorHandler(ErrorHandler errorHandler)
      {
        AST.errorHandler = errorHandler;
      }


    /**
     * Get the error handler used during code generation.
     */
    public static ErrorHandler getErrorHandler()
      {
        return AST.errorHandler;
      }


    /**
     * Creates/returns a new constraint exception with the specified position and message.
     */
    protected ConstraintException error(Position errorPos, String errorMsg)
      {
        return new ConstraintException(errorPos, errorMsg);
      }


    /**
     * emit the opCode for the instruction
     */
   protected void emit(byte opCode) throws IOException
     {
       out.write(opCode);
     }


    /**
     * emit an integer argument for the instruction
     */
   protected void emit(int arg) throws IOException
     {
       out.write(ByteUtil.intToBytes(arg));
     }


    /**
     * emit a character argument for the instruction
     */
   protected void emit(char arg) throws IOException
     {
       out.write(ByteUtil.charToBytes(arg));
     }


   /**
    * check semantic/contextual constraints
    */
   public abstract void checkConstraints();


   /**
    * emit the object code for the AST
    */
   public abstract void emit() throws IOException;
  }
