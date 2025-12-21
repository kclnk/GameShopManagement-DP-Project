package patterns.structural;

import java.util.List;
import model.Item;
import model.Player;
import patterns.creational.ShopManager;

/**
 * Facade providing simplified interface for shopping operations.
 */
public class ShoppingFacade {
    private final ShopManager shopManager;
    private final Player player;
    
    public ShoppingFacade(Player player) {
        this.player = player;
        this.shopManager = ShopManager.getInstance();
    }
    
    public boolean buyItem(Item item) {
        try {
            // Step 1: Validate item exists in shop
            if (!shopManager.isItemAvailable(item)) {
                System.out.println("Error: Item not available in shop.");
                return false;
            }
            
            // Step 2: Check if player has enough gold
            if (player.getGold() < item.getPrice()) {
                System.out.println("Error: Insufficient gold! Need " + item.getPrice() + ", have " + player.getGold());
                return false;
            }
            
            // Step 3: Check inventory space
            if (!player.getInventory().hasSpace()) {
                System.out.println("Error: Inventory is full!");
                return false;
            }
            
            // Step 4-5: Execute purchase (Command pattern will be integrated in Section 4.1)
            // For now, perform operations directly
            player.removeGold(item.getPrice());
            player.addItem(item);
            
            // Step 6: Remove from shop
            shopManager.removeItemFromShop(item);
            
            System.out.println("Successfully purchased " + item.getName() + " for " + item.getPrice() + " gold!");
            return true;
            
        } catch (Exception e) {
            System.out.println("Error during purchase: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Attempts to sell an item back to the shop.
     * Player receives 50% of the item's original price.
     * 
     * @param item the item to sell
     * @return true if sale successful, false otherwise
     */
    public boolean sellItem(Item item) {
        try {
            // Step 1: Check item exists in inventory
            if (!player.getInventory().getBackpackItems().contains(item)) {
                System.out.println("Error: Item not in inventory.");
                return false;
            }
            
            // Step 2-3: Execute sale (Command pattern integration in Section 4.1)
            // Sell for 50% of original price
            double sellPrice = item.getPrice() * 0.5;
            player.removeItemFromInventory(item);
            player.addGold(sellPrice);
            
            // Step 4: Add back to shop
            shopManager.addItemToShop(item);
            
            System.out.println("Successfully sold " + item.getName() + " for " + sellPrice + " gold!");
            return true;
            
        } catch (Exception e) {
            System.out.println("Error during sale: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Equips an item from the player's backpack.
     * 
     * @param item the item to equip
     * @return true if equip successful, false otherwise
     */
    public boolean equipItem(Item item) {
        try {
            // Step 1: Check item in backpack
            if (!player.getInventory().getBackpackItems().contains(item)) {
                System.out.println("Error: Item not in backpack.");
                return false;
            }
            
            // Step 2-3: Execute equip (Command pattern integration in Section 4.1)
            player.equipItem(item);
            
            return true;
            
        } catch (Exception e) {
            System.out.println("Error during equip: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Unequips an item back to the player's backpack.
     * 
     * @param item the item to unequip
     * @return true if unequip successful, false otherwise
     */
    public boolean unequipItem(Item item) {
        try {
            // Step 1: Check item is equipped
            if (!player.getInventory().getEquippedItems().contains(item)) {
                System.out.println("Error: Item not equipped.");
                return false;
            }
            
            // Step 2: Check backpack space
            if (!player.getInventory().hasSpace()) {
                System.out.println("Error: Backpack is full!");
                return false;
            }
            
            // Step 3-4: Execute unequip (Command pattern integration in Section 4.1)
            player.unequipItem(item);
            
            return true;
            
        } catch (Exception e) {
            System.out.println("Error during unequip: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Returns the list of items available in the shop.
     * @return list of available items
     */
    public List<Item> getAvailableItems() {
        return shopManager.getAvailableItems();
    }
    
    /**
     * Returns the player's inventory items.
     * @return list of items in backpack
     */
    public List<Item> getPlayerInventory() {
        return player.getInventory().getBackpackItems();
    }
    
    /**
     * Returns the player's equipped items.
     * @return list of equipped items
     */
    public List<Item> getEquippedItems() {
        return player.getInventory().getEquippedItems();
    }
    
    /**
     * Returns the player's current gold amount.
     * @return player's gold
     */
    public double getPlayerGold() {
        return player.getGold();
    }
    
    /**
     * Returns the player object.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Prints a formatted status overview of the shop and player.
     */
    public void printShopStatus() {
        System.out.println("\n===== SHOP STATUS =====");
        System.out.println("Available Items: " + shopManager.getAvailableItems().size());
        System.out.println("Player: " + player.getName() + " (Level " + player.getLevel() + ")");
        System.out.printf("Gold: %.2f%n", player.getGold());
        System.out.println("Inventory: " + player.getInventory().getBackpackItems().size() + 
                          "/6 slots");
        System.out.println("Equipped: " + player.getInventory().getEquippedItems().size() + " items");
        System.out.println("======================\n");
    }
    
    /**
     * Prints all available items in the shop.
     */
    public void printAvailableItems() {
        shopManager.printShopInventory();
    }
    
    /**
     * Prints the player's inventory.
     */
    public void printPlayerInventory() {
        player.printInventory();
    }
}
