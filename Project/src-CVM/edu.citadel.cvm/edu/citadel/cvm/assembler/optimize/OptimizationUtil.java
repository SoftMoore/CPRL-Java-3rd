package edu.citadel.cvm.assembler.optimize;


/**
 * Utility class for shift-related optimizations. 
 */
public class OptimizationUtil
  {
    /**
     * If n is a power of 2, returns log2(n) (i.e., returns the exponent);
     * otherwise returns 0. 
     */
    public static byte getShiftAmount(int n)
      {
        byte result = 0;

        while (n > 1)
          {
            if (n % 2 == 1)
                return 0;
            else
              {
                n = n/2;
                ++result;
              }
          }

        return result;
      }
  }
