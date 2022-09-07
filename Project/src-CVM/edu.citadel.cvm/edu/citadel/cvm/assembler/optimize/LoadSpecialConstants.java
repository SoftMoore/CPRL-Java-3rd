package edu.citadel.cvm.assembler.optimize;

import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;
import edu.citadel.cvm.assembler.ast.*;

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
        Instruction instruction = instructions.get(instNum);
        Symbol symbol = instruction.getOpCode().getSymbol();

        if (symbol == Symbol.LDCINT)
          {
            InstructionOneArg inst = (InstructionOneArg)instruction;
            String arg = inst.getArg().getText();
            List<Token> labels = inst.getLabels();

            if (arg.equals("0"))
              {
                // replace LDCINT 0 with LDCINT0
                Token loadToken = new Token(Symbol.LDCINT0);
                Instruction loadInst = new InstructionLDCINT0(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
            else if (arg.equals("1"))
              {
                // replace LDCINT 1 with LDCINT1
                Token loadToken = new Token(Symbol.LDCINT1);
                Instruction loadInst = new InstructionLDCINT1(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
          }
        else if (symbol == Symbol.LDCB)
          {
            InstructionOneArg inst = (InstructionOneArg)instruction;
            String arg = inst.getArg().getText();
            List<Token> labels = inst.getLabels();

            if (arg.equals("0"))
              {
                // replace LDCB 0 with LDCB0
                Token loadToken = new Token(Symbol.LDCB0);
                Instruction loadInst = new InstructionLDCB0(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
            else if (arg.equals("1"))
              {
                // replace LDCB 1 with LDCB1
                Token loadToken = new Token(Symbol.LDCB1);
                Instruction loadInst = new InstructionLDCB1(labels, loadToken);
                instructions.set(instNum, loadInst);
              }
          } 
      }
  }
