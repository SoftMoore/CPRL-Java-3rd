package edu.citadel.cprl.ast;


import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.Position;

import edu.citadel.cprl.ArrayType;
import edu.citadel.cprl.RecordType;
import edu.citadel.cprl.StringType;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.ScopeLevel;
import edu.citadel.cprl.Type;

import java.util.List;


/**
 * The abstract syntax tree node for a variable, which is any named variable
 * that can appear on the left-hand side of an assignment statement.
 */
public class Variable extends Expression
  {
    private List<Expression> selectorExprs;
    private VariableDecl decl;   // nonstructural reference


    /**
     * Construct a variable with a reference to its declaration,
     * its position, and a list of selector expressions.
     */
    public Variable(VariableDecl decl, Position position, List<Expression> selectorExprs)
      {
        super(decl.getType(), position);
        this.decl = decl;
        this.selectorExprs = selectorExprs;
      }


    /**
     * Construct a variable that corresponds to a variable expression.
     */
    public Variable(VariableExpr varExpr)
      {
        this(varExpr.getDecl(), varExpr.getPosition(), varExpr.getSelectorExprs());
      }


    /**
     * Returns the declaration for this variable.
     */
    public VariableDecl getDecl()
      {
        return decl;
      }


    /**
     * Returns the list of selector expressions for the variable.  Returns
     * an empty list if the variable is not an array variable.
     */
    public List<Expression> getSelectorExprs()
      {
        return selectorExprs;
      }


    @Override
    public void checkConstraints()
      {
        try
          {
            assert decl instanceof SingleVarDecl || decl instanceof ParameterDecl :
                "Declaration is not a variable.";

            for (Expression expr : selectorExprs)
               {
                 expr.checkConstraints();

                // Each selector expression must correspond to
                // an array type, a record type, or a string type.
                if (getType() instanceof ArrayType arrayType)
                  {
                    // Applying the selector effectively changes the
                    // variable's type to the element type of the array.
                    setType(arrayType.getElementType());

                    // check that the selector expression is not a field expression
// ...

                    // check that the type of the index expression is Integer
// ...
                  }
                else if (getType() instanceof RecordType recType)
                  {
                    // check that the selector expression is a field expression
// ...

                    // Applying the selector effectively changes the
                    // variable's type to the type of the field.
                    FieldExpr fieldExpr = (FieldExpr) expr;
                    Token fieldId = fieldExpr.getFieldId();
                    FieldDecl fieldDecl = recType.get(fieldId.getText());
                    if (fieldDecl != null)
                      {
                        fieldExpr.setFieldDecl(fieldDecl);
                        setType(fieldDecl.getType());
                      }
                    else
                      {
                        String errorMsg = "\"" + fieldId.getText()
                                        + "\" is not a valid field name for " + recType + ".";
                        throw error(fieldId.getPosition(), errorMsg);
                      }
                  }
                else if (getType() instanceof StringType)
                  {
                    // A string can have both a field expression for length (always
                    // at offset 0) and an index expression for the characters.

                    if (expr instanceof FieldExpr fieldExpr)
                      {
                        // Applying length field selector effectively changes the
                        // variable's type to Integer.
                        setType(Type.Integer);

                        // check that the field identifier is "length"
                        Token fieldId = fieldExpr.getFieldId();
                        if (!fieldId.getText().equals("length"))
                          {
                            String errorMsg = "Field name must be \"length\" for strings.";
                            throw error(fieldId.getPosition(), errorMsg);
                          }
                      }
                    else
                      {
                        // Applying an index selector effectively changes the
                        // variable's type to Char.
                        setType(Type.Char);

                        if (expr.getType() != Type.Integer)
                          {
                            String errorMsg = "Index expression must have type Integer.";
                            throw error(expr.getPosition(), errorMsg);
                          }
                      }
                  }
                else
                  {
                    String errorMsg = "Selector expression not allowed; "
                                    + "not an array, record, or string.";
                    throw error(expr.getPosition(), errorMsg);
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
        if (decl instanceof ParameterDecl pDecl && pDecl.isVarParam())
          {
            // address of actual parameter is value of var parameter
            emit("LDLADDR " + decl.getRelAddr());
            emit("LOADW");
          }
        else if (decl.getScopeLevel() == ScopeLevel.GLOBAL)
            emit("LDGADDR " + decl.getRelAddr());
        else
            emit("LDLADDR " + decl.getRelAddr());

        // For an array, record, or string, at this point the base address of the array
        // record, or string is on the top of the stack.  We need to replace it by the
        // sum: base address + offset
        Type type = decl.getType();

        for (Expression expr : selectorExprs)
          {
            if (type instanceof ArrayType arrayType)
              {
                expr.emit();   // emit the index

                // multiply by size of array element type to get offset
// ...

                // Note: No code to perform bounds checking for the index to
                // ensure that the index is >= 0 and < number of elements.

                // add offset to the base address
                emit("ADD");

                type = arrayType.getElementType();
              }
            else if (type instanceof RecordType)
              {
                FieldExpr fieldExpr = (FieldExpr) expr;

                if (fieldExpr.getFieldDecl().getOffset() != 0)
                  {
                    // add offset to the base address
// ...
                  }

                type = fieldExpr.getFieldDecl().getType();
              }
            else if (type instanceof StringType)
              {
                if (expr instanceof FieldExpr)
                  {
                    // The only allowed field expression for strings is length, which
                    // is at offset 0; we don't need to emit code for the offset.
                  }
                else   // selector expression must be an index expression
                  {
                    // skip over length (type Integer)
                    emit("LDCINT " + Type.Integer.getSize());
                    emit("ADD");

                    expr.emit();   // emit offset

                    // multiply by size of type Char to get offset
                    emit("LDCINT " + Type.Char.getSize());
                    emit("MUL");

                    // add offset to the base address
                    emit("ADD");

                    // Note: No code to perform bounds checking for the index to
                    // ensure that the index is >= 0 and < string capacity.
                  }
              }
          }
      }
  }
