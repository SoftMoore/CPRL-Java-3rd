package edu.citadel.cprl;

/**
 * An enum class for the three scope levels in CPRL.
 */
public enum ScopeLevel
  {
    GLOBAL("global"),
    LOCAL("local"),
    RECORD("record");

    private String text;

    /**
     * Construct a new scope level with its text.
     */
    private ScopeLevel(String text)
      {
        this.text = text;
      }

    /**
     * Returns a "nice" string for the name of the scope type.  For
     * example, this method returns "local" instead of "LOCAL".
     */
    @Override
    public String toString()
      {
        return text;
      }
  }
