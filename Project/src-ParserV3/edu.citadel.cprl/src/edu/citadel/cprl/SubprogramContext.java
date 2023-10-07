package edu.citadel.cprl;

import edu.citadel.cprl.ast.SubprogramDecl;

/**
 * This class provides access to an enclosing subprogram context required
 * by return statements for constraint analysis and code generation.
 */
public final class SubprogramContext
  {
    // The subprogram declaration currently being parsed;
    // null if not currently parsing a subprogram.
    private SubprogramDecl subprogDecl;

    public SubprogramContext()
      {
        subprogDecl = null;
      }

    /**
     * Returns the subprogram declaration currently being parsed.
     * Returns null if not currently parsing a subprogram.
     */
    public SubprogramDecl getSubprogramDecl()
      {
        return subprogDecl;
      }

    /**
     * Called when starting to parse a subprogram declaration.
     */
    public void beginSubprogramDecl(SubprogramDecl subprogDecl)
      {
        this.subprogDecl = subprogDecl;
      }

    /**
     * Called when finished parsing a subprogram declaration.
     */
    public void endSubprogramDecl()
      {
        this.subprogDecl = null;
      }
  }
