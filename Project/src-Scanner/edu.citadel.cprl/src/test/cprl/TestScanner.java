package test.cprl;

import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.Source;
import edu.citadel.cprl.Scanner;
import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;

import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestScanner
  {
    private static PrintStream out
            = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    public static void main(String[] args)
      {
        try
          {
            // check arguments
            if (args.length != 1)
                printUsageAndExit();

            out.println("initializing...");

            var fileName = args[0];
            var errorHandler = new ErrorHandler();
            var reader  = new FileReader(fileName, StandardCharsets.UTF_8);
            var source  = new Source(reader);
            var scanner = new Scanner(source, 4, errorHandler);   // 4 lookahead tokens
            Token token;

            out.println("starting main loop...");
            out.println();

            do
              {
                token = scanner.getToken();
                printToken(token);
                scanner.advance();
              }
            while (token.getSymbol() != Symbol.EOF);

            out.println();
            out.println("...done");
          }
        catch (Exception e)
          {
            e.printStackTrace();
          }
      }

    private static void printToken(Token token)
      {
        out.printf("line: %2d   char: %2d   token: ",
            token.getPosition().getLineNumber(),
            token.getPosition().getCharNumber());

        var symbol = token.getSymbol();
        if (symbol.isReservedWord())
            out.print("Reserved Word -> ");
        else if (symbol == Symbol.identifier    || symbol == Symbol.intLiteral
              || symbol == Symbol.stringLiteral || symbol == Symbol.charLiteral)
            out.print(token.getSymbol().toString() + " -> ");

        out.println(token.getText());
      }

    private static void printUsageAndExit()
      {
        out.println("Usage: java test.cprl.TestScanner <test file>");
        out.println();
        System.exit(0);
      }
  }
