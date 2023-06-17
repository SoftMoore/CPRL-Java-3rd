package edu.citadel.cprl.ast;

import edu.citadel.cprl.Token;
import edu.citadel.cvm.Constants;

import java.util.Collections;
import java.util.List;

/**
 * Base class for CPRL procedures and functions.
 */
public abstract class SubprogramDecl extends Declaration
  {
    private List<ParameterDecl> formalParams = Collections.emptyList();
    private List<InitialDecl>   initialDecls = Collections.emptyList();
    private List<Statement>     statements   = Collections.emptyList();

    private int varLength = 0;   // # bytes of all declared variables
    private String L1;           // label of address of first statement

    /**
     * Construct a subprogram declaration with the specified subprogram identifier.
     */
    public SubprogramDecl(Token subprogramId)
      {
        super(subprogramId);
        L1 = "_" + subprogramId;
      }

    /**
     * Returns the list of initial declarations for this subprogram.
     */
    public List<InitialDecl> getInitialDecls()
      {
        return initialDecls;
      }

    /**
     * Set the list of initial declarations for this subprogram.
     */
    public void setInitialDecls(List<InitialDecl> initialDecls)
      {
        this.initialDecls = initialDecls;
      }

    /**
     * Returns the list of formal parameter declarations for this subprogram.
     */
    public List<ParameterDecl> getFormalParams()
      {
        return formalParams;
      }

    /**
     * Set the list of formal parameter declarations for this subprogram.
     */
    public void setFormalParams(List<ParameterDecl> formalParams)
      {
        this.formalParams = formalParams;
      }

    /**
     * Returns the list of statements for this subprogram.
     */
    public List<Statement> getStatements()
      {
        return statements;
      }

    /**
     * Set the list of statements for this subprogram.
     */
    public void setStatements(List<Statement> statements)
      {
        this.statements = statements;
      }

    /**
     * Returns the number of bytes required for all variables in the initial declarations.
     */
    protected int getVarLength()
      {
        return varLength;
      }

    /**
     * Returns the label associated with the first statement of the subprogram.
     */
    public String getSubprogramLabel()
      {
        return L1;
      }

    /**
     * Returns the number of bytes for all parameters.
     */
    public int getParamLength()
      {
        int paramLength = 0;

        for (ParameterDecl decl : formalParams)
            paramLength += decl.getSize();

        return paramLength;
      }

    @Override
    public void checkConstraints()
      {
        for (ParameterDecl paramDecl : formalParams)
            paramDecl.checkConstraints();

        for (InitialDecl decl : initialDecls)
            decl.checkConstraints();

        for (Statement statement : statements)
            statement.checkConstraints();
      }

    /**
    * Set the relative address (offset) for each variable and
    * parameter, and compute the length of all variables.
    */
    protected void setRelativeAddresses()
      {
        // initial relative address for a subprogram
        int currentAddr = Constants.BYTES_PER_CONTEXT;

        for (InitialDecl decl : initialDecls)
          {
            if (decl instanceof VarDecl varDecl)
              {
                // set relative address for single variable declarations
                for (SingleVarDecl svDecl : varDecl.getSingleVarDecls())
                  {
                    svDecl.setRelAddr(currentAddr);
                    currentAddr = currentAddr + svDecl.getSize();
                  }
              }
          }

        // compute length of all variables by subtracting initial relative address
        varLength = currentAddr - Constants.BYTES_PER_CONTEXT;

        // set relative address for parameters
        if (formalParams.size() > 0)
          {
            // initial relative address for a subprogram parameter
            currentAddr = 0;

            // we need to process the parameter declarations in reverse order
            var iter = formalParams.listIterator(formalParams.size());
            while (iter.hasPrevious())
              {
                var decl = iter.previous();
                currentAddr = currentAddr - decl.getSize();
                decl.setRelAddr(currentAddr);
              }
          }
      }
  }
