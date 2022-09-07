package edu.citadel.cprl;


import edu.citadel.cprl.ast.Declaration;
import java.util.HashMap;


/**
 * This class encapsulates a scope in CPRL.
 */
public class Scope extends HashMap<String, Declaration>
  {
    private static final long serialVersionUID = -7956988200931288212L;

    private ScopeLevel scopeLevel;
    
    
    public Scope(ScopeLevel scopeLevel)
      {
        this.scopeLevel = scopeLevel;
      }


    public ScopeLevel getScopeLevel()
      {
          return scopeLevel;
      }
  }
