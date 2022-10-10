package edu.citadel.cvm.assembler;


import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.FatalException;
import edu.citadel.compiler.Source;

import edu.citadel.cvm.assembler.ast.AST;
import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.Program;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Assembler for the CPRL Virtual Machine.
 */
public class Assembler
  {
    private static final boolean DEBUG = false;

    private static final String  SUFFIX  = ".asm";
    private static final int     FAILURE = -1;

    private static boolean optimize = true;

    private File sourceFile;


    /**
     * Translates the assembly source files named in args to CVM machine
     * code.  Object files have the same name but with a ".obj" suffix.
     */
    public static void main(String[] args) throws Exception
      {
        // check arguments
        if (args.length == 0)
            printUsageAndExit();

        int startIndex = 0;

        if (args[0].startsWith("-opt:"))
          {
            processOption(args[0]);
            startIndex = 1;
          }

        for (int i = startIndex; i < args.length; ++i)
          {
            try
              {
                String fileName = args[i];
                File sourceFile = new File(fileName);

                if (!sourceFile.isFile())
                  {
                    // see if we can find the file by appending the suffix
                    int index = fileName.lastIndexOf('.');

                    if (index < 0 || !fileName.substring(index).equals(SUFFIX))
                      {
                        fileName += SUFFIX;
                        sourceFile = new File(fileName);

                        if (!sourceFile.isFile())
                            throw new FatalException("\"*** File " + fileName
                                                   + " not found ***\"");
                      }
                    else
                      {
                        // don't try to append the suffix
                        throw new FatalException("\"*** File " + fileName
                                               + " not found ***\"");
                      }
                  }

                Assembler assembler = new Assembler(sourceFile);
                assembler.assemble();
              }
            catch (FatalException e)
              {
                // report error and continue compiling
                ErrorHandler errorHandler = new ErrorHandler();
                errorHandler.reportFatalError(e);
              }

            System.out.println();
          }
      }


    /**
     * Construct an assembler with the specified source file.
     */
    public Assembler(File sourceFile)
      {
        this.sourceFile = sourceFile;
        Instruction.initMaps();
      }


    /**
     * Assembles the source file.  If there are no errors in the source file,
     * the object code is placed in a file with the same base file name as
     * the source file but with a ".obj" suffix.
     *
     * @throws IOException if there are problems reading the source file
     *                     or writing to the target file.
     */
    public void assemble() throws IOException
      {
        ErrorHandler errorHandler = new ErrorHandler();
        Reader  reader  = new FileReader(sourceFile, StandardCharsets.UTF_8);
        Source  source  = new Source(reader);
        Scanner scanner = new Scanner(source, errorHandler);
        Parser  parser  = new Parser(scanner, errorHandler);
        AST.setErrorHandler(errorHandler);

        printProgressMessage("Starting assembly for " + sourceFile.getName());

        // parse source file
        Program prog = parser.parseProgram();

        if (DEBUG)
          {
            System.out.println("...program after parsing");
            printInstructions(prog.getInstructions());
          }

        // optimize
        if (!errorHandler.errorsExist() && optimize)
          {
            printProgressMessage("...performing optimizations");
            prog.optimize();
          }

        if (DEBUG)
          {
            System.out.println("...program after performing optimizations");
            printInstructions(prog.getInstructions());
          }

        // set addresses
        if (!errorHandler.errorsExist())
          {
            printProgressMessage("...setting memory addresses");
            prog.setAddresses();
          }

        // check constraints
        if (!errorHandler.errorsExist())
          {
            printProgressMessage("...checking constraints");
            prog.checkConstraints();
          }

        if (DEBUG)
          {
            System.out.println("...program after checking constraints");
            printInstructions(prog.getInstructions());
          }

        // generate code
        if (!errorHandler.errorsExist())
          {
            printProgressMessage("...generating code");
            AST.setOutputStream(getTargetOutputStream(sourceFile));

            // no error recovery from errors detected during code generation
            prog.emit();
          }

        if (errorHandler.errorsExist())
            errorHandler.printMessage("*** Errors detected in " + sourceFile.getName()
                                    + " -- assembly terminated. ***");
        else
            printProgressMessage("Assembly complete.");
      }


    /**
     * This method is useful for debugging.
     *
     * @param instructions the list of instructions to print
     */
    private static void printInstructions(List<Instruction> instructions)
      {
        if (instructions == null)
            System.out.println("<no instructions>");
        else
          {
            System.out.println("There are " + instructions.size() + " instructions");
            for (Instruction instruction : instructions)
                System.out.println(instruction);
            System.out.println();
          }
      }

    private static void printProgressMessage(String message)
      {
         System.out.println(message);
      }


    private static void printUsageAndExit()
      {
        System.out.println("Usage: Assembler expecting [<option>] and one or more source files");
        System.out.println("where the option is omitted or is one of the following:");
        System.out.println("-opt:off   Turns off all assembler optimizations");
        System.out.println("-opt:on    Turns on all assembler optimizations (default)");
        System.out.println();
        System.exit(0);
      }


    private static void processOption(String option)
      {
        if (option.equals("-opt:off"))
            optimize = false;
        else if (option.equals("-opt:on"))
            optimize = true;
        else
            printUsageAndExit();
      }


    private OutputStream getTargetOutputStream(File sourceFile)
      {
        // get source file name minus the suffix
        String baseName = sourceFile.getName();
        int suffixIndex = baseName.lastIndexOf(SUFFIX);
        if (suffixIndex > 0)
            baseName = sourceFile.getName().substring(0, suffixIndex);

        String targetFileName = baseName + ".obj";

        File targetFile = null;
        OutputStream targetStream = null;

        try
          {
            targetFile = new File(sourceFile.getParent(), targetFileName);
            targetStream = new FileOutputStream(targetFile);
          }
        catch (IOException e)
          {
            e.printStackTrace();
            System.exit(FAILURE);
          }

        return targetStream;
      }
  }
