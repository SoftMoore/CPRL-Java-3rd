package edu.citadel.assembler.optimize;

import edu.citadel.assembler.Symbol;
import edu.citadel.assembler.Token;
import edu.citadel.assembler.ast.Instruction;
import edu.citadel.assembler.ast.InstructionOneArg;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * If an instruction follows a return or an unconditional branch, and if the instruction
 * has no targeted labels  then that instruction is unreachable (dead) and can be removed.
 */
public class DeadCodeElimination implements Optimization
  {
    private Map<String, Integer> labelBranchCounts = new HashMap<>();
    
    @Override
    public void optimize(List<Instruction> instructions, int instNum)
      {
        // quick check that there are at least 2 instructions remaining
        if (instNum > instructions.size() - 2)
            return;
        
        // When instNum = 1, create a map that maps labels -> n, where n is the number
        // of branch or call instructions that branch to that label.  If a branch
        // instruction gets removed, decrement the corresponding n for its target label.
        // Any instruction that follows a return or an unconditional branch is unreachable
        // if it has no labels OR if the total count of branches to it is zero.
        if (instNum == 1)
          {
            for (Instruction inst : instructions)
              {
                if (isBranchOrCallInstruction(inst))
                  {
                    var labelTarget = getLabelTarget((InstructionOneArg) inst);
                    if (labelBranchCounts.containsKey(labelTarget))
                      {
                        // increment the branch count for this label
                        int count = labelBranchCounts.get(labelTarget);
                        labelBranchCounts.put(labelTarget, Integer.valueOf(count + 1));
                      }
                    else
                        labelBranchCounts.put(labelTarget, 1);
                  }
              }
          }        

        var instruction0 = instructions.get(instNum);
        var symbol0      = instruction0.getOpcode().getSymbol();

        // Check that symbol0 is either an unconditional branch or a return instruction.
        if (symbol0 == Symbol.BR || isReturnSymbol(symbol0))
          {
            var instruction1 = instructions.get(instNum + 1);
            var labels1 = instruction1.getLabels();

            // check that the second instruction does not have any labels
            if (labels1.isEmpty() || getTotalBranchCounts(labels1) == 0)
              {
                if (isBranchOrCallInstruction(instruction1))
                  {
                    // decrement the branch count for this label in this instruction
                    var labelTarget = getLabelTarget((InstructionOneArg) instruction1);
                    var count = labelBranchCounts.get(labelTarget);
                    if (count != null)
                        labelBranchCounts.put(labelTarget, Integer.valueOf(count - 1));
                  }

                // We are free to remove the second instruction.
                instructions.remove(instNum + 1);
              }
          }
      }
    
    private String getLabelTarget(InstructionOneArg instruction)
      {
        assert isBranchOrCallInstruction(instruction) : "invalid branch instruction";
        Token targetLabel = instruction.getArg();
        assert targetLabel.getSymbol() == Symbol.identifier
            : "invalid argument for branch instruction";
        return targetLabel.getText() + ":";
      }
    
    private boolean isBranchOrCallInstruction(Instruction instruction)
      {
        return isBranchOrCallSymbol(instruction.getOpcode().getSymbol());
      }
    
    private boolean isBranchOrCallSymbol(Symbol symbol)
      {
        return switch (symbol)
              {
                case BR, BE, BNE, BG, BGE, BL, BLE, BZ, BNZ, CALL -> true;
                default -> false;
              };
      }

    private boolean isReturnSymbol(Symbol symbol)
      {
        return symbol == Symbol.RET || symbol == Symbol.RET0 || symbol == Symbol.RET4;
      }
    
    private int getTotalBranchCounts(List<Token> labels)
      {
        int sum = 0;
        for (Token label : labels)
            sum = sum + labelBranchCounts.getOrDefault(label.getText(), 0);        
        return sum;
      }
  }
