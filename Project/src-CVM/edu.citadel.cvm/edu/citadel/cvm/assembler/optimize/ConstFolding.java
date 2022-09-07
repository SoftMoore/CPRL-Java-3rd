package edu.citadel.cvm.assembler.optimize;


import edu.citadel.cvm.assembler.Symbol;
import edu.citadel.cvm.assembler.ast.Instruction;
import edu.citadel.cvm.assembler.ast.InstructionOneArg;

import java.util.List;


/**
 * Replaces runtime arithmetic on constants with compile-time arithmetic.
 * For example, the following instructions
 * <code>
     LDCINT 5
     LDCINT 7
     ADD
   </code>
 * can be replaced with
 * <code>
     LDCINT 12
 * </code>
 */
public class ConstFolding implements Optimization
  {
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least three instructions remaining
        if (instNum > instructions.size() - 3)
            return;

        Instruction instruction0 = instructions.get(instNum);
        Instruction instruction1 = instructions.get(instNum + 1);
        Instruction instruction2 = instructions.get(instNum + 2);

        Symbol symbol0 = instruction0.getOpCode().getSymbol();
        Symbol symbol1 = instruction1.getOpCode().getSymbol();
        Symbol symbol2 = instruction2.getOpCode().getSymbol();

        // quick check that we are dealing with two LDCINT instructions
        if (symbol0 != Symbol.LDCINT || symbol1 != Symbol.LDCINT)
            return;

        // we are dealing with two constant integers
        InstructionOneArg inst0 = (InstructionOneArg)instruction0;
        InstructionOneArg inst1 = (InstructionOneArg)instruction1;

        int value0 = inst0.argToInt();
        int value1 = inst1.argToInt();

        // make sure that inst1 and inst2 do not have any labels
        if (inst1.getLabels().isEmpty() && instruction2.getLabels().isEmpty())
          {
            switch (symbol2)
              {
                case ADD ->
                  {
                    int sum = value0 + value1;
                    inst0.getArg().setText(Integer.toString(sum));
                  }
                case SUB ->
                  {
                    int difference = value0 - value1;
                    inst0.getArg().setText(Integer.toString(difference));
                  }
                case MUL ->
                  {
                    int product = value0*value1;
                    inst0.getArg().setText(Integer.toString(product));
                  }
                case DIV ->
                  {
                    int quotient = value0/value1;
                    inst0.getArg().setText(Integer.toString(quotient));
                  }
                case MOD ->
                  {
                    int remainder = value0%value1;
                    inst0.getArg().setText(Integer.toString(remainder));
                  }
                default ->
                  {
                    return;
                  }
              }

            // modify the list of instructions to reflect the optimization
            instructions.remove(instNum + 1);
            instructions.remove(instNum + 1);
          }
      }
  }
