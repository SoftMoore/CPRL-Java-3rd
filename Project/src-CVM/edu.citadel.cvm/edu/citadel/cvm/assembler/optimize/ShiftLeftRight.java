package edu.citadel.cvm.assembler.optimize;


import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.InstructionOneArg;
import edu.citadel.cvm.assembler.ast.InstructionSHL;
import edu.citadel.cvm.assembler.ast.InstructionSHR;

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

        Instruction instruction0 = instructions.get(instNum);
        Instruction instruction1 = instructions.get(instNum + 1);
        
        Symbol symbol0 = instruction0.getOpCode().getSymbol();

        // quick check that we have LDCINT
        if (symbol0 != Symbol.LDCINT)
            return;

        InstructionOneArg inst0 = (InstructionOneArg)instruction0;        
        int shiftAmount = OptimizationUtil.getShiftAmount(inst0.argToInt());
        if (shiftAmount > 0)
          {
            // make sure that inst1 does not have any labels
            List<Token> inst1Labels = instruction1.getLabels();
            if (inst1Labels.isEmpty())
              {
                List<Token> labels = inst0.getLabels();
                String argStr = Integer.toString(shiftAmount);
                Token argToken = new Token(Symbol.intLiteral, argStr);

                Symbol symbol1 = instruction1.getOpCode().getSymbol();
                
                if (symbol1 == Symbol.MUL)
                  {
                    // replace LDCINT with SHL 
                    Token shlToken = new Token(Symbol.SHL);
                    Instruction shlInst = new InstructionSHL(labels, shlToken, argToken);
                    instructions.set(instNum, shlInst);
                  }
                else if (symbol1 == Symbol.DIV)
                  {
                    // replace LDCINT by SHR
                    Token shrToken = new Token(Symbol.SHR);
                    Instruction shrInst = new InstructionSHR(labels, shrToken, argToken);
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
