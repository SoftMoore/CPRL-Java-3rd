package edu.citadel.cvm.assembler.optimize;

import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;
import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.InstructionOneArg;

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

        Instruction instruction0 = instructions.get(instNum);
        Instruction instruction1 = instructions.get(instNum + 1);

        Symbol symbol0 = instruction0.getOpCode().getSymbol();
        Symbol symbol1 = instruction1.getOpCode().getSymbol();

        // check that we have LDCINT followed by NEG
        if (symbol0 == Symbol.LDCINT && symbol1 == Symbol.NEG)
          {
            InstructionOneArg inst0 = (InstructionOneArg)instruction0;
            int constValue = inst0.argToInt();

            // make sure that the NEG instruction does not have any labels
            List<Token> inst1Labels = instruction1.getLabels();
            if (inst1Labels.isEmpty())
              {
                inst0.getArg().setText(Integer.toString(-constValue));

                // remove the NEG instruction
                instructions.remove(instNum + 1);
              }
          }
      }
  }
