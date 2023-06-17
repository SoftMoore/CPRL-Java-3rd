package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;

import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionDEC;
import edu.citadel.assembler.ast.InstructionINC;
import edu.citadel.assembler.ast.InstructionOneArg;

import java.util.List;

/**
 * Replaces addition of 1 to a variable with increment and subtraction
 * of 1 from a variable with decrement.  Basically, this class looks for
 * patterns of the form "LDCINT 1, LDLADDR x, LOADW, ADD" and replaces
 * it with "LDLADDR x, LOADW, INC", and similarly for SUB.
 */
public class IncDec2 implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least four instructions remaining
        if (instNum > instructions.size() - 4)
            return;

        var instruction0 = instructions.get(instNum);
        var instruction1 = instructions.get(instNum + 1);
        var instruction2 = instructions.get(instNum + 2);
        var instruction3 = instructions.get(instNum + 3);

        Symbol symbol0 = instruction0.getOpcode().getSymbol();
        Symbol symbol1 = instruction1.getOpcode().getSymbol();
        Symbol symbol2 = instruction2.getOpcode().getSymbol();

        // quick check that we have LDCINT, LDLADDR, and LOADW
        if (symbol0 != Symbol.LDCINT || symbol1 != Symbol.LDLADDR || symbol2 != Symbol.LOADW)
            return;

        var inst0 = (InstructionOneArg) instruction0;
        var arg0  = inst0.getArg().getText();

        if (arg0.equals("1"))
          {
            Symbol symbol3 = instruction3.getOpcode().getSymbol();

            if (symbol3 == Symbol.ADD)
              {
                // replace ADD with INC
                var incToken = new Token(Symbol.INC);
                var labels   = instruction3.getLabels();
                var incInst  = new InstructionINC(labels, incToken);
                instructions.set(instNum + 3, incInst);
              }
            else if (symbol3 == Symbol.SUB)
              {
                // replace SUB with DEC
                var decToken = new Token(Symbol.DEC);
                var labels   = instruction3.getLabels();
                var decInst  = new InstructionDEC(labels, decToken);
                instructions.set(instNum + 3, decInst);
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
