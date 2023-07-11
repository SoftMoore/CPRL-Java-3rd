package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.Position;

import edu.citadel.cprl.IdTable;
import edu.citadel.cprl.Type;
import edu.citadel.cprl.StringType;

import java.io.PrintWriter;

/**
 * Base class for all abstract syntax tree classes.
 */
public abstract class AST
  {
    private static PrintWriter out = new PrintWriter(System.out);

    // current label number for control flow
    private static int currentLabelNum = -1;

    private static IdTable idTable;
    private static ErrorHandler errorHandler;

    /**
     * Initializes static members that are shared with all instructions.
     * The members must be re-initialized each time that the compiler is
     * run on a different file; e.g., via a command like cprlc *.asm1.
     */
    public static void initStatic()
      {
        currentLabelNum = -1;
      }

    /**
     * Set the print writer to be used for code generation.
     */
    public static void setPrintWriter(PrintWriter out)
      {
        AST.out = out;
      }

    /**
     * Set the identifier table to be used during code generation.
     */
    public static void setIdTable(IdTable idTable)
      {
        AST.idTable = idTable;
      }

    /**
     * Get the identifier table used during code generation.
     */
    public static IdTable getIdTable()
      {
        return AST.idTable;
      }

    /**
     * Set the error handler to be used during code generation.
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
     * Creates/returns a new constraint exception with the specified message.
     */
    protected ConstraintException error(String errorMsg)
      {
        return new ConstraintException(errorMsg);
      }

    /**
     * Check semantic/contextual constraints.
     */
    public abstract void checkConstraints();

    /**
     * Emit object code.
     *
     * @throws CodeGenException if the method is unable to generate object code.
     */
    public abstract void emit() throws CodeGenException;

    /**
     * Returns a new value for a label number.  This method should
     * be called once for each label before code generation.
     */
    protected String getNewLabel()
      {
        ++currentLabelNum;
        return "L" + currentLabelNum;
      }

    /**
     * Returns true if the expression type is assignment compatible with
     * the specified type.  This method is used to compare types for
     * assignment statements, subprogram parameters, and return values.
     */
    protected boolean matchTypes(Type type, Expression expr)
      {
        var exprType = expr.getType();
        if (type == exprType)
            return true;
        else if (type instanceof StringType t && exprType instanceof StringType e)
            return (e.getCapacity() <= t.getCapacity()) && (expr instanceof ConstValue);
        else
            return false;
      }

    /**
     * Emits the appropriate LOAD instruction based on the type.
     */
    protected void emitLoadInst(Type t)
      {
        switch (t.getSize())
          {
            case 4  -> emit("LOADW");
            case 2  -> emit("LOAD2B");
            case 1  -> emit("LOADB");
            default -> emit("LOAD " + t.getSize());
          }
      }

    /**
     * Emits the appropriate STORE instruction based on the type.
     */
    protected void emitStoreInst(Type t)
      {
        switch (t.getSize())
          {
            case 4  -> emit("STOREW");
            case 2  -> emit("STORE2B");
            case 1  -> emit("STOREB");
            default -> emit("STORE " + t.getSize());
          }
      }

    /**
     * Emit label for assembly instruction.  This instruction appends a colon
     * to the end of the label and writes out the result on a single line.
     */
    protected void emitLabel(String label)
      {
        out.println(label + ":");
      }

    /**
     * Emit string representation for an assembly instruction.
     */
    protected void emit(String instruction)
      {
        out.println("   " + instruction);
      }
  }
