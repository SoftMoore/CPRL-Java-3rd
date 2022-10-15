package edu.citadel.cvm.assembler.optimize;

import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.InstructionOneArg;

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

        Instruction instruction0 = instructions.get(instNum);
        Instruction instruction1 = instructions.get(instNum + 1);

        Symbol symbol0 = instruction0.getOpCode().getSymbol();
        Symbol symbol1 = instruction1.getOpCode().getSymbol();

        // Check that we have two ALLOC symbols.
        if (symbol0 == Symbol.ALLOC && symbol1 == Symbol.ALLOC)
          {
            InstructionOneArg inst0 = (InstructionOneArg)instruction0;
            int constValue0 = inst0.argToInt();

            InstructionOneArg inst1 = (InstructionOneArg)instruction1;
            int constValue1 = inst1.argToInt();

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
