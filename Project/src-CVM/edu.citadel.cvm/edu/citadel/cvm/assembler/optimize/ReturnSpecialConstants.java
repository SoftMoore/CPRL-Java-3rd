package edu.citadel.cvm.assembler.optimize;

import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.Token;

import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.InstructionOneArg;
import edu.citadel.cvm.assembler.ast.InstructionRET0;
import edu.citadel.cvm.assembler.ast.InstructionRET4;

import java.util.List;

/**
 * Replaces RET 0 with RET0 and RET 4 with RET4.
 * IMPORTANT: This optimization should not be performed until after
 * the optimization for dead code elimination.
 */
public class ReturnSpecialConstants implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        Instruction instruction = instructions.get(instNum);
        Symbol symbol = instruction.getOpCode().getSymbol();

        if (symbol == Symbol.RET)
          {
            InstructionOneArg inst = (InstructionOneArg)instruction;
            String arg = inst.getArg().getText();
            List<Token> labels = inst.getLabels();

            if (arg.equals("0"))
              {
                // replace RET 0 with RET0
                Token retToken = new Token(Symbol.RET0);
                Instruction retInst = new InstructionRET0(labels, retToken);
                instructions.set(instNum, retInst);
              }
            else if (arg.equals("4"))
              {
                // replace RET 4 with RET4
                Token retToken = new Token(Symbol.RET4);
                Instruction retInst = new InstructionRET4(labels, retToken);
                instructions.set(instNum, retInst);
              }
          }
      }
  }
