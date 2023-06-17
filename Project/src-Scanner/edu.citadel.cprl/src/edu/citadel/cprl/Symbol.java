package edu.citadel.cprl;

/**
 * This class encapsulates the symbols (also known as token types)
 * of the programming language CPRL.
 */
public enum Symbol
  {
    // reserved words
    BooleanRW("Boolean"),
    CharRW("Char"),
    IntegerRW("Integer"),
    andRW("and"),
    arrayRW("array"),
    classRW("class"),
    constRW("const"),
    elseRW("else"),
    enumRW("enum"),
    exitRW("exit"),
    falseRW("false"),
    forRW("for"),
    funRW("fun"),
    ifRW("if"),
    loopRW("loop"),
    modRW("mod"),
    notRW("not"),
    ofRW("of"),
    orRW("or"),
    privateRW("private"),
    procRW("proc"),
    protectedRW("protected"),
    publicRW("public"),
    readRW("read"),
    readlnRW("readln"),
    recordRW("record"),
    returnRW("return"),
    stringRW("string"),
    thenRW("then"),
    trueRW("true"),
    typeRW("type"),
    varRW("var"),
    whenRW("when"),
    whileRW("while"),
    writeRW("write"),
    writelnRW("writeln"),

    // arithmetic operator symbols
    plus("+"),
    minus("-"),
    times("*"),
    divide("/"),

    // relational operator symbols
    equals("="),
    notEqual("!="),
    lessThan("<"),
    lessOrEqual("<="),
    greaterThan(">"),
    greaterOrEqual(">="),

    // assignment, punctuation, and grouping symbols
    assign(":="),
    leftParen("("),
    rightParen(")"),
    leftBracket("["),
    rightBracket("]"),
    leftBrace("{"),
    rightBrace("}"),
    comma(","),
    colon(":"),
    semicolon(";"),
    dot("."),

    // literal and identifier symbols
    intLiteral("Integer Literal"),
    charLiteral("Char Literal"),
    stringLiteral("String Literal"),
    identifier("Identifier"),

    // special scanning symbols
    EOF("End-of-File"),
    unknown("Unknown");

    // instance fields
    private final String label;

    /**
     * Construct a new symbol with its label.
     */
    private Symbol(String label)
      {
        this.label = label;
      }

    /**
     * Returns true if this symbol is a reserved word.
     */
    public boolean isReservedWord()
      {
        return this.compareTo(BooleanRW) >=0 && this.compareTo(writelnRW) <= 0;
      }

    /**
     * Returns true if this symbol can start an initial declaration.
     */
    public boolean isInitialDeclStarter()
      {
        return this == constRW || this == varRW || this == typeRW;
      }

    /**
     * Returns true if this symbol can start a subprogram declaration.
     */
    public boolean isSubprogramDeclStarter()
      {
        return this == procRW || this == funRW;
      }

    /**
     * Returns true if this symbol can start a statement.
     */
    public boolean isStmtStarter()
      {
        return this == identifier || this == leftBrace || this == ifRW
            || this == loopRW     || this == whileRW   || this == exitRW
            || this == readRW     || this == writeRW   || this == writelnRW
            || this == returnRW;
      }

    /**
     * Returns true if this symbol is a literal.
     */
    public boolean isLiteral()
      {
        return this == intLiteral || this == charLiteral || this == stringLiteral
            || this == trueRW     || this == falseRW;
      }

    /**
     * Returns true if this symbol can start an expression.
     */
    public boolean isExprStarter()
      {
        return isLiteral()  || this == identifier || this == leftParen
            || this == plus || this == minus      || this == notRW;
      }

    /**
     * Returns true if this symbol can start a parameter declaration.
     */
    public boolean isParameterDeclStarter()
      {
        return this == identifier || this == varRW;
      }

    /**
     * Returns true if this symbol can start a variable selector.
     */
    public boolean isSelectorStarter()
      {
        return this == leftBracket || this == dot;
      }

    /**
     * Returns true if this symbol is a logical operator.
     */
    public boolean isLogicalOperator()
      {
        return this == andRW || this == orRW;
      }

    /**
     * Returns true if this symbol is a relational operator.
     */
    public boolean isRelationalOperator()
      {
        return this == equals      || this == notEqual
            || this == lessThan    || this == lessOrEqual
            || this == greaterThan || this == greaterOrEqual;
      }

    /**
     * Returns true if this symbol is a binary adding operator.
     */
    public boolean isAddingOperator()
      {
        return this == plus || this == minus;
      }

    /**
     * Returns true if this symbol is a unary sign operator.
     */
    public boolean isSignOperator()
      {
        return this == plus || this == minus;
      }

    /**
     * Returns true if this symbol is a multiplying operator.
     */
    public boolean isMultiplyingOperator()
      {
        return this == times || this == divide || this == modRW;
      }

    @Override
    public String toString()
      {
        return label;
      }
  }
