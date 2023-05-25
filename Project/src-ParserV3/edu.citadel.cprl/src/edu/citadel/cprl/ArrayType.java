package edu.citadel.cprl;

/**
 * This class encapsulates the language concept of an array type
 * in the programming language CPRL.
 */
public class ArrayType extends Type
  {
    private Type elementType;

    /**
     * Construct an array type with the specified type name, number
     * of elements, and the type of elements contained in the array.
     */
    public ArrayType(String typeName, int numElements, Type elementType)
      {
        super(typeName, 0);
// ... In call to superclass constructor, 0 is not correct as the size for the array type.
// ... What is the size for the array type?  Hint: Read the book.
        this.elementType = elementType;
      }

    /**
     * Returns the element type for this array type.
     */
    public Type getElementType()
      {
        return elementType;
      }
  }
