package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionDEC;
import edu.citadel.assembler.ast.InstructionINC;
import edu.citadel.assembler.ast.InstructionOneArg;

import java.util.List;

/**
 * Replaces addition of 1 with increment and subtraction of 1 with decrement.
 * Basically, this class looks for patterns of the form "LDCINT 1, ADD" and
 * replaces it with "INC", and similarly for SUB.
 */
public class IncDec implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least 2 instructions remaining
        if (instNum > instructions.size() - 2)
            return;

        var instruction0 = instructions.get(instNum);
        var instruction1 = instructions.get(instNum + 1);

        // quick check that we have LDCINT
        var symbol0 = instruction0.getOpcode().getSymbol();
        if (symbol0 != Symbol.LDCINT)
            return;

        var inst0 = (InstructionOneArg) instruction0;
        var arg0  = inst0.getArg().getText();

        if (arg0.equals("1"))
          {
            // Make sure that instruction1 does not have any labels
            if (instruction1.getLabels().isEmpty())
              {
                var symbol1 = instruction1.getOpcode().getSymbol();

                if (symbol1 == Symbol.ADD)
                  {
                    // replace LDCINT by INC
                    var incToken = new Token(Symbol.INC);
                    var labels   = inst0.getLabels();
                    var incInst  = new InstructionINC(labels, incToken);
                    instructions.set(instNum, incInst);
                  }
                else if (symbol1 == Symbol.SUB)
                  {
                    // replace LDCINT 1 by DEC
                    var decToken = new Token(Symbol.DEC);
                    var labels   = inst0.getLabels();
                    var decInst  = new InstructionDEC(labels, decToken);
                    instructions.set(instNum, decInst);
                  }
                else
                    return;

                // remove the ADD/SUB instruction
                instructions.remove(instNum + 1);
              }
          }
      }
  }
