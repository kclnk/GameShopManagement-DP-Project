package patterns.behavioral;

import model.Item;
import model.Player;

/**
 * EquipCommand encapsulates equipping an item from the backpack.
 * Implements the Command pattern for item equipment.
 */
public class EquipCommand implements Command {
    private final Player player;
    private final Item item;
    private boolean executed;
    private ShopObservable observable;
    
    /**
     * Creates a new EquipCommand.
     * @param player the player equipping the item
     * @param item the item to equip
     */
    public EquipCommand(Player player, Item item) {
        this.player = player;
        this.item = item;
        this.executed = false;
    }
    
    /**
     * Creates a new EquipCommand with observer support.
     * @param player the player equipping the item
     * @param item the item to equip
     * @param observable the observable to notify on changes
     */
    public EquipCommand(Player player, Item item, ShopObservable observable) {
        this.player = player;
        this.item = item;
        this.executed = false;
        this.observable = observable;
    }
    
    /**
     * Executes the equip: moves item from backpack to equipped.
     */
    @Override
    public void execute() {
        if (executed) {
            System.out.println("Warning: EquipCommand already executed");
            return;
        }
        
        // Equip the item
        player.getInventory().equipItem(item);
        
        executed = true;
        
        System.out.println("Ã¢Å“â€œ EquipCommand executed: Equipped " + item.getName());
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyItemEquipped(item, player);
        }
    }
    
    /**
     * Undoes the equip: moves item from equipped back to backpack.
     */
    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Warning: EquipCommand not executed, cannot undo");
            return;
        }
        
        // Unequip the item
        player.getInventory().unequipItem(item);
        
        executed = false;
        
        System.out.println("Ã¢â€ Â¶ EquipCommand undone: Unequipped " + item.getName());
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyItemUnequipped(item, player);
        }
    }
    
    /**
     * Returns a description of this command.
     * @return description string
     */
    @Override
    public String getDescription() {
        return "Equip " + item.getName();
    }
    
    /**
     * Returns the item being equipped.
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


