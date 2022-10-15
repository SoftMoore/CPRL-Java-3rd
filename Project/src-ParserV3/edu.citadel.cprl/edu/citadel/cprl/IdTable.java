package edu.citadel.cprl;

import edu.citadel.compiler.ParserException;
import edu.citadel.cprl.ast.Declaration;

import java.util.ArrayList;

/**
 * The identifier table (also known as a symbol table) is used to
 * hold attributes of identifiers in the programming language CPRL.
 */
public final class IdTable
  {
    // IdTable is implemented as a stack of scopes.  Opening a scope
    // pushes a new scope onto the stack.  Searching for an identifier
    // involves searching at the current level (top scope in the stack)
    // and then at enclosing scopes (scopes under the top).

    private static final int INITIAL_SCOPE_LEVELS = 3;

    private ArrayList<Scope> table;
    private int currentLevel;

    /**
     * Construct an empty identifier table with scope level initialized to 0.
     */
    public IdTable()
      {
        table = new ArrayList<Scope>(INITIAL_SCOPE_LEVELS);
        currentLevel = 0;
        table.add(currentLevel, new Scope(ScopeLevel.GLOBAL));
      }

    /**
     * Returns the current scope level.
     */
    public ScopeLevel getScopeLevel()
      {
        return table.get(currentLevel).getScopeLevel();
      }

    /**
     * Opens a new scope for identifiers.
     */
    public void openScope(ScopeLevel scopeLevel)
      {
        ++currentLevel;
        table.add(currentLevel, new Scope(scopeLevel));
      }

    /**
     * Closes the outermost scope.
     */
    public void closeScope()
      {
        table.remove(currentLevel);
        --currentLevel;
      }

    /**
     * Add a declaration to the current scope.
     *
     * @throws ParserException if the name in the declaration already
     *                         exists in the current scope.
     */
    public void add(Declaration decl) throws ParserException
      {
        Token idToken = decl.getIdToken();

        // assumes that idToken is an identifier token
        assert idToken.getSymbol() == Symbol.identifier :
            "IdTable.add(): The token in the declaration is not an identifier.";

        Scope scope = table.get(currentLevel);
        Declaration oldDecl = scope.put(idToken.getText(), decl);

        // check that the identifier has not been declared previously
        if (oldDecl != null)
          {
            String errorMsg = "Identifier \"" + idToken.getText()
                            + "\" is already defined in the current scope.";
            throw new ParserException(idToken.getPosition(), errorMsg);
          }
      }

    /**
     * Returns the declaration associated with the identifier name
     * (type String).  Returns null if the identifier is not found.
     * Searches enclosing scopes if necessary.
     */
    public Declaration get(String idStr)
      {
        Declaration decl = null;
        int level = currentLevel;

        while (level >= 0 && decl == null)
          {
            Scope scope = table.get(level);
            decl = scope.get(idStr);
            --level;
          }

        return decl;
      }
  }
