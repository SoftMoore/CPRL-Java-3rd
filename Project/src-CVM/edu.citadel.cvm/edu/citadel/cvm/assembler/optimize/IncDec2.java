package edu.citadel.cvm.assembler.optimize;

import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.InstructionDEC;
import edu.citadel.cvm.assembler.ast.InstructionINC;
import edu.citadel.cvm.assembler.ast.InstructionOneArg;

import java.util.List;


/**
 * Replaces addition of 1 to a variable with increment and subtraction
 * of 1 from a variable with decrement.  Basically, this class looks for
 * patterns of the form "LDCINT 1, LDLADDR x, LOADW, ADD" and replaces it
 * with "LDLADDR x, LOADW, INC", and similarly for SUB.
 */
public class IncDec2 implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least four instructions remaining
        if (instNum > instructions.size() - 4)
            return;

        Instruction instruction0 = instructions.get(instNum);
        Instruction instruction1 = instructions.get(instNum + 1);
        Instruction instruction2 = instructions.get(instNum + 2);
        Instruction instruction3 = instructions.get(instNum + 3);
        
        Symbol symbol0 = instruction0.getOpCode().getSymbol();
        Symbol symbol1 = instruction1.getOpCode().getSymbol();
        Symbol symbol2 = instruction2.getOpCode().getSymbol();

        // quick check that we have LDCINT, LDLADDR, and LOADW
        if (symbol0 != Symbol.LDCINT || symbol1 != Symbol.LDLADDR || symbol2 != Symbol.LOADW)
            return;
        
        InstructionOneArg inst0 = (InstructionOneArg)instruction0;

        String arg0 = inst0.getArg().getText();

        if (arg0.equals("1"))
          {
            Symbol symbol3 = instruction3.getOpCode().getSymbol();

            if (symbol3 == Symbol.ADD)
              {
                // replace ADD with INC
                Token incToken = new Token(Symbol.INC);
                List<Token> labels = instruction3.getLabels();
                Instruction incInst = new InstructionINC(labels, incToken);
                instructions.set(instNum + 3, incInst);
              }
            else if (symbol3 == Symbol.SUB)
              {
                // replace SUB with DEC
                Token decToken = new Token(Symbol.DEC);
                List<Token> labels = instruction3.getLabels();
                Instruction decInst = new InstructionDEC(labels, decToken);
                instructions.set(instNum + 3, decInst);
              }
            else
                return;

            // copy labels from inst0 to inst1 before removing it
            List<Token> inst1Labels = instruction1.getLabels();
            inst1Labels.addAll(inst0.getLabels());

            // remove the LDCINT instruction
            instructions.remove(instNum);
          }
      }
  }
