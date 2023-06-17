package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionOneArg;

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

        var instruction0 = instructions.get(instNum);
        var instruction1 = instructions.get(instNum + 1);
        var instruction2 = instructions.get(instNum + 2);

        var symbol0 = instruction0.getOpcode().getSymbol();
        var symbol1 = instruction1.getOpcode().getSymbol();
        var symbol2 = instruction2.getOpcode().getSymbol();

        // quick check that we are dealing with two LDCINT instructions
        if (symbol0 != Symbol.LDCINT || symbol1 != Symbol.LDCINT)
            return;

        // we are dealing with two constant integers
        var inst0  = (InstructionOneArg) instruction0;
        var inst1  = (InstructionOneArg) instruction1;

        var value0 = inst0.argToInt();
        var value1 = inst1.argToInt();

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
