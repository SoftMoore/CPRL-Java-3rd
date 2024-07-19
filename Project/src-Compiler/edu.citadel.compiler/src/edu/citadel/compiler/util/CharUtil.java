package edu.citadel.compiler.util;

/**
 * Utilities for recognizing binary and hexadecimal digits.
 */
public class CharUtil
  {
    /**
     * Returns true only if the specified character is a binary digit
     * ('0' or '1').
     */
    public static boolean isBinaryDigit(char ch)
      {
        return ch == '0' || ch == '1';
      }

    /**
     * Returns true only if the specified character is a hex digit.<br>
     * <code>'0'..'9' + 'A'..'F' + 'a'..'f'</code>
     */
    public static boolean isHexDigit(int ch)
      {
        return (ch >= '0' && ch <= '9')
            || (ch >= 'a' && ch <= 'f')
            || (ch >= 'A' && ch <= 'F');
      }
  }
