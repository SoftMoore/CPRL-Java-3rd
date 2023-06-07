package test.compiler;

import edu.citadel.compiler.Source;

import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestSource
  {
    public static void main(String[] args)
      {
        try
          {
            var fileName = args[0];
            var reader   = new FileReader(fileName, StandardCharsets.UTF_8);
            var source   = new Source(reader);
            var out      = new PrintStream(System.out, true, StandardCharsets.UTF_8);

            while (source.getChar() != Source.EOF)
              {
                int c = source.getChar();

                if (c == '\n')
                    out.println("\\n\t" + source.getCharPosition());
                else if (c != '\r')
                    out.println((char) c + "\t" + source.getCharPosition());

                source.advance();
              }
          }
        catch (Exception e)
          {
            e.printStackTrace();
          }
      }
  }
