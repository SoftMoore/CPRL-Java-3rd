package edu.citadel.cprl;

import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.InternalCompilerException;
import edu.citadel.compiler.ParserException;
import edu.citadel.compiler.Position;
import edu.citadel.cprl.ast.*;

import java.io.IOException;
import java.util.*;

/**
 * This class uses recursive descent to perform syntax analysis of
 * the CPRL source language and to generate an abstract syntax tree.
 */
public final class Parser
  {
    private Scanner scanner;
    private IdTable idTable;
    private ErrorHandler errorHandler;
    private LoopContext  loopContext = new LoopContext();
    private SubprogramContext subprogramContext = new SubprogramContext();

    /** Symbols that can follow a statement. */
    private final Set<Symbol> stmtFollowers = EnumSet.of(
// ...
      );

    /** Symbols that can follow a subprogram declaration. */
    private final Set<Symbol> subprogDeclFollowers = EnumSet.of(
// ...
      );

    /** Symbols that can follow a factor. */
    private final Set<Symbol> factorFollowers = EnumSet.of(
        Symbol.semicolon, Symbol.loopRW,       Symbol.thenRW,      Symbol.rightParen,
        Symbol.andRW,     Symbol.orRW,         Symbol.equals,      Symbol.notEqual,
        Symbol.lessThan,  Symbol.lessOrEqual,  Symbol.greaterThan, Symbol.greaterOrEqual,
        Symbol.plus,      Symbol.minus,        Symbol.times,       Symbol.divide,
        Symbol.modRW,     Symbol.rightBracket, Symbol.comma);

    /** Symbols that can follow an initial declaration (computed property).
     *  Set is computed dynamically based on the scope level. */
    private Set<Symbol> initialDeclFollowers()
      {
        // An initial declaration can always be followed by another
        // initial declaration, regardless of the scope level.
        var followers = EnumSet.of(Symbol.constRW, Symbol.varRW, Symbol.typeRW);

        if (idTable.getScopeLevel() == ScopeLevel.GLOBAL)
            followers.addAll(EnumSet.of(Symbol.procRW, Symbol.funRW));
        else
          {
            followers.addAll(stmtFollowers);
            followers.remove(Symbol.elseRW);
          }

        return followers;
      }

    /**
     * Construct a parser with the specified scanner, identifier
     * table, and error handler.
     */
    public Parser(Scanner scanner, IdTable idTable, ErrorHandler errorHandler)
      {
        this.scanner = scanner;
        this.idTable = idTable;
        this.errorHandler = errorHandler;
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>program = initialDecls subprogramDecls .</code>
     *
     * @return the parsed program.  Returns a program with an empty list of initial
     *         declarations and an empty list of statements if parsing fails.
     *
     */
    public Program parseProgram() throws IOException
      {
        try
          {
            var initialDecls = parseInitialDecls();
            var subprogDecls = parseSubprogramDecls();

            // match(Symbol.EOF)
            // Let's generate a better error message than "Expecting "End-of-File" but ..."
            if (scanner.getSymbol() != Symbol.EOF)
                throw error("Expecting \"proc\" or \"fun\" " + "but found \""
                            + scanner.getToken() + "\" instead.");

            return new Program(initialDecls, subprogDecls);
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(EnumSet.of(Symbol.EOF));
            return new Program();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>initialDecls = { initialDecl } .</code>
     *
     * @return the list of initial declarations.
     */
    private List<InitialDecl> parseInitialDecls() throws IOException
      {
        var initialDecls = new ArrayList<InitialDecl>(10);

        while (scanner.getSymbol().isInitialDeclStarter())
            initialDecls.add(parseInitialDecl());

        return initialDecls;
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>initialDecl = constDecl | varDecl | typeDecl .</code>
     *
     * @return the parsed initial declaration.  Returns an
     *         empty initial declaration if parsing fails.
     */
    private InitialDecl parseInitialDecl() throws IOException
      {
// ...   throw an internal error if the symbol is not one of constRW, varRW, or typeRW
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>constDecl = "const" constId ":=" literal ";" .</code>
     *
     * @return the parsed constant declaration.  Returns an
     *         empty initial declaration if parsing fails.
     */
    private InitialDecl parseConstDecl() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>literal = intLiteral | charLiteral | stringLiteral | "true" | "false" .</code>
     *
     * @return the parsed literal token.  Returns a default token if parsing fails.
     */
    private Token parseLiteral() throws IOException
      {
        try
          {
            if (scanner.getSymbol().isLiteral())
              {
                Token literal = scanner.getToken();
                matchCurrentSymbol();
                return literal;
              }
            else
                throw error("Invalid literal expression.");
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(factorFollowers);
            return new Token();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>varDecl = "var" identifiers ":" typeName [ ":=" constValue] ";" .</code>
     *
     * @return the parsed variable declaration.  Returns an
     *         empty initial declaration if parsing fails.
     */
    private InitialDecl parseVarDecl() throws IOException
      {
        try
          {
            match(Symbol.varRW);
            var identifiers = parseIdentifiers();
            match(Symbol.colon);
            var varType = parseTypeName();

            ConstValue initialValue = null;
            if (scanner.getSymbol() == Symbol.assign)
              {
                matchCurrentSymbol();
                var constValue = parseConstValue();
                if (constValue instanceof ConstValue)
                    initialValue = (ConstValue) constValue;
              }

            match(Symbol.semicolon);

            var varDecl = new VarDecl(identifiers, varType, initialValue,
                                      idTable.getScopeLevel());

            for (SingleVarDecl decl : varDecl.getSingleVarDecls())
                idTable.add(decl);

            return varDecl;
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(initialDeclFollowers());
            return EmptyInitialDecl.getInstance();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>identifiers = identifier { "," identifier } .</code>
     *
     * @return the list of identifier tokens.  Returns an empty list if parsing fails.
     */
    private List<Token> parseIdentifiers() throws IOException
      {
        try
          {
            var identifiers = new ArrayList<Token>(10);
            var idToken = scanner.getToken();
            match(Symbol.identifier);
            identifiers.add(idToken);

            while (scanner.getSymbol() == Symbol.comma)
              {
                matchCurrentSymbol();
                idToken = scanner.getToken();
                match(Symbol.identifier);
                identifiers.add(idToken);
              }

            return identifiers;
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(EnumSet.of(Symbol.colon));
            return Collections.emptyList();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>typeDecl = arrayTypeDecl | recordTypeDecl | stringTypeDecl .</code>
     *
     * @return the parsed type declaration.  Returns an
     *         empty initial declaration parsing fails.
     */
    private InitialDecl parseTypeDecl() throws IOException
      {
        assert scanner.getSymbol() == Symbol.typeRW;

        try
          {
            var symbol = scanner.lookahead(4).getSymbol();
            if (symbol == Symbol.arrayRW)
                return parseArrayTypeDecl();
            else if (symbol == Symbol.recordRW)
                return parseRecordTypeDecl();
            else if (symbol == Symbol.stringRW)
                return parseStringTypeDecl();
            else
              {
                Position errorPos = scanner.lookahead(4).getPosition();
                throw error(errorPos, "Invalid type declaration.");
              }
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            matchCurrentSymbol();   // force scanner past "type"
            recover(initialDeclFollowers());
            return EmptyInitialDecl.getInstance();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>arrayTypeDecl = "type" typeId "=" "array" "[" intConstValue "]"
     *                       "of" typeName ";" .</code>
     *
     * @return the parsed array type declaration.  Returns an
     *         empty initial declaration if parsing fails.
     */
    private InitialDecl parseArrayTypeDecl() throws IOException
      {
        try
          {
// ...
            var numElements = parseConstValue();
            if (numElements instanceof EmptyExpression)
              {
                // create default value for numElements to prevent "undeclared" errors
                var token = new Token(Symbol.intLiteral, scanner.getPosition(), "0");
                numElements = new ConstValue(token);
              }
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>recordTypeDecl = "type" typeId "=" "record" "{" fieldDecls "}" ";" .</code>
     *
     * @return the parsed record type declaration.  Returns
     *         an empty initial declaration if parsing fails.
     */
    private InitialDecl parseRecordTypeDecl() throws IOException
      {
        try
          {
            match(Symbol.typeRW);
            var typeId = scanner.getToken();
            match(Symbol.identifier);
            match(Symbol.equals);
            match(Symbol.recordRW);
            match(Symbol.leftBrace);

            List<FieldDecl> fieldDecls;
            try
              {
                idTable.openScope(ScopeLevel.RECORD);
                fieldDecls = parseFieldDecls();
              }
            finally
              {
                idTable.closeScope();
              }

            match(Symbol.rightBrace);
            match(Symbol.semicolon);

            var typeDecl = new RecordTypeDecl(typeId, fieldDecls);
            idTable.add(typeDecl);
            return typeDecl;
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(initialDeclFollowers());

            return EmptyInitialDecl.getInstance();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>fieldDecls = { fieldDecl } .</code>
     *
     * @return a list of field declarations.
     */
    private List<FieldDecl> parseFieldDecls() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>fieldDecl = fieldId ":" typeName ";" .</code>
     *
     * @return the parsed field declaration.  Returns null if parsing fails.
     */
    private FieldDecl parseFieldDecl() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>stringTypeDecl = "type" typeId "=" "string" "[" intConstValue "]" ";" .</code>
     *
     * @return the parsed string type declaration.  Returns an
     *         empty initial declaration if parsing fails.
     */
    private InitialDecl parseStringTypeDecl() throws IOException
      {
// ...
            var numElements = parseConstValue();
            if (numElements instanceof EmptyExpression)
              {
                // create a default value for numElements to prevent "not declared" errors
                var token = new Token(Symbol.intLiteral, scanner.getPosition(), "0");
                numElements = new ConstValue(token);
              }
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>typeName = "Integer" | "Boolean" | "Char" | typeId .</code>
     *
     * @return the parsed named type.  Returns Type.UNKNOWN if parsing fails.
     */
    private Type parseTypeName() throws IOException
      {
        var type = Type.UNKNOWN;

        try
          {
            switch (scanner.getSymbol())
              {
                case IntegerRW ->
                  {
                    type = Type.Integer;
                    matchCurrentSymbol();
                  }
                case BooleanRW ->
                  {
                    type = Type.Boolean;
                    matchCurrentSymbol();
                  }
                case CharRW ->
                  {
                    type = Type.Char;
                    matchCurrentSymbol();
                  }
                case identifier ->
                  {
                    var typeId = scanner.getToken();
                    matchCurrentSymbol();
                    var decl = idTable.get(typeId.getText());

                    if (decl != null)
                      {
                        if (decl instanceof ArrayTypeDecl || decl instanceof RecordTypeDecl
                                || decl instanceof StringTypeDecl)
                            type = decl.getType();
                        else
                          {
                            var errorMsg = "Identifier \"" + typeId + "\" is not a valid type name.";
                            throw error(typeId.getPosition(), errorMsg);
                          }
                      }
                    else
                      {
                        var errorMsg = "Identifier \"" + typeId + "\" has not been declared.";
                        throw error(typeId.getPosition(), errorMsg);
                      }
                  }
                default -> throw error("Invalid type name.");
              }
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(EnumSet.of(Symbol.semicolon,  Symbol.comma,
                               Symbol.rightParen, Symbol.leftBrace));
          }

        return type;
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>subprogramDecls = { subprogramDecl } .</code>
     *
     * @return the list of subprogram declarations.
     */
    private List<SubprogramDecl> parseSubprogramDecls() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>subprogramDecl = procedureDecl | functionDecl .</code>
     *
     * @return the parsed subprogram declaration.  Returns an
     *         empty subprogram declaration if parsing fails.
     */
    private SubprogramDecl parseSubprogramDecl() throws IOException
      {
// ...   throw an internal error if the symbol is not one of procedureRW or functionRW
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>procedureDecl = "proc" procId "(" [ formalParameters ] ")"
     *                       "{" initialDecls statements "}" .</code>
     *
     * @return the parsed procedure declaration.  Returns an
     *         empty subprogram declaration if parsing fails.
     */
    private SubprogramDecl parseProcedureDecl() throws IOException
      {
        try
          {
            match(Symbol.procRW);
            var procId = scanner.getToken();
            match(Symbol.identifier);

            var procDecl = new ProcedureDecl(procId);
            idTable.add(procDecl);
            match(Symbol.leftParen);

            try
              {
                idTable.openScope(ScopeLevel.LOCAL);

                if (scanner.getSymbol().isParameterDeclStarter())
                    procDecl.setFormalParams(parseFormalParameters());
                match(Symbol.rightParen);
                match(Symbol.leftBrace);
                procDecl.setInitialDecls(parseInitialDecls());

                subprogramContext.beginSubprogramDecl(procDecl);
                procDecl.setStatements(parseStatements());
                subprogramContext.endSubprogramDecl();
              }
            finally
              {
                idTable.closeScope();
              }

            match(Symbol.rightBrace);
            return procDecl;
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(subprogDeclFollowers);
            return EmptySubprogramDecl.getInstance();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>functionDecl = "fun" funcId "(" [ formalParameters ] ")" ":" typeName
     *                      "{" initialDecls statements "}" .</code>
     *
     * @return the parsed function declaration.  Returns an
     *         empty subprogram declaration if parsing fails.
     */
    private SubprogramDecl parseFunctionDecl() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>formalParameters = parameterDecl { "," parameterDecl } .</code>
     *
     * @return a list of formal parameter declarations.
     */
    private List<ParameterDecl> parseFormalParameters() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>parameterDecl = [ "var" ] paramId ":" typeName .</code>
     *
     * @return the parsed parameter declaration.  Returns null if parsing fails.
     */
    private ParameterDecl parseParameterDecl() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>statements = { statement } .</code>
     *
     * @return a list of statements.
     */
    private List<Statement> parseStatements() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>statement = assignmentStmt | procedureCallStmt | compoundStmt | ifStmt
     *                 | loopStmt | exitStmt | readStmt | writeStmt | writelnStmt
     *                 | returnStmt .</code>
     *
     * @return the parsed statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseStatement() throws IOException
      {
        // assumes that scanner.getSymbol() can start a statement
        assert scanner.getSymbol().isStmtStarter() : "Invalid statement.";

        try
          {
            Statement stmt;
            var symbol = scanner.getSymbol();

            if (symbol == Symbol.identifier)
              {
                // Handle identifiers based on how they are declared,
                // or use the lookahead symbol if not declared.
                var idStr = scanner.getText();
                var decl  = idTable.get(idStr);

                if (decl != null)
                  {
                    if (decl instanceof VariableDecl)
                        stmt = parseAssignmentStmt();
                    else if (decl instanceof ProcedureDecl)
                        stmt = parseProcedureCallStmt();
                    else
                        throw error("Identifier \"" + idStr + "\" cannot start a statement.");
                  }
                else
                  {
                    // make parsing decision using lookahead symbol
// ...
                  }
              }
            else if (symbol == Symbol.leftBrace)
                stmt = parseCompoundStmt();
// ...
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);

            // Error recovery here is complicated for identifiers since they can both
            // start a statement and appear elsewhere in the statement.  (Consider,
            // for example, an assignment statement or a procedure call statement.)
            // Since the most common error is to declare or reference an identifier
            // incorrectly, we will assume that this is the case and advance to the
            // end of the current statement before performing error recovery.
            scanner.advanceTo(EnumSet.of(Symbol.semicolon, Symbol.rightBrace));
            recover(stmtFollowers);
            return EmptyStatement.getInstance();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>assignmentStmt = variable ":=" expression ";" .</code>
     *
     * @return the parsed assignment statement.  Returns
     *         an empty statement if parsing fails.
     */
    private Statement parseAssignmentStmt() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>compoundStmt = "{" statements "}" .<\code>
     *
     * @return the parsed compound statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseCompoundStmt() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>ifStmt = "if" booleanExpr "then" statement  [ "else" statement ] .</code>
     *
     * @return the parsed if statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseIfStmt() throws IOException
      {
// ...   Hint: Use null for elseStmt if there is no else statement.
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>loopStmt = [ "while" booleanExpr ] "loop" statement .</code>
     *
     * @return the parsed loop statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseLoopStmt() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>exitStmt = "exit" [ "when" booleanExpr ] ";" .</code>
     *
     * @return the parsed exit statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseExitStmt() throws IOException
      {
// ...   Hint: Use null for whenExpr if there is no when expression.
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>readStmt = "read" variable ";" .</code>
     *
     * @return the parsed read statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseReadStmt() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>writeStmt = "write" expressions ";" .</code>
     *
     * @return the parsed write statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseWriteStmt() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>expressions = expression [ "," expression ] .</code>
     *
     * @return a list of expressions.
     */
    private List<Expression> parseExpressions() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>writelnStmt = "writeln" [ expressions ] ";" .</code>
     *
     * @return the parsed writeln statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseWritelnStmt() throws IOException
      {
        try
          {
            match(Symbol.writelnRW);

            List<Expression> expressions;
            if (scanner.getSymbol().isExprStarter())
                expressions = parseExpressions();
            else
                expressions = Collections.emptyList();

            match(Symbol.semicolon);
            return new OutputStmt(expressions, true);
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(stmtFollowers);
            return EmptyStatement.getInstance();
          }
      }

    /**
     * Parse the following grammar rules:<br>
     * <code>procedureCallStmt = procId "(" [ actualParameters ] ")" ";" .<br>
     *       actualParameters = expressions .</code>
     *
     * @return the parsed procedure call statement.  Returns
     *         an empty statement if parsing fails.
     */
    private Statement parseProcedureCallStmt() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>returnStmt = "return" [ expression ] ";" .</code>
     *
     * @return the parsed return statement.  Returns an empty statement if parsing fails.
     */
    private Statement parseReturnStmt() throws IOException
      {
// ...   Hint: Use null for returnExpr if there is no return expression.
      }

    /**
     * Parse the following grammar rules:<br>
     * <code>variable = ( varId | paramId ) { indexExpr | fieldExpr } .<br>
     *       indexExpr = "[" expression "]" .<br>
     *       fieldExpr = "." fieldId .</code>
     * <br>
     * This helper method provides common logic for methods parseVariable() and
     * parseVariableExpr().  The method does not handle any ParserExceptions but
     * throws them back to the calling method where they can be handled appropriately.
     *
     * @return the parsed variable.
     * @throws ParserException if parsing fails.
     * @see #parseVariable()
     * @see #parseVariableExpr()
     */
    private Variable parseVariableCommon() throws IOException, ParserException
      {
        var idToken = scanner.getToken();
        match(Symbol.identifier);
        var decl = idTable.get(idToken.getText());

        if (decl == null)
          {
            var errorMsg = "Identifier \"" + idToken + "\" has not been declared.";
            throw error(idToken.getPosition(), errorMsg);
          }
        else if (!(decl instanceof VariableDecl))
          {
            var errorMsg = "Identifier \"" + idToken + "\" is not a variable.";
            throw error(idToken.getPosition(), errorMsg);
          }

        var variableDecl  = (VariableDecl) decl;
        var selectorExprs = new ArrayList<Expression>(5);

        while (scanner.getSymbol().isSelectorStarter())
          {
            if (scanner.getSymbol() == Symbol.leftBracket)
              {
                // parse index expression
                match(Symbol.leftBracket);
                selectorExprs.add(parseExpression());
                match(Symbol.rightBracket);
              }
            else if (scanner.getSymbol() == Symbol.dot)
              {
                // parse field expression
                match(Symbol.dot);
                var fieldId = scanner.getToken();
                match(Symbol.identifier);
                selectorExprs.add(new FieldExpr(fieldId));
              }
          }

        return new Variable(variableDecl, idToken.getPosition(), selectorExprs);
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>variable = ( varId | paramId ) { indexExpr | fieldExpr } .</code>
     *
     * @return the parsed variable.  Returns null if parsing fails.
     */
    private Variable parseVariable() throws IOException
      {
        try
          {
            return parseVariableCommon();
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(EnumSet.of(Symbol.assign, Symbol.semicolon));
            return null;
          }
      }

    /**
     * Parse the following grammar rules:<br>
     * <code>expression = relation { logicalOp relation } .<br>
     *       logicalOp = "and" | "or" . </code>
     *
     * @return the parsed expression.
     */
    private Expression parseExpression() throws IOException
      {
        var expr = parseRelation();

        while (scanner.getSymbol().isLogicalOperator())
          {
            var operator = scanner.getToken();
            matchCurrentSymbol();
            expr = new LogicalExpr(expr, operator, parseRelation());
          }

        return expr;
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>relation = simpleExpr [ relationalOp simpleExpr ] .<br>
     *   relationalOp = "=" | "!=" | "&lt;" | "&lt;=" | "&gt;" | "&gt;=" .</code>
     *
     * @return the parsed relational expression.
     */
    private Expression parseRelation() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rules:<br>
     * <code>simpleExpr = [ signOp ] term { addingOp term } .<br>
     *       signOp = "+" | "-" .<br>
     *       addingOp = "+" | "-" .</code>
     *
     * @return the parsed simple expression.
     */
    private Expression parseSimpleExpr() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rules:<br>
     * <code>term = factor { multiplyingOp factor } .<br>
     *       multiplyingOp = "*" | "/" | "mod" .</code>
     *
     * @return the parsed term expression.
     */
    private Expression parseTerm() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>factor = "not" factor | constValue | variableExpr | functionCallExpr
     *              | "(" expression ")" .</code>
     *
     * @return the parsed factor expression.  Returns an empty expression if parsing fails.
     */
    private Expression parseFactor() throws IOException
      {
        try
          {
            Expression expr;

            if (scanner.getSymbol() == Symbol.notRW)
              {
                var operator = scanner.getToken();
                matchCurrentSymbol();
                expr = new NotExpr(operator, parseFactor());
              }
            else if (scanner.getSymbol().isLiteral())
              {
                // Handle constant literals separately from constant identifiers.
                expr = parseConstValue();
              }
            else if (scanner.getSymbol() == Symbol.identifier)
              {
                // Handle identifiers based on how they are declared,
                // or use the lookahead symbol if not declared.
                var idStr = scanner.getText();
                var decl  = idTable.get(idStr);

                if (decl != null)
                  {
                    if (decl instanceof ConstDecl)
                        expr = parseConstValue();
                    else if (decl instanceof VariableDecl)
                        expr = parseVariableExpr();
                    else if (decl instanceof FunctionDecl)
                        expr = parseFunctionCallExpr();
                    else
                      {
                        var errorPos = scanner.getPosition();
                        var errorMsg = "Identifier \"" + idStr + "\" is not valid as an expression.";

                        // special handling when procedure call is used as a function call
                        if (decl instanceof ProcedureDecl)
                          {
                            scanner.advance();
                            if (scanner.getSymbol() == Symbol.leftParen)
                              {
                                scanner.advanceTo(Symbol.rightParen);
                                scanner.advance();   // advance past the right paren
                              }
                          }

                        throw error(errorPos, errorMsg);
                      }
                  }
                else
                  {
                    // Make parsing decision using an additional lookahead symbol.
                    if (scanner.lookahead(2).getSymbol() == Symbol.leftParen)
                        expr = parseFunctionCallExpr();
                    else
                        throw error("Identifier \"" + scanner.getToken()
                                  + "\" has not been declared.");
                  }
              }
            else if (scanner.getSymbol() == Symbol.leftParen)
              {
                matchCurrentSymbol();
                expr = parseExpression();
                match(Symbol.rightParen);
              }
            else
                throw error("Invalid expression.");

            return expr;
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(factorFollowers);
            return EmptyExpression.getInstance();
          }
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>constValue = literal | constId .</code>
     *
     * @return the parsed constant value.  Returns
     *         an empty expression if parsing fails.
     */
    private Expression parseConstValue() throws IOException
      {
// ...
      }

    /**
     * Parse the following grammar rule:<br>
     * <code>variableExpr = variable .</code>
     *
     * @return the parsed variable expression.  Returns
     *         an empty expression if parsing fails.
     */
    private Expression parseVariableExpr() throws IOException
      {
        try
          {
            var variable = parseVariableCommon();
            return new VariableExpr(variable);
          }
        catch (ParserException e)
          {
            errorHandler.reportError(e);
            recover(factorFollowers);
            return EmptyExpression.getInstance();
          }
      }

    /**
     * Parse the following grammar rules:<br>
     * <code>functionCallExpr = funcId "(" [ actualParameters ] ")" .<br>
     *       actualParameters = expressions .</code>
     *
     * @return the parsed function call expression.  Returns
     *         an empty expression if parsing fails.
     */
    private Expression parseFunctionCallExpr() throws IOException
      {
// ...
      }

    // Utility parsing methods

    /**
     * Check that the current scanner symbol is the expected symbol.  If it
     * is, then advance the scanner.  Otherwise, throw a ParserException.
     */
    private void match(Symbol expectedSymbol) throws IOException, ParserException
      {
        if (scanner.getSymbol() == expectedSymbol)
            scanner.advance();
        else
          {
            var errorMsg = "Expecting \"" + expectedSymbol + "\" but found \""
                         + scanner.getToken() + "\" instead.";
            throw error(errorMsg);
          }
      }

    /**
     * Advance the scanner.  This method represents an unconditional
     * match with the current scanner symbol.
     */
    private void matchCurrentSymbol() throws IOException
      {
        scanner.advance();
      }

    /**
     * Advance the scanner until the current symbol is one of the
     * symbols in the specified set of follows.
     */
    private void recover(Set<Symbol> followers) throws IOException
      {
        scanner.advanceTo(followers);
      }

    /**
     * Create a parser exception with the specified error message and
     * the current scanner position.
     */
    private ParserException error(String errorMsg)
      {
        return error(scanner.getPosition(), errorMsg);
      }

    /**
     * Create a parser exception with the specified error position and error message.
     */
    private ParserException error(Position errorPos, String errorMsg)
      {
        return new ParserException(errorPos, errorMsg);
      }

    /**
     * Create an internal compiler exception with the specified error
     * message and the current scanner position.
     */
    private InternalCompilerException internalError(String errorMsg)
      {
        return new InternalCompilerException(scanner.getPosition(), errorMsg);
      }
  }
