package edu.citadel.assembler.optimize;

import java.util.List;

/**
 * This class is used to retrieve the list of all optimizations.
 */
public class Optimizations
  {
    private static final List<Optimization> optimizations;

    public static List<Optimization> getOptimizations()
      {
        return optimizations;
      }

    static
      {
        optimizations = List.of
          (
            new ConstFolding(),
            new IncDec(),
            new IncDec2(),
            new ShiftLeftRight(),
            new ShiftLeft(),
            new BranchingReduction(),
            new ConstNeg(),
            new LoadSpecialConstants(),
            new Allocate(),
            new DeadCodeElimination(),
            new ReturnSpecialConstants()
          );
      }
  }
