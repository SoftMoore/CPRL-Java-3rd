package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionOneArg;
import edu.citadel.assembler.ast.InstructionSHL;
import edu.citadel.assembler.ast.InstructionSHR;

import java.util.List;

/**
 * Replaces multiplication by a power of 2 with left shift and division by a power
 * of two with right shift where possible.  Basically, this class looks for patterns
 * of the form "LDCINT 2**n, MUL" and replaces it with "SHL n".  It also replaces
 * patterns of the form "LDCINT 2**n, DIV" with "SHR n".
 */
public class ShiftLeftRight implements Optimization
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

        // quick check that we have LDCINT
        if (symbol0 != Symbol.LDCINT)
            return;

        InstructionOneArg inst0 = (InstructionOneArg) instruction0;
        int shiftAmount = OptimizationUtil.getShiftAmount(inst0.argToInt());
        if (shiftAmount > 0)
          {
            // make sure that inst1 does not have any labels
            List<Token> inst1Labels = instruction1.getLabels();
            if (inst1Labels.isEmpty())
              {
                var labels   = inst0.getLabels();
                var argStr   = Integer.toString(shiftAmount);
                var argToken = new Token(Symbol.intLiteral, argStr);
                var symbol1  = instruction1.getOpcode().getSymbol();

                if (symbol1 == Symbol.MUL)
                  {
                    // replace LDCINT with SHL
                    var shlToken = new Token(Symbol.SHL);
                    var shlInst  = new InstructionSHL(labels, shlToken, argToken);
                    instructions.set(instNum, shlInst);
                  }
                else if (symbol1 == Symbol.DIV)
                  {
                    // replace LDCINT by SHR
                    var shrToken = new Token(Symbol.SHR);
                    var shrInst  = new InstructionSHR(labels, shrToken, argToken);
                    instructions.set(instNum, shrInst);
                  }
                else
                    return;

                // remove the MUL/DIV instruction
                instructions.remove(instNum + 1);
              }
          }
      }
  }
