package test.compiler;


import edu.citadel.compiler.util.ByteUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TestByteUtil
  {
    public static void main(String[] args) throws Exception
      {
        int  n, n2;
        char c, c2;
        byte[] bytes;

        var in = new BufferedReader(new InputStreamReader(System.in));

        do
          {
            System.out.print("Enter a value for integer n (0 to stop): ");
            String line = in.readLine();
            n = Integer.parseInt(line);

            System.out.println("n = " + n);

            bytes = ByteUtil.intToBytes(n);
            System.out.println("bytes[] = [" + bytes[0] + ", " + bytes[1] + ", "
                                             + bytes[2] + ", " + bytes[3] + "]");

            n2 = ByteUtil.bytesToInt(bytes[0], bytes[1], bytes[2], bytes[3]);
            System.out.println("n2 = " + n2);
            System.out.println();
          }
        while (n != 0);

        c = 'z';

        do
          {
            System.out.print("Enter a letter or a numeric value for character c (z to stop): ");
            String line = in.readLine();
            
            if (line != null && line.length() > 0)
              {
                if (Character.isDigit(line.charAt(0)))
                  {
                    // process a number
                    n = Integer.parseInt(line);

                    // convert number to a char
                    c = (char) n;

                    System.out.println("c = " + c);

                    bytes = ByteUtil.charToBytes(c);
                    System.out.println("bytes[] = [" + bytes[0] + ", " + bytes[1] + "]");

                    c2 = ByteUtil.bytesToChar(bytes[0], bytes[1]);
                    System.out.println("c2 = " + c2);
                    
                    System.out.println("charToHexString = " + ByteUtil.charToHexString(c));
                    System.out.println();
                  }
                else
                  {
                    // process a character
                    c = line.charAt(0);

                    System.out.println("c = " + c);

                    bytes = ByteUtil.charToBytes(c);
                    System.out.println("bytes[] = [" + bytes[0] + ", " + bytes[1] + "]");

                    c2 = ByteUtil.bytesToChar(bytes[0], bytes[1]);
                    System.out.println("c2 = " + c2);

                    System.out.println("charToHexString = " + ByteUtil.charToHexString(c));
                    System.out.println();
                  }
              }
          }
        while (c != 'z');
      }
  }