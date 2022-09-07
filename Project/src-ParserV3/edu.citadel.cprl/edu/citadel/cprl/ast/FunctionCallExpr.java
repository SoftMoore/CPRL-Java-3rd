package edu.citadel.cprl.ast;


import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;

import edu.citadel.cprl.ArrayType;
import edu.citadel.cprl.StringType;
import edu.citadel.cprl.Token;

import java.util.List;


/**
 * The abstract syntax tree node for a function call expression.
 */
public class FunctionCallExpr extends Expression
  {
    private Token funId;
    private List<Expression> actualParams;

    // declaration of the function being called
    private FunctionDecl funDecl;   // nonstructural reference


    /**
     * Construct a function call expression with the function name (an identifier
     * token) and the list of actual parameters being passed as part of the call.
     */
    public FunctionCallExpr(Token funId, List<Expression> actualParams)
      {
        super(funId.getPosition());

        this.funId = funId;
        this.actualParams = actualParams;
      }


    @Override
    public void checkConstraints()
      {
        try
          {
            // get the declaration for this function call from the identifier table
            Declaration decl = getIdTable().get(funId.getText());

            if (decl == null)
              {
                String errorMsg = "Function \"" + funId + "\" has not been declared.";
                throw error(funId.getPosition(), errorMsg);
              }
            else if (!(decl instanceof FunctionDecl))
              {
                String errorMsg = "Identifier \"" + funId + "\" was not declared as a function.";
                throw error(funId.getPosition(), errorMsg);
              }
            else
                funDecl = (FunctionDecl) decl;

            // at this point funDecl should not be null
            setType(funDecl.getType());

            List<ParameterDecl> formalParams = funDecl.getFormalParams();

            // check that numbers of parameters match
            if (actualParams.size() != formalParams.size())
                throw error(funId.getPosition(), "Incorrect number of actual parameters.");

            // call checkConstraints for each actual parameter
            for (Expression expr : actualParams)
                expr.checkConstraints();

            for (int i = 0;  i < actualParams.size();  ++i)
              {
                Expression    expr  = actualParams.get(i);
                ParameterDecl param = formalParams.get(i);

                // check that parameter types match
                if (!matchTypes(param.getType(), expr))
                    throw error(expr.getPosition(), "Parameter type mismatch.");

                // check that string parameters are not literals
                if (expr.getType() instanceof StringType && expr instanceof ConstValue)
                  {
                    String errorMsg = "String literals can't be passed as parameters.";
                    throw error(expr.getPosition(), errorMsg);
                  }

                // arrays are passed as var parameters (checked in FunctionDecl)
                if (param.isVarParam() && param.getType() instanceof ArrayType)
                  {
                    // replace variable expression by a variable
                    expr = new Variable((VariableExpr) expr);
                    actualParams.set(i, expr);
                  }
              }            
          }
        catch (ConstraintException e)
          {
            getErrorHandler().reportError(e);
          }
      }


    @Override
    public void emit() throws CodeGenException
      {
        // allocate space on the stack for the return value
        emit("ALLOC " + funDecl.getType().getSize());

        // emit code for actual parameters
        for (Expression expr : actualParams)
            expr.emit();

        emit("CALL " + funDecl.getSubprogramLabel());
      }
  }
