package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionOneArg;
import edu.citadel.assembler.ast.InstructionSHL;

import java.util.List;

/**
 * Replaces multiplication by a power of 2 times a variable with left
 * shift.  Basically, this class looks for patterns of the form
 * "LDCINT 2**n, LDLADDR x, LOADW, MUL" and replaces it with
 * "LDLADDR x, LOADW, SHL n".  Note that the analogous replacement
 * for division will not work since division is not commutative.
 */
public class ShiftLeft implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum) {
        // quick check that there are at least four instructions remaining
        if (instNum > instructions.size() - 4)
            return;

        var instruction0 = instructions.get(instNum);
        var instruction1 = instructions.get(instNum + 1);
        var instruction2 = instructions.get(instNum + 2);
        var instruction3 = instructions.get(instNum + 3);

        // quick check that we are dealing with a constant and a variable
        var symbol0 = instruction0.getOpcode().getSymbol();
        var symbol1 = instruction1.getOpcode().getSymbol();
        var symbol2 = instruction2.getOpcode().getSymbol();

        // quick check that we have LDCINT, LDLADDR, and LOADW
        if (symbol0 != Symbol.LDCINT || symbol1 != Symbol.LDLADDR || symbol2 != Symbol.LOADW)
            return;

        var inst0 = (InstructionOneArg) instruction0;
        var arg0  = inst0.getArg().getText();
        var shiftAmount = OptimizationUtil.getShiftAmount(Integer.parseInt(arg0));

        if (shiftAmount > 0)
          {
            var symbol3 = instruction3.getOpcode().getSymbol();

            if (symbol3 == Symbol.MUL)
              {
                // replace MUL by SHL
                var shlToken = new Token(Symbol.SHL);
                var labels   = instruction3.getLabels();
                var argStr   = Integer.toString(shiftAmount);
                var argToken = new Token(Symbol.intLiteral, argStr);
                var shlInst  = new InstructionSHL(labels, shlToken, argToken);
                instructions.set(instNum + 3, shlInst);
              }
            else
                return;

            // copy labels from inst0 to inst1 before removing it
            var inst1Labels = instruction1.getLabels();
            inst1Labels.addAll(inst0.getLabels());

            // remove the LDCINT instruction
            instructions.remove(instNum);
          }
      }
  }
