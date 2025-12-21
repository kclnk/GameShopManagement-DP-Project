package patterns.behavioral;

import model.Item;
import model.Player;

/**
 * SellCommand encapsulates selling an item back to the shop.
 * Implements the Command pattern for item sales.
 * Player receives 80% of the item's original price.
 */
public class SellCommand implements Command {
    private final Player player;
    private final Item item;
    private final double goldReceived;
    private boolean executed;
    private ShopObservable observable;
    
    /**
     * Creates a new SellCommand.
     * @param player the player selling the item
     * @param item the item to sell
     */
    public SellCommand(Player player, Item item) {
        this.player = player;
        this.item = item;
        this.goldReceived = item.getPrice() * 0.8; // Sell for 80% of purchase price
        this.executed = false;
    }
    
    /**
     * Creates a new SellCommand with observer support.
     * @param player the player selling the item
     * @param item the item to sell
     * @param observable the observable to notify on changes
     */
    public SellCommand(Player player, Item item, ShopObservable observable) {
        this.player = player;
        this.item = item;
        this.goldReceived = item.getPrice() * 0.8;
        this.executed = false;
        this.observable = observable;
    }
    
    /**
     * Executes the sale: removes item from inventory and adds gold.
     */
    @Override
    public void execute() {
        if (executed) {
            System.out.println("Warning: SellCommand already executed");
            return;
        }
        
        // Remove item from inventory
        player.removeItemFromInventory(item);
        
        // Add gold to player
        player.addGold(goldReceived);
        
        executed = true;
        
        System.out.println("Ã¢Å“â€œ SellCommand executed: Sold " + item.getName() + 
                          " for " + goldReceived + " gold");
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyGoldChanged(player);
            observable.notifyInventoryChanged(player);
        }
    }
    
    /**
     * Undoes the sale: removes gold and returns item to inventory.
     */
    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Warning: SellCommand not executed, cannot undo");
            return;
        }
        
        // Add item back to inventory
        player.addItem(item);
        
        // Remove gold from player
        player.removeGold(goldReceived);
        
        executed = false;
        
        System.out.println("Ã¢â€ Â¶ SellCommand undone: Bought back " + item.getName() + 
                          " for " + goldReceived + " gold");
        
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
        return "Sell " + item.getName() + " for " + goldReceived + " gold";
    }
    
    /**
     * Returns the item being sold.
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


