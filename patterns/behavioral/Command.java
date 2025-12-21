package patterns.behavioral;

/**
 * Command interface for encapsulating actions with undo support.
 */
public interface Command {
    /**
     * Executes the command, performing the encapsulated action.
     */
    void execute();
    
    /**
     * Undoes the command, reversing the action performed by execute().
     */
    void undo();
    
    /**
     * Returns a description of what this command does.
     * @return descriptive string of the command
     */
    String getDescription();
}


