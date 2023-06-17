package edu.citadel.cprl;

import edu.citadel.cprl.ast.FieldDecl;

import java.util.List;
import java.util.HashMap;

/**
 * This class encapsulates the language concept of a record type
 * in the programming language CPRL.
 */
public class RecordType extends Type
  {
    // Use a hash map for efficient lookup of field names.
    private HashMap<String, FieldDecl> fieldNameMap = new HashMap<>();

    /**
     * Construct a record type with the specified type name, list of
     * field declarations, and size.
     */
    public RecordType(String typeName, List<FieldDecl> fieldDecls)
      {
        super(typeName, 0);
// ... In call to superclass constructor, 0 is not correct as the size for the record type.
// ... What is the size for the record type?  Hint: Read the book.
        for (FieldDecl fieldDecl : fieldDecls)
            fieldNameMap.put(fieldDecl.getIdToken().getText(), fieldDecl);
      }

    /**
     * Returns the field declaration associated with the identifier string.
     * Returns null if the identifier string is not found.
     */
    public FieldDecl get(String idStr)
      {
        return fieldNameMap.get(idStr);
      }
  }
