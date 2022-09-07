package edu.citadel.compiler.util;


/**
 * Utility class for formatting strings.
 */
public class StringUtil
  {
    /**
     * Formats an integer as right-justified within the specified field
     * width by prepending a sufficient number of blank spaces.
     * 
     * @return the formatted string representation for the integer.
     */
    public static String format(int n, int fieldWidth)
      {
        String intStr = Integer.toString(n);

        if (intStr.length() >= fieldWidth)
            return intStr;
        else
          {
            var builder = new StringBuilder(fieldWidth);

            for (int i = intStr.length();  i < fieldWidth;  ++i)
                builder.append(' ');

            builder.append(intStr);

            return builder.toString();
          }
      }
  }
