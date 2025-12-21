package patterns.behavioral;

import model.Item;
import model.Player;

/**
 * Command for purchasing items.
 */
public class BuyCommand implements Command {
    private final Player player;
    private final Item item;
    private final double goldSpent;
    private boolean executed;
    private ShopObservable observable;
    
    public BuyCommand(Player player, Item item) {
        this.player = player;
        this.item = item;
        this.goldSpent = item.getPrice();
        this.executed = false;
    }
    
    /**
     * Creates a new BuyCommand with observer support.
     * @param player the player purchasing the item
     * @param item the item to purchase
     * @param observable the observable to notify on changes
     */
    public BuyCommand(Player player, Item item, ShopObservable observable) {
        this.player = player;
        this.item = item;
        this.goldSpent = item.getPrice();
        this.executed = false;
        this.observable = observable;
    }
    
    @Override
    public void execute() {
        if (executed) {
            System.out.println("Warning: BuyCommand already executed");
            return;
        }
        
        // Remove gold from player
        player.removeGold(goldSpent);
        
        // Add item to inventory
        player.addItem(item);
        
        // Mark as executed
        executed = true;
        
        System.out.println("Ã¢Å“â€œ BuyCommand executed: Purchased " + item.getName() + 
                          " for " + goldSpent + " gold");
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyGoldChanged(player);
            observable.notifyInventoryChanged(player);
        }
    }
    
    /**
     * Undoes the purchase: refunds gold and removes item from inventory.
     */
    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Warning: BuyCommand not executed, cannot undo");
            return; // Can't undo if not executed
        }
        
        // Return gold to player
        player.addGold(goldSpent);
        
        // Remove item from inventory
        player.getInventory().removeItem(item);
        
        // Mark as unexecuted
        executed = false;
        
        System.out.println("Ã¢â€ Â¶ BuyCommand undone: Refunded " + item.getName() + 
                          " for " + goldSpent + " gold");
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyGoldChanged(player);
            observable.notifyInventoryChanged(player);
        }
    }
    
    /**
     * Returns a description of this command.
     * @return description string
     */
    @Override
    public String getDescription() {
        return "Buy " + item.getName() + " for " + goldSpent + " gold";
    }
    
    /**
     * Returns the item being purchased.
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


