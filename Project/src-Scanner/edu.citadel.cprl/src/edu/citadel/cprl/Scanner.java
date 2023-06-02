package edu.citadel.cprl;

import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.Position;
import edu.citadel.compiler.ScannerException;
import edu.citadel.compiler.Source;

import java.io.IOException;
import java.util.Set;

/**
 * Performs lexical analysis for the CPRL programming language.
 * Implements k tokens of lookahead.
 */
public final class Scanner
  {
    private Source source;
    private ErrorHandler errorHandler;

    // buffer to hold lookahead tokens
    private TokenBuffer buffer;

    private StringBuilder scanBuffer;

    /**
     * Construct scanner with its associated source, number
     * of lookahead tokens, and error handler.
     */
    public Scanner(Source source, int k, ErrorHandler errorHandler) throws IOException
      {
        this.source = source;
        this.errorHandler = errorHandler;
        buffer = new TokenBuffer(k);
        scanBuffer = new StringBuilder(100);

        // fill buffer with k tokens
        for (int i = 0; i < k; ++i)
            advance();
      }

    /**
     * The current token; equivalent to lookahead(1).
     */
    public Token getToken()
      {
        return lookahead(1);
      }

    /**
     * The current symbol; equivalent to lookahead(1).getSymbol().
     */
    public Symbol getSymbol()
      {
        return lookahead(1).getSymbol();
      }

    /**
     * The current text; equivalent to lookahead(1).getText().
     */
    public String getText()
      {
        return lookahead(1).getText();
      }

    /**
     * The current position; equivalent to lookahead(1).getPosition().
     */
    public Position getPosition()
      {
        return lookahead(1).getPosition();
      }

    /**
     * Returns the ith lookahead token.  Valid parameter values are in the
     * range 1..k; i.e., the first (current) lookahead token is lookahead(1).
     */
    public Token lookahead(int i)
      {
        assert i >= 1 & i <= 4 : "Range check for lookahead token index";
        return buffer.get(i - 1);
      }

    /**
     * Advance the scanner one token.
     */
    public void advance() throws IOException
      {
        buffer.add(nextToken());
      }

    /**
     * Advance until the current symbol matches the symbol specified
     * in the parameter or until end of file is encountered.
     */
    public void advanceTo(Symbol symbol) throws IOException
      {
        while (getSymbol() != symbol && getSymbol() != Symbol.EOF)
            advance();
      }

    /**
     * Advance until the current symbol matches one of the symbols
     * in the given set or until end of file is encountered.
     */
    public void advanceTo(Set<Symbol> symbols) throws IOException
      {
        while (!symbols.contains(getSymbol()) && getSymbol() != Symbol.EOF)
            advance();
      }

    /**
     * Returns the next token in the source file.
     */
    private Token nextToken() throws IOException
      {
        Symbol symbol   = Symbol.unknown;
        var    position = new Position();
        String text     = "";

        try
          {
            skipWhiteSpace();

            // currently at starting character of next token
            position = source.getCharPosition();

            if (source.getChar() == Source.EOF)
              {
                // set symbol but don't advance source
                symbol = Symbol.EOF;
              }
            else if (isLetter((char) source.getChar()))
              {
                String idString = scanIdentifier();
                symbol = getIdentifierSymbol(idString);

                if (symbol == Symbol.identifier)
                    text = idString;
              }
            else if (isDigit((char) source.getChar()))
              {
                symbol = Symbol.intLiteral;
                text   = scanIntegerLiteral();
              }
            else
              {
                switch((char) source.getChar())
                  {
                    case '+' ->
                      {
                        symbol = Symbol.plus;
                        source.advance();
                      }

// ...

                    case '/' ->
                      {
                        source.advance();
                        if ((char) source.getChar() == '/')
                          {
                            skipComment();
                            return nextToken();   // continue scanning for next token
                          }
                        else
                            symbol = Symbol.divide;
                      }

// ...

                    case '>' ->
                      {
                        source.advance();
                        if ((char) source.getChar() == '=')
                          {
                            symbol = Symbol.greaterOrEqual;
                            source.advance();
                          }
                        else
                            symbol = Symbol.greaterThan;
                      }

// ...

                    default   ->     // error: invalid character
                      {
                        String errorMsg = "Invalid character \'"
                                        + ((char) source.getChar()) + "\'";
                        source.advance();
                        throw error(errorMsg);
                      }
                  }
              }
          }
        catch (ScannerException e)
          {
            errorHandler.reportError(e);
            // set symbol to either EOF or unknown
            symbol = source.getChar() == Source.EOF ? Symbol.EOF : Symbol.unknown;
          }

        return new Token(symbol, position, text);
      }

    /**
     * Returns the symbol associated with an identifier
     * (Symbol.arrayRW, Symbol.ifRW, Symbol.identifier, etc.)
     */
    private Symbol getIdentifierSymbol(String idString)
      {
// ...  Hint: Need an efficient search based on the text of the identifier (parameter idString)
      }

    /**
     * Skip over a comment.
     */
    private void skipComment() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the second '/'
        assert (char) source.getChar() == '/' : "Check for '/' as part of comment.";

// ...
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
     * lexical rule: identifier = letter { letter | digit } .
     *
     * @return the string of letters and digits for the identifier.
     */
    private String scanIdentifier() throws IOException
      {
        // assumes that source.getChar() is the first letter of the identifier
        assert isLetter((char) source.getChar()) :
            "Check identifier start for letter at position "
            + source.getCharPosition() + ".";

// ...
      }

    /**
     * Scans characters in the source file for a valid integer literal.
     * Assumes that source.getChar() is the first character of the Integer literal.
     *
     * @return the string of digits for the integer literal.
     */
    private String scanIntegerLiteral() throws IOException
      {
        // assumes that source.getChar() is the first digit of the integer literal
        assert isDigit((char) source.getChar()) :
            "Check integer literal start for digit at position "
            + source.getCharPosition() + ".";

        clearScanBuffer();

        do
          {
            scanBuffer.append((char) source.getChar());
            source.advance();
          }
        while (isDigit((char) source.getChar()));

        return scanBuffer.toString();
      }

    /**
     * Scan characters in the source file for a String literal.  Escaped
     * characters are not converted; e.g., '\t' is not converted to the tab
     * character since the assembler performs the conversion.  Assumes that
     * source.getChar() is the opening double quote (") of the String literal.
     *
     * @return the string of characters for the string literal, including
     *         opening and closing quotes
     */
    private String scanStringLiteral() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the opening double quote for the string literal
        assert (char) source.getChar() == '\"' :
            "Check for opening quote (\") at position " + source.getCharPosition() + ".";

// ...
      }

    /**
     * Scan characters in the source file for a Char literal.  Escaped
     * characters are not converted; e.g., '\t' is not converted to the tab
     * character since the assembler performs that conversion.  Assumes that
     * source.getChar() is the opening single quote (') of the Char literal.
     *
     * @return the string of characters for the char literal, including
     *         opening and closing single quotes.
     */
    private String scanCharLiteral() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the opening single quote for the char literal
        assert (char) source.getChar() == '\'' :
            "Check for opening quote (\') at position "
            + source.getCharPosition() + ".";

        String errorMsg = "Invalid Char literal.";
        clearScanBuffer();

        // append the opening single quote
        char c = (char) source.getChar();
        scanBuffer.append(c);
        source.advance();

        checkGraphicChar(source.getChar());
        c = (char) source.getChar();

        if (c == '\\')                 // escaped character
          {
            scanBuffer.append(scanEscapedChar());
          }
        else if (c == '\'')            // either '' (empty) or '''; both are invalid
          {
            source.advance();
            c = (char) source.getChar();

            if (c == '\'')             // three single quotes in a row
                source.advance();

            throw error(errorMsg);
          }
        else
          {
            scanBuffer.append(c);
            source.advance();
          }

        c = (char) source.getChar();   // should be the closing single quote
        checkGraphicChar(c);

        if (c == '\'')                 // should be the closing single quote
          {
            scanBuffer.append(c);      // append the closing quote
            source.advance();
          }
        else
            throw error(errorMsg);

        return scanBuffer.toString();
      }

    /**
     * Scans characters in the source file for an escaped character; i.e.,
     * a character preceded by a backslash.  This method checks escape
     * characters \t, \n, \r, \", \', and \\.  If the character following
     * a backslash is anything other than one of these characters, then an
     * exception is thrown.  Note that the escaped character sequence is
     * returned unmodified; i.e., \t returns "\t", not the tab character.
     * Assumes that source.getChar() is the escape character (\).
     *
     * @return the escaped character sequence unmodified.
     */
    private String scanEscapedChar() throws ScannerException, IOException
      {
        // assumes that source.getChar() is the backslash for the escaped char
        assert (char) source.getChar() == '\\' :
            "Check for escape character ('\\') at position "
            + source.getCharPosition() + ".";

        // Need to save current position for error reporting.
        Position backslashPosition = source.getCharPosition();

        source.advance();
        checkGraphicChar(source.getChar());
        char c = (char) source.getChar();

        source.advance();  // leave source at second character following the backslash

        switch (c)
          {
            case 't'  : return "\\t";    // tab
            case 'n'  : return "\\n";    // linefeed (a.k.a. newline)
            case 'r'  : return "\\r";    // carriage return
            case '\"' : return "\\\"";   // double quote
            case '\'' : return "\\\'";   // single quote
            case '\\' : return "\\\\";   // backslash
            default   :
                // report error but return the invalid character
                ScannerException ex = error(backslashPosition, "Illegal escape character.");
                errorHandler.reportError(ex);
                return "\\" + c;
          }
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
     * @throws ScannerException if the integer does not represent a BMP graphic
     *         character.
     */
    private void checkGraphicChar(int n) throws ScannerException
      {
        if (n == Source.EOF)
            throw error("End of file reached before closing quote for Char or String literal.");
        else if (n > 0xffff)
            throw error("Character not in Unicode Basic Multilingual Pane (BMP)");
        else
          {
            char c = (char) n;
            if (c == '\r' || c == '\n')            // special check for end of line
                throw error("Char and String literals can not extend past end of line.");
            else if (Character.isISOControl(c))    // Sorry.  No ISO control characters.
                throw error("Control characters not allowed in Char or String literal.");
          }
      }

    /**
     * Returns true only if the specified character is a letter.<br>
     * `'A'..'Z' + 'a'..'z' (r.e. char class: [A-Za-z])`
     */
    private static boolean isLetter(char ch)
      {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
      }

    /**
     * Returns true only if the specified character is a digit.<br>
     * `'0'..'9' (r.e. char class: [0-9])`
     */
    private static boolean isDigit(char ch)
      {
        return ch >= '0' && ch <= '9';
      }

    /**
     * Returns true only if the specified character is a letter or a digit.<br>
     * `'A'..'Z' + 'a'..'z + '0'..'9' (r.e. char class: [A-Za-z0-9])`
     */
    private static boolean isLetterOrDigit(char ch)
      {
        return isLetter(ch) || isDigit(ch);
      }

    /**
     * Returns a scanner exception with the specified error message
     * and current token position.
     */
    private ScannerException error(String errorMsg)
      {
        return error(source.getCharPosition(), errorMsg);
      }

    /**
     * Returns a scanner exception with the specified error message
     * and token position.
     */
    private ScannerException error(Position position, String errorMsg)
      {
        return new ScannerException(position, errorMsg);
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
