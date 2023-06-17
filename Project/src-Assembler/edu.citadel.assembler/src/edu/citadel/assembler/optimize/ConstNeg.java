package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionOneArg;

import java.util.List;

/**
 * Performs a special type of constant folding by replacing an instruction
 * sequence of the form LDCINT x, NEG with LDCINT -x.
 */
public class ConstNeg implements Optimization
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

        // check that we have LDCINT followed by NEG
        if (symbol0 == Symbol.LDCINT && symbol1 == Symbol.NEG)
          {
            var inst0      = (InstructionOneArg) instruction0;
            var constValue = inst0.argToInt();

            // make sure that the NEG instruction does not have any labels
            var inst1Labels = instruction1.getLabels();
            if (inst1Labels.isEmpty())
              {
                inst0.getArg().setText(Integer.toString(-constValue));

                // remove the NEG instruction
                instructions.remove(instNum + 1);
              }
          }
      }
  }
