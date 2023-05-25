package edu.citadel.cprl.ast;

import edu.citadel.cprl.RecordType;
import edu.citadel.cprl.Token;
import java.util.List;

/**
 * The abstract syntax tree node for a record type declaration.
 */
public class RecordTypeDecl extends InitialDecl
  {
    private List<FieldDecl> fieldDecls;

    /**
     * Construct a record type declaration with its type name (identifier)
     * and list of field declarations.
     *
     * @param typeId the token containing the identifier for the record type name
     * @param fieldDecls the list of field declarations for the record
     */
    public RecordTypeDecl(Token typeId, List<FieldDecl> fieldDecls)
      {
        super(typeId, new RecordType(typeId.getText(), fieldDecls));
        this.fieldDecls = fieldDecls;
      }

    @Override
    public void checkConstraints()
      {
// ...   Don't forget to compute fieldDecl offsets.
      }
  }
