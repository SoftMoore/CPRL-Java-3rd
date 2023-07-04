package edu.citadel.assembler;

import edu.citadel.compiler.Source;
import edu.citadel.compiler.Position;
import edu.citadel.compiler.ScannerException;
import edu.citadel.compiler.ErrorHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Performs lexical analysis for CVM assembly language.
 */
public class Scanner
  {
    private Source source;
    private ErrorHandler errorHandler;

    private StringBuilder scanBuffer;

    // maps opcode names to opcode symbols
    private HashMap<String, Symbol> opcodeMap;

    //  The current token in the source file.
    private Token token;

    /**
     * Initialize scanner with its associated source and error handler,
     * and advance to the first token.
     */
    public Scanner(Source source, ErrorHandler errorHandler) throws IOException
      {
        this.source = source;
        this.errorHandler = errorHandler;
        scanBuffer = new StringBuilder(100);

        // initialize HashMap with reserved word symbols
        opcodeMap = new HashMap<>(100);
        for (Symbol symbol : Symbol.values())
          {
            if (symbol.isOpcode())
                opcodeMap.put(symbol.toString(), symbol);
          }

        advance();   // advance to the first token
      }

    /**
     * Returns the current token in the source file.
     */
    public Token getToken()
      {
        return token;
      }

    /**  Short version for getToken().getSymbol(), the next lookahead symbol. */
    public Symbol getSymbol()
      {
        return token.getSymbol();
      }

    /**  Short version for getToken().getText(), the next lookahead text. */
    public String getText()
      {
        return token.getText();
      }

    /**  Short version for getToken().getPosition(), the next lookahead position. */
    public Position getPosition()
      {
        return token.getPosition();
      }

    /**
     * Advance scanner to the next token.
     */
    public void advance() throws IOException
      {
        token = nextToken();
      }

    /**
     * Advance until lookahead(1).symbol matches one of the symbols
     * in the given set or until end of file is encountered.
     */
    public void advanceTo(Set<Symbol> symbols) throws IOException
      {
        while (!symbols.contains(token.getSymbol()) && token.getSymbol() != Symbol.EOF)
            advance();
      }

    /**
     * Returns the next token in the source file.
     */
    public Token nextToken() throws IOException
      {
        var symbol   = Symbol.UNKNOWN;
        var position = source.getCharPosition();
        var text     = "";

        try
          {
            skipWhiteSpace();

            // currently at starting character of next token
            position = source.getCharPosition();

            if (source.getChar() == Source.EOF)
              {
                // set symbol but don't advance
                symbol = Symbol.EOF;
              }
            else if (Character.isLetter((char) source.getChar())
                  || source.getChar() == '_')
              {
                // opcode symbol, identifier, or label
                String idString = scanIdentifier();
                symbol = getIdentifierSymbol(idString);

                if (symbol == Symbol.identifier)
                  {
                    // check to see if we have a label
                    if (source.getChar() == ':')
                      {
                        symbol = Symbol.labelId;
                        text   = idString + ":";
                        source.advance();
                      }
                    else
                        text = idString;
                  }
              }
            else if (Character.isDigit((char) source.getChar()))
              {
                text   = scanIntegerLiteral();
                symbol = Symbol.intLiteral;
              }
            else
                switch ((char) source.getChar())
                  {
                    case ';' ->
                      {
                        skipComment();
                        return nextToken();
                      }
                    case '\'' ->
                      {
                        text   = scanCharLiteral();
                        symbol = Symbol.charLiteral;
                      }
                    case '\"' ->
                      {
                        text   = scanStringLiteral();
                        symbol = Symbol.stringLiteral;
                      }
                    case '-' ->
                      {
                        // should be a negative integer literal
                        source.advance();
                        if (Character.isDigit((char) source.getChar()))
                          {
                            text   = "-" + scanIntegerLiteral();
                            symbol = Symbol.intLiteral;
                          }
                        else
                            throw error("Expecting an integer literal");
                      }
                    default ->
                      {
                        throw error(position, "Invalid Token");
                      }
                  }
          }
        catch (ScannerException e)
          {
            // stop on first error -- no error recovery
            errorHandler.reportError(e);
            System.exit(1);
          }

        return new Token(symbol, position, text);
      }

    /**
     * Clear the scan buffer (makes it empty).
     */
    private void clearScanBuffer()
      {
        scanBuffer.delete(0, scanBuffer.length());
      }

    /**
     * Scans characters in the source file for a valid identifier using the
     * lexical rule: identifier = ( letter | "_" ) { letter | digit } .
     */
    private String scanIdentifier() throws IOException
      {
        // assumes that source.getChar() is the first character of the identifier
        assert Character.isLetter((char) source.getChar()) || source.getChar() == '_' :
                "Check identifier start for letter at position " + getPosition() + ".";

        clearScanBuffer();

        do
          {
            scanBuffer.append((char) source.getChar());
            source.advance();
          }
        while (Character.isLetterOrDigit((char) source.getChar()));

        return scanBuffer.toString();
      }

    /**
     * Scans characters in the source file for a valid integer literal. Assumes
     * that source.getChar() is the first character of the Integer literal.
     *
     * @return the string of digits for the integer literal.
     */
    private String scanIntegerLiteral() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the first digit of the integer literal
        assert Character.isDigit((char) source.getChar()) :
            "Check integer literal start for digit at position " + getPosition() + ".";

        clearScanBuffer();

        do
          {
            scanBuffer.append((char) source.getChar());
            source.advance();
          }
        while (Character.isDigit((char) source.getChar()));

        return scanBuffer.toString();
      }

    private void skipComment() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the leading ';'
        assert (char) source.getChar() == ';' : "Check for ';' to start comment.";

        skipToEndOfLine();
        source.advance();
      }

    /**
     * Scans characters in the source file for a String literal.
     * Escaped characters are converted; e.g., '\t' is converted to
     * the tab character.  Assumes that source.getChar() is the
     * opening quote (") of the String literal.
     *
     * @return the string of characters for the string literal, including
     *         opening and closing quotes
     */
    private String scanStringLiteral() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the opening double quote for the string literal
        assert (char) source.getChar() == '\"' :
            "Check for opening quote (\") at position " + getPosition() + ".";

        clearScanBuffer();

        do
          {
            checkGraphicChar(source.getChar());
            char c = (char) source.getChar();

            if (c == '\\')
                scanBuffer.append(scanEscapedChar());   // call to scanEscapedChar()
                                                        // advances source
            else {
                scanBuffer.append(c);
                source.advance();
            }
          }
        while ((char) source.getChar() != '\"');

        scanBuffer.append('\"');   // append closing quote
        source.advance();

        return scanBuffer.toString();
      }

    /**
     * Scans characters in the source file for a valid char literal.
     * Escaped characters are converted; e.g., '\t' is converted to the
     * tab character.  Assumes that source.getChar() is the opening
     * single quote (') of the Char literal.
     *
     * @return the string of characters for the char literal, including
     *         opening and closing single quotes.
     */
    private String scanCharLiteral() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the opening single quote for the
        // char literal
        assert (char) source.getChar() == '\'' :
            "Check for opening quote (\') at position " + getPosition() + ".";

        clearScanBuffer();

        char c = (char) source.getChar();           // opening quote
        scanBuffer.append(c);                       // append the opening quote

        source.advance();
        checkGraphicChar(source.getChar());
        c = (char) source.getChar();                // the character literal

        if (c == '\\')                              // escaped character
            scanBuffer.append(scanEscapedChar());   // call to scanEscapedChar() advances source
        else if (c == '\'')                         // check for empty char literal
          {
            source.advance();
            throw error("Char literal must contain exactly one character.");
          }
        else
          {
            scanBuffer.append(c);                   // append the character literal
            source.advance();
          }

        checkGraphicChar(source.getChar());
        c = (char) source.getChar();                // should be the closing quote

        if (c == '\'')
          {
            scanBuffer.append(c);                   // append the closing quote
            source.advance();
          }
        else
            throw error("Char literal not closed properly.");

        return scanBuffer.toString();
      }

    /**
     * Scans characters in the source file for an escaped character; i.e.,
     * a character preceded by a backslash.  This method handles escape
     * characters \t, \n, \r, \", \', and \\.  If the character following
     * a backslash is anything other than one of these characters, then an
     * exception is thrown.  Assumes that source.getChar() is the escape
     * character (\).
     *
     * @return the value for an escaped character.
     */
    private char scanEscapedChar() throws ScannerException, IOException
      {
        // assumes that source.getChar() is a backslash character
        assert (char) source.getChar() == '\\' :
            "Check for escape character ('\\') at position " + getPosition() + ".";

        // Need to save current position for error reporting.
        Position backslashPosition = source.getCharPosition();

        source.advance();
        checkGraphicChar(source.getChar());
        char c = (char) source.getChar();

        source.advance();   // leave source at second character following the backslash

        return switch (c)
          {
            case 't'  -> '\t';   // tab
            case 'n'  -> '\n';   // newline
            case 'r'  -> '\r';   // carriage return
            case '\"' -> '\"';   // double quote
            case '\'' -> '\'';   // single quote
            case '\\' -> '\\';   // backslash
            default   -> throw new ScannerException(backslashPosition, "Illegal escape character.");
          };
      }

    /**
     * Returns the symbol associated with an identifier
     * (Symbol.ADD, Symbol.AND, Symbol.identifier, etc.)
     */
    private Symbol getIdentifierSymbol(String idString)
      {
        return opcodeMap.getOrDefault(idString.toUpperCase(), Symbol.identifier);
      }

    /**
     * Fast skip over white space.
     */
    private void skipWhiteSpace() throws IOException
      {
        while (Character.isWhitespace((char) source.getChar()))
            source.advance();
      }

    /**
     * Advances over source characters to the end of the current line.
     */
    private void skipToEndOfLine() throws ScannerException, IOException
      {
        while ((char) source.getChar() != '\n')
          {
            source.advance();
            checkEOF();
          }
      }

    /**
     * Checks that the integer represents a graphic character in the Unicode
     * Basic Multilingual Plane (BMP).
     *
     * @throws ScannerException if the integer does not represent a BMP graphic character.
     */
    private void checkGraphicChar(int n) throws ScannerException
      {
        if (n == Source.EOF)
            throw error("End of file reached before closing quote.");
        else if (n > 0xffff)
            throw error("Character not in Unicode Basic Multilingual Pane (BMP)");
        else
          {
            char c = (char) n;
            if (c == '\r' || c == '\n')   // special check for end of line
                throw error("Char and String literals can not extend past end of line.");
            else if (Character.isISOControl(c))
                // Sorry. No ISO control characters.
                throw new ScannerException(source.getCharPosition(),
                    "Control characters not allowed in Char or String literal.");
          }
      }

    /**
     * Returns a ScannerException with the specified error message.
     */
    private ScannerException error(String errorMsg)
      {
        return error(getPosition(), errorMsg);
      }

    /**
     * Returns a ScannerException with the specified position and error message.
     */
    private ScannerException error(Position errorPos, String errorMsg)
      {
        return new ScannerException(errorPos, errorMsg);
      }

    /**
     * Used to check for EOF in the middle of scanning tokens that
     * require closing characters such as strings and comments.
     *
     * @throws ScannerException if source is at end of file.
     */
    private void checkEOF() throws ScannerException
      {
        if (source.getChar() == Source.EOF)
            throw error("Unexpected end of file");
      }
  }
