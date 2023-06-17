package edu.citadel.cprl;

import edu.citadel.cprl.ast.LoopStmt;
import java.util.Stack;

/**
 * This class provides access to an enclosing loop context required
 * by exit statements for constraint analysis code generation.
 */
public final class LoopContext
  {
    private Stack<LoopStmt> loopStack= new Stack<LoopStmt>();

    /**
     * Returns the loop statement currently being parsed.
     * Returns null if not currently parsing a loop.
     */
    public LoopStmt getLoopStmt()
      {
        return loopStack.empty() ? null : loopStack.peek();
      }

    /**
     * Called when starting to parse a loop statement.
     */
    public void beginLoop(LoopStmt stmt)
      {
        loopStack.push(stmt);
      }

    /**
     * Called when finished parsing a loop statement.
     */
    public void endLoop()
      {
        if (!loopStack.empty())
            loopStack.pop();
      }
  }
