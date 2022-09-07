package edu.citadel.cvm.assembler.optimize;


import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.InstructionOneArg;
import edu.citadel.cvm.assembler.ast.InstructionSHL;

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
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least four instructions remaining
        if (instNum > instructions.size() - 4)
            return;

        Instruction instruction0 = instructions.get(instNum);
        Instruction instruction1 = instructions.get(instNum + 1);
        Instruction instruction2 = instructions.get(instNum + 2);
        Instruction instruction3 = instructions.get(instNum + 3);
        
        // quick check that we are dealing with a constant and a variable
        Symbol symbol0 = instruction0.getOpCode().getSymbol();
        Symbol symbol1 = instruction1.getOpCode().getSymbol();
        Symbol symbol2 = instruction2.getOpCode().getSymbol();

        // quick check that we have LDCINT, LDLADDR, and LOADW
        if (symbol0 != Symbol.LDCINT || symbol1 != Symbol.LDLADDR || symbol2 != Symbol.LOADW)
              return;

        InstructionOneArg inst0 = (InstructionOneArg)instruction0;
        String arg0 = inst0.getArg().getText();
        int shiftAmount = OptimizationUtil.getShiftAmount(Integer.parseInt(arg0));

        if (shiftAmount > 0)
          {
            Symbol symbol3 = instruction3.getOpCode().getSymbol();

            if (symbol3 == Symbol.MUL)
              {
                // replace MUL by SHL
                Token shlToken = new Token(Symbol.SHL);
                List<Token> labels = instruction3.getLabels();
                String argStr = Integer.toString(shiftAmount);
                Token argToken = new Token(Symbol.intLiteral, argStr);
                Instruction shlInst = new InstructionSHL(labels, shlToken, argToken);
                instructions.set(instNum + 3, shlInst);
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
