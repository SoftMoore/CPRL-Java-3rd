package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.cprl.Token;

/**
 * The abstract syntax tree node for a procedure declaration.
 */
public class ProcedureDecl extends SubprogramDecl
  {
    /**
     * Construct a procedure declaration with its name (an identifier).
     */
    public ProcedureDecl(Token procId)
      {
        super(procId);
      }

    // inherited checkConstraints() is sufficient

    @Override
    public void emit() throws CodeGenException
      {
        setRelativeAddresses();
        emitLabel(getSubprogramLabel());

        // no need to emit PROC instruction if varLength == 0
        if (getVarLength() > 0)
            emit("PROC " + getVarLength());

        for (InitialDecl decl : getInitialDecls())
            decl.emit();

        for (Statement statement : getStatements())
            statement.emit();

        emit("RET " + getParamLength());
      }
  }
