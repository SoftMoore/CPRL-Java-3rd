package edu.citadel.compiler.util;

/**
 * This class encapsulates several bit manipulation utility methods.
 */
public class BitUtil
  {
    /**
     * Converts a string of 16 0s and 1s to a short value.
     */
    public static short binaryStringToShort(String bits)
      {
        int result = 0;

        if (bits.length() != 16)
          {
            var errorMsg = "*** Bad string length: " + bits.length() + " ***";
            throw new IllegalArgumentException(errorMsg);
          }

        int mask = (1 << 15);

        for (int i = 0; i < 16; ++i)
          {
            char c = bits.charAt(i);
            if (c == '1')
                result = result | mask;
            else if (c != '0')
              {
                var errorMsg = "*** Non-binary character: " + c +" ***";
                throw new IllegalArgumentException(errorMsg);
              }

            mask = mask >>> 1;
          }

        return (short) result;
      }

    /**
     * Returns a binary string representation of the specified integer.
     */
    public static String toBinaryString(int n)
      {
        return toBinaryString(n, Integer.SIZE);
      }

    /**
     * Returns a binary string representation of the least significant
     * (right most) bits for the specified int.
     */
    public static String toBinaryString(int n, int numBits)
      {
        var builder = new StringBuilder();
        int mask = 1 << (numBits - 1);

        for (int count = 0; count < numBits; ++count)
          {
            builder.append((n & mask) == 0 ? '0' : '1');
            mask = mask >>> 1;
          }

        return builder.toString();
      }

    /**
     * Returns a binary string representation of the specified short.
     */
    public static String toBinaryString(short n)
      {
        return toBinaryString(n, Short.SIZE);
      }

    /**
     * Returns a binary string representation of the least significant
     * (right mode) bits for the specified short.
     */
    public static String toBinaryString(short n, int numBits)
      {
        var builder = new StringBuilder();
        int mask = 1 << (numBits - 1);

        for (int count = 0; count < numBits; ++count)
          {
            builder.append((n & mask) == 0 ? '0' : '1');
            mask = mask >>> 1;
          }

        return builder.toString();
      }

    /**
     * Returns a binary string representation of the specified byte.
     */
    public static String toBinaryString(byte n)
      {
        return toBinaryString(n, Byte.SIZE);
      }

    /**
     * Returns a binary string representation of the least significant
     * (right mode) bits for the specified byte.
     */
    public static String toBinaryString(byte n, int numBits)
      {
        var builder = new StringBuilder();
        int mask = 1 << (numBits - 1);

        for (int count = 0; count < numBits; ++count)
          {
            builder.append((n & mask) == 0 ? '0' : '1');
            mask = mask >>> 1;
          }

        return builder.toString();
      }

    /**
     * Returns a hexadecimal string representation of the specified integer.
     */
    public static String toHexString(int n)
      {
        var builder  = new StringBuilder();
        byte[] bytes = ByteUtil.intToBytes(n);

        for (byte b : bytes)
            builder.append(String.format("%02X", b));

        return builder.toString();
      }

    /**
     * Returns a hexadecimal string representation of the specified short.
     */
    public static String toHexString(short n)
      {
        var builder  = new StringBuilder();
        byte[] bytes = ByteUtil.shortToBytes(n);

        for (byte b : bytes)
            builder.append(String.format("%02X", b));

        return builder.toString();
      }

    /**
     * Returns a hexadecimal string representation of the specified byte.
     */
    public static String toHexString(byte n)
      {
        return String.format("%02X", n);
      }

    /**
     * Returns the n least significant (rightmost) bits in the
     * specified value as a signed (2s complement) integer.
     */
    public static int bitsToInt(int value, int n)
      {
        if (n >= Integer.SIZE)
          {
            var errorMsg = "*** Parameter n too large: " + n + " ***";
            throw new IllegalArgumentException(errorMsg);
          }

        // mask out rightmost n bits
        int mask   = -1 >>> (Integer.SIZE - n);
        int result = value & mask;

        // if bit (n - 1) is 1, we have a negative number, so set bits 32..n to 1.
        int nthBitMask = 1 << (n - 1);
        if ((result & nthBitMask) != 0)
          {
            int maskComplement = ~mask;
            result = result | maskComplement;
          }

        return result;
      }

    /**
     * Returns the n least significant (rightmost) bits in the specified
     * value as an unsigned integer; i.e., the most significant bit is not
     * interpreted as a sign bit.
     */
    public static int bitsToUnsigned(int value, int n)
      {
        if (n >= Integer.SIZE)
          {
            var errorMsg = "*** Parameter n too large: " + n + " ***";
            throw new IllegalArgumentException(errorMsg);
          }

        // mask out rightmost n bits
        int mask   = -1 >>> (Integer.SIZE - n);
        int result = value & mask;

        return result;
      }
  }
