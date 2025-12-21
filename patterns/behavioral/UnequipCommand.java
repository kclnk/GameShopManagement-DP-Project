package patterns.behavioral;

import model.Item;
import model.Player;

/**
 * UnequipCommand encapsulates unequipping an item back to the backpack.
 * Implements the Command pattern for item unequipment.
 */
public class UnequipCommand implements Command {
    private final Player player;
    private final Item item;
    private boolean executed;
    private ShopObservable observable;
    
    /**
     * Creates a new UnequipCommand.
     * @param player the player unequipping the item
     * @param item the item to unequip
     */
    public UnequipCommand(Player player, Item item) {
        this.player = player;
        this.item = item;
        this.executed = false;
    }
    
    /**
     * Creates a new UnequipCommand with observer support.
     * @param player the player unequipping the item
     * @param item the item to unequip
     * @param observable the observable to notify on changes
     */
    public UnequipCommand(Player player, Item item, ShopObservable observable) {
        this.player = player;
        this.item = item;
        this.executed = false;
        this.observable = observable;
    }
    
    /**
     * Executes the unequip: moves item from equipped to backpack.
     */
    @Override
    public void execute() {
        if (executed) {
            System.out.println("Warning: UnequipCommand already executed");
            return;
        }
        
        // Unequip the item
        player.getInventory().unequipItem(item);
        
        executed = true;
        
        System.out.println("Ã¢Å“â€œ UnequipCommand executed: Unequipped " + item.getName());
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyItemUnequipped(item, player);
        }
    }
    
    /**
     * Undoes the unequip: moves item from backpack back to equipped.
     */
    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Warning: UnequipCommand not executed, cannot undo");
            return;
        }
        
        // Re-equip the item
        player.getInventory().equipItem(item);
        
        executed = false;
        
        System.out.println("Ã¢â€ Â¶ UnequipCommand undone: Re-equipped " + item.getName());
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyItemEquipped(item, player);
        }
    }
    
    /**
     * Returns a description of this command.
     * @return description string
     */
    @Override
    public String getDescription() {
        return "Unequip " + item.getName();
    }
    
    /**
     * Returns the item being unequipped.
     * @return the item
     */
    public Item getItem() {
        return item;
    }
    
    /**
     * Returns whether this command has been executed.
     * @return true if executed, false otherwise
     */
    public boolean isExecuted() {
        return executed;
    }
}


