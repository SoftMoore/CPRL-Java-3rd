package edu.citadel.cprl.ast;

import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a field expression.  The value of a field
 * expression is simply the value of the offset of the field within the record.
 */
public class FieldExpr extends Expression
  {
    private Token fieldId;

    // Note: value for fieldDecl is assigned in Variable.checkConstraints()
    private FieldDecl fieldDecl;   // nonstructural reference

    /**
     * Construct a field expression with its field name.
     */
    public FieldExpr(Token fieldId)
      {
        super(Type.Integer, fieldId.getPosition());
        this.fieldId = fieldId;
      }

    /**
     * Returns the field identifier token for this field expression.
     */
    public Token getFieldId()
      {
        return fieldId;
      }

    /**
     * Returns the field declaration for this field expression.
     */
    public FieldDecl getFieldDecl()
      {
        return fieldDecl;
      }

    /**
     * Set the field declaration for this field expression.
     */
    public void setFieldDecl(FieldDecl fieldDecl)
      {
        this.fieldDecl = fieldDecl;
      }

    @Override
    public void checkConstraints()
      {
        // nothing to do for now
      }

    @Override
    public void emit()
      {
        assert(fieldDecl.getOffset() >= 0) : "Invalid value for field offset.";
// ...
      }
  }
