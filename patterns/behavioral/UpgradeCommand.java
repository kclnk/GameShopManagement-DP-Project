package patterns.behavioral;

import model.Item;
import model.Player;
import patterns.structural.ItemDecorator;

/**
 * UpgradeCommand encapsulates upgrading an item with a decorator.
 * Implements the Command pattern for item upgrades.
 */
public class UpgradeCommand implements Command {
    private final Player player;
    private final Item baseItem;
    private final ItemDecorator decorator;
    private final double upgradeCost;
    private final Item upgradedItem;
    private boolean executed;
    private ShopObservable observable;
    
    /**
     * Creates a new UpgradeCommand.
     * @param player the player upgrading the item
     * @param baseItem the original item to upgrade
     * @param decorator the decorator to apply (upgrade)
     * @param upgradeCost the cost of the upgrade
     */
    public UpgradeCommand(Player player, Item baseItem, ItemDecorator decorator, double upgradeCost) {
        this.player = player;
        this.baseItem = baseItem;
        this.decorator = decorator;
        this.upgradeCost = upgradeCost;
        this.upgradedItem = decorator; // The decorator IS the upgraded item
        this.executed = false;
    }
    
    /**
     * Creates a new UpgradeCommand with observer support.
     * @param player the player upgrading the item
     * @param baseItem the original item to upgrade
     * @param decorator the decorator to apply (upgrade)
     * @param upgradeCost the cost of the upgrade
     * @param observable the observable to notify on changes
     */
    public UpgradeCommand(Player player, Item baseItem, ItemDecorator decorator, double upgradeCost, ShopObservable observable) {
        this.player = player;
        this.baseItem = baseItem;
        this.decorator = decorator;
        this.upgradeCost = upgradeCost;
        this.upgradedItem = decorator;
        this.executed = false;
        this.observable = observable;
    }
    
    /**
     * Executes the upgrade: deducts cost and replaces item with upgraded version.
     */
    @Override
    public void execute() {
        if (executed) {
            System.out.println("Warning: UpgradeCommand already executed");
            return;
        }
        
        // Remove cost from player
        player.removeGold(upgradeCost);
        
        // Replace item in inventory with upgraded version
        player.getInventory().removeItem(baseItem);
        player.getInventory().addItem(upgradedItem);
        
        executed = true;
        
        System.out.println("Ã¢Å“â€œ UpgradeCommand executed: Upgraded " + baseItem.getName() + 
                          " for " + upgradeCost + " gold");
        
        // Notify observers if observable is set
        if (observable != null) {
            observable.notifyGoldChanged(player);
            observable.notifyInventoryChanged(player);
        }
    }
    
    /**
     * Undoes the upgrade: refunds cost and replaces upgraded item with base item.
     */
    @Override
    public void undo() {
        if (!executed) {
            System.out.println("Warning: UpgradeCommand not executed, cannot undo");
            return;
        }
        
        // Refund cost to player
        player.addGold(upgradeCost);
        
        // Replace upgraded item back with base item
        player.getInventory().removeItem(upgradedItem);
        player.getInventory().addItem(baseItem);
        
        executed = false;
        
        System.out.println("Ã¢â€ Â¶ UpgradeCommand undone: Downgraded back to " + baseItem.getName());
        
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
        return "Upgrade " + baseItem.getName() + " for " + upgradeCost + " gold";
    }
    
    /**
     * Returns the base item being upgraded.
     * @return the base item
     */
    public Item getBaseItem() {
        return baseItem;
    }
    
    /**
     * Returns the upgraded item (decorator).
     * @return the upgraded item
     */
    public Item getUpgradedItem() {
        return upgradedItem;
    }
    
    /**
     * Returns whether this command has been executed.
     * @return true if executed, false otherwise
     */
    public boolean isExecuted() {
        return executed;
    }
}


