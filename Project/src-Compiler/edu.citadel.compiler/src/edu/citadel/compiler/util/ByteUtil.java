package edu.citadel.compiler.util;

/**
 * Methods to convert integers and characters to byte representations, and vice versa.
 */
public class ByteUtil
  {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * Convert a single byte to a string of 2 hexadecimal digits.
     */
    public static String byteToHexString(byte b)
      {
        var builder = new StringBuilder(2);
        builder.append(HEX_ARRAY[b >>> 4])
               .append(HEX_ARRAY[b & 0x0F]);
        return builder.toString();
      }

    /**
     * Convert a 2-byte char to a string of 4 hexadecimal digits.
     */
    public static String charToHexString(char c)
      {
        var builder = new StringBuilder(2);
        builder.append(HEX_ARRAY[c >>> 12])
               .append(HEX_ARRAY[(c & 0x0F00) >> 8])
               .append(HEX_ARRAY[(c & 0x00F0) >> 4])
               .append(HEX_ARRAY[c & 0x000F]);
        return builder.toString();
      }

    /**
     * Convert an array of bytes to a string of hexadecimal digits separated by spaces.
     */
    public static String bytesToHexString(byte[] bytes)
      {
        var builder = new StringBuilder(bytes.length*3);
        for (int i = 0; i < bytes.length; ++i)
          {
            int v = bytes[i] & 0xFF;
            builder.append(HEX_ARRAY[v >>> 4]);
            builder.append(HEX_ARRAY[v & 0x0F]);
            builder.append(' ');
          }

        return builder.toString();
      }

    /**
     * Converts 2 bytes to a char.  The bytes passed as arguments are
     * ordered with b0 as the high order byte and b1 as the low order byte.
     */
    public static char bytesToChar(byte b0, byte b1)
      {
        return (char) ((((int) b0 << 8) & 0x0000FF00) | ((int) b1 & 0x000000FF));
      }

    /**
     * Converts 4 bytes to an int.  The bytes passed as arguments are
     * ordered with b0 as the high order byte and b3 as the low order byte.
     */
    public static int bytesToInt(byte b0, byte b1, byte b2, byte b3)
      {
        return (int) b0 << 24 & 0xFF000000
             | (int) b1 << 16 & 0x00FF0000
             | (int) b2 << 8  & 0x0000FF00
             | (int) b3       & 0x000000FF;
      }

    /**
     * Converts a byte to an int.  The specified byte is the the low
     * order (least significant) byte for the int and the three high
     * order bytes are all zero.
     */
    public static int byteToInt(byte b)
      {
        byte zero = (byte) 0;
        return bytesToInt(zero, zero, zero, b);
      }

    /**
     * Converts a char to an array of 2 bytes.  The bytes in the return
     * array are ordered with the byte at index 0 as the high order byte
     * and the byte at index 1 as the low order byte.
     */
    public static byte[] charToBytes(char c)
      {
        byte[] result = new byte[2];
        result[0] = (byte) ((c >>> 8) & 0x00FF);
        result[1] = (byte) ((c >>> 0) & 0x00FF);
        return result;
      }

    /**
     * Converts a short to an array of 2 bytes.  The bytes in the return
     * array are ordered with the byte at index 0 as the high order byte
     * and the byte at index 1 as the low order byte.
     */
    public static byte[] shortToBytes(short n)
      {
        byte[] result = new byte[2];
        result[0] = (byte) ((n >>> 8) & 0x00FF);
        result[1] = (byte) ((n >>> 0) & 0x00FF);
        return result;
      }

    /**
     * Converts an int to an array of 4 bytes.  The bytes in the return
     * array are ordered with the byte at index 0 as the high order byte
     * and the byte at index 3 as the low order byte.
     */
    public static byte[] intToBytes(int n)
      {
        byte[] result = new byte[4];
        result[0] = (byte) ((n >>> 24) & 0x000000FF);
        result[1] = (byte) ((n >>> 16) & 0x000000FF);
        result[2] = (byte) ((n >>> 8)  & 0x000000FF);
        result[3] = (byte) ((n >>> 0)  & 0x000000FF);
        return result;
      }

    /**
     * Returns the low order (least significant) byte of the specified integer.
     */
    public static byte intToByte(int n)
      {
        byte[] bytes = intToBytes(n);
        return bytes[3];
      }
  }
