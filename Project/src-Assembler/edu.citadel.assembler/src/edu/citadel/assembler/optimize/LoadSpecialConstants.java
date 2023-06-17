package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;
import edu.citadel.assembler.ast.*;

import java.util.List;

/**
 * Replaces LDCB 0 with LDCB0, LDCB 1 with LDCB1, LDCINT 0 with LDCINT0, and
 * LDCINT 1 with LDCINT1.  Implementation Note: This optimization must be
 * applied after the other optimizations or else they will need to be rewritten.
 * For example, the inc/dec instructions look for LDCINT 1 and not LDCINT1.
 */
public class LoadSpecialConstants implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        var instruction = instructions.get(instNum);
        var symbol = instruction.getOpcode().getSymbol();

        if (symbol == Symbol.LDCINT)
          {
            var inst   = (InstructionOneArg) instruction;
            var arg    = inst.getArg().getText();
            var labels = inst.getLabels();

            if (arg.equals("0"))
              {
                // replace LDCINT 0 with LDCINT0
                var loadToken = new Token(Symbol.LDCINT0);
                var loadInst  = new InstructionLDCINT0(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
            else if (arg.equals("1"))
              {
                // replace LDCINT 1 with LDCINT1
                var loadToken = new Token(Symbol.LDCINT1);
                var loadInst  = new InstructionLDCINT1(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
          }
        else if (symbol == Symbol.LDCB)
          {
            var inst   = (InstructionOneArg) instruction;
            var arg    = inst.getArg().getText();
            var labels = inst.getLabels();

            if (arg.equals("0"))
              {
                // replace LDCB 0 with LDCB0
                var loadToken = new Token(Symbol.LDCB0);
                var loadInst  = new InstructionLDCB0(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
            else if (arg.equals("1"))
              {
                // replace LDCB 1 with LDCB1
                var loadToken = new Token(Symbol.LDCB1);
                var loadInst  = new InstructionLDCB1(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
          }
      }
  }
