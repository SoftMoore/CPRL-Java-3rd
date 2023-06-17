package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionOneArg;

import java.util.List;

/**
 * Using function composition in CPRL can generate 2 or more consecutive
 * ALLOC instructions.  This optimization replaces an instruction sequence
 * of the form ALLOC n, ALLOC m with ALLOC (n + m).
 */
public class Allocate implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least 2 instructions remaining
        if (instNum > instructions.size() - 2)
            return;

        var instruction0 = instructions.get(instNum);
        var instruction1 = instructions.get(instNum + 1);

        var symbol0 = instruction0.getOpcode().getSymbol();
        var symbol1 = instruction1.getOpcode().getSymbol();

        // Check that we have two ALLOC symbols.
        if (symbol0 == Symbol.ALLOC && symbol1 == Symbol.ALLOC)
          {
            var inst0 = (InstructionOneArg) instruction0;
            var constValue0 = inst0.argToInt();

            var inst1 = (InstructionOneArg) instruction1;
            var constValue1 = inst1.argToInt();

            // Make sure that the second ALLOC instruction does not have any labels.
            if (instruction1.getLabels().isEmpty())
              {
                // We are free to make this optimization.
                inst0.getArg().setText(Integer.toString(constValue0 + constValue1));

                // remove the second ALLOC instruction
                instructions.remove(instNum + 1);
              }
          }
      }
  }
