package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.ast.Instruction;

import java.util.List;

/**
 * If an instruction without labels follows a return or an unconditional
 * branch, then that instruction is unreachable (dead) and can be removed.
 */
public class DeadCodeElimination implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least 2 instructions remaining
        if (instNum > instructions.size() - 2)
            return;

        var instruction0 = instructions.get(instNum);
        var symbol0      = instruction0.getOpcode().getSymbol();

        // Check that symbol0 is either BR or RET.
        if (symbol0 == Symbol.BR || symbol0 == Symbol.RET)
          {
            var instruction1 = instructions.get(instNum + 1);

            // check that the second instruction does not have any labels
            if (instruction1.getLabels().isEmpty())
              {
                // We are free to remove the second instruction.
                instructions.remove(instNum + 1);
              }
          }
      }
  }
