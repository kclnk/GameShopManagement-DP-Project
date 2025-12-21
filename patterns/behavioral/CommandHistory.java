package patterns.behavioral;

import java.util.Stack;

/**
 * Manages command history for undo/redo functionality.
 */
public class CommandHistory {
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;
    
    /**
     * Creates a new CommandHistory with empty stacks.
     */
    public CommandHistory() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }
    
    /**
     * Adds a command to the history.
     * Clears the redo stack (can't redo after new command).
     * @param command the command to add
     */
    public void addCommand(Command command) {
        undoStack.push(command);
        redoStack.clear(); // Can't redo after new command
    }
    
    /**
     * Executes a command and adds it to the history.
     * @param command the command to execute and track
     */
    public void executeCommand(Command command) {
        command.execute();
        addCommand(command);
    }
    
    /**
     * Undoes the most recent command.
     * Moves it from undo stack to redo stack.
     */
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }
        
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }
    
    /**
     * Redoes the most recently undone command.
     * Moves it from redo stack back to undo stack.
     */
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo");
            return;
        }
        
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }
    
    /**
     * Checks if undo is possible.
     * @return true if there are commands to undo
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    /**
     * Checks if redo is possible.
     * @return true if there are commands to redo
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    /**
     * Checks if the history is empty.
     * @return true if no commands in undo stack
     */
    public boolean isEmpty() {
        return undoStack.isEmpty();
    }
    
    /**
     * Returns the number of commands in undo history.
     * @return size of undo stack
     */
    public int getUndoStackSize() {
        return undoStack.size();
    }
    
    /**
     * Returns the number of commands in redo history.
     * @return size of redo stack
     */
    public int getRedoStackSize() {
        return redoStack.size();
    }
    
    /**
     * Prints the command history.
     */
    public void printHistory() {
        System.out.println("\n===== COMMAND HISTORY =====");
        
        if (undoStack.isEmpty()) {
            System.out.println("  (No commands executed)");
        } else {
            int index = 1;
            for (Command command : undoStack) {
                System.out.println(index + ". " + command.getDescription());
                index++;
            }
        }
        
        System.out.println("\nCan Undo: " + canUndo());
        System.out.println("Can Redo: " + canRedo());
        System.out.println("===========================\n");
    }
    
    /**
     * Clears all command history.
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
