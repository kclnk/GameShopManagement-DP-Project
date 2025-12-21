package patterns.creational;

import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 * Singleton shop manager ensuring only one instance exists.
 */
public class ShopManager {
    private static ShopManager instance = null;
    
    // Shop inventory lists
    private final List<Item> availableItems;
    private final List<Item> soldOutItems;
    
    private ShopManager() {
        this.availableItems = new ArrayList<>();
        this.soldOutItems = new ArrayList<>();
        
        // Initialize shop with starter items
        initializeShopItems();
    }
    
    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
            System.out.println("ShopManager instance created for the first time.");
        }
        return instance;
    }
    
    private void initializeShopItems() {
        // Add starter weapons
        availableItems.add(new Weapon("Longsword", 500, 25));
        availableItems.add(new Weapon("Great Axe", 1200, 50));
        
        // Add starter armor
        availableItems.add(new Armor("Iron Armor", 800, 20));
        availableItems.add(new Armor("Steel Plate", 1500, 40));
        
        // Add starter potions
        availableItems.add(new Potion("Health Potion", 50, 100));
        availableItems.add(new Potion("Greater Health Potion", 150, 300));
        
        // Add starter trinkets
        availableItems.add(new Trinket("Sapphire Gem", 1200, 30));
        availableItems.add(new Trinket("Emerald Ring", 1500, 50));
        
        System.out.println("Shop initialized with " + availableItems.size() + " items.");
    }
    
    /**
     * Adds an item to the shop's available inventory.
     * @param item the item to add to the shop
     */
    public void addItemToShop(Item item) {
        if (item == null) {
            System.out.println("Cannot add null item to shop.");
            return;
        }
        availableItems.add(item);
        System.out.println("Added " + item.getName() + " to shop inventory.");
    }
    
    /**
     * Removes an item from the shop (marks as sold).
     * Moves item from availableItems to soldOutItems.
     * @param item the item to remove from the shop
     */
    public void removeItemFromShop(Item item) {
        if (item == null) {
            System.out.println("Cannot remove null item from shop.");
            return;
        }
        
        if (availableItems.remove(item)) {
            soldOutItems.add(item);
            System.out.println(item.getName() + " removed from shop (sold out).");
        } else {
            System.out.println(item.getName() + " is not available in the shop.");
        }
    }
    
    /**
     * Returns a copy of the available items list.
     * Returns a copy to prevent external modification of the shop's inventory.
     * @return a copy of the available items list
     */
    public List<Item> getAvailableItems() {
        return new ArrayList<>(availableItems);
    }
    
    /**
     * Returns a copy of the sold out items list.
     * Returns a copy to prevent external modification.
     * @return a copy of the sold out items list
     */
    public List<Item> getSoldOutItems() {
        return new ArrayList<>(soldOutItems);
    }
    
    /**
     * Searches for an item by name in available inventory.
     * @param itemName the name of the item to find
     * @return the item if found, null otherwise
     */
    public Item findItemByName(String itemName) {
        if (itemName == null) {
            return null;
        }
        
        for (Item item : availableItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        
        return null;
    }
    
    /**
     * Checks if an item is available in the shop.
     * @param item the item to check
     * @return true if item is available, false otherwise
     */
    public boolean isItemAvailable(Item item) {
        if (item == null) {
            return false;
        }
        return availableItems.contains(item);
    }
    
    /**
     * Restocks an item by moving it from sold out back to available.
     * @param item the item to restock
     */
    public void restockItem(Item item) {
        if (item == null) {
            System.out.println("Cannot restock null item.");
            return;
        }
        
        if (soldOutItems.remove(item)) {
            availableItems.add(item);
            System.out.println(item.getName() + " has been restocked.");
        } else {
            System.out.println(item.getName() + " is not in the sold out list.");
        }
    }
    
    /**
     * Prints all available items in the shop in a formatted way.
     */
    public void printShopInventory() {
        System.out.println("\n===== SHOP INVENTORY =====");
        
        if (availableItems.isEmpty()) {
            System.out.println("  (No items available)");
        } else {
            for (int i = 0; i < availableItems.size(); i++) {
                Item item = availableItems.get(i);
                System.out.println((i + 1) + ". " + item);
            }
        }
        
        System.out.println("\nAvailable: " + availableItems.size() + " items");
        System.out.println("Sold Out: " + soldOutItems.size() + " items");
        System.out.println("==========================\n");
    }
    
    /**
     * Prints all sold out items.
     */
    public void printSoldOutItems() {
        System.out.println("\n===== SOLD OUT ITEMS =====");
        
        if (soldOutItems.isEmpty()) {
            System.out.println("  (No sold out items)");
        } else {
            for (int i = 0; i < soldOutItems.size(); i++) {
                Item item = soldOutItems.get(i);
                System.out.println((i + 1) + ". " + item);
            }
        }
        
        System.out.println("\nTotal Sold Out: " + soldOutItems.size() + " items");
        System.out.println("==========================\n");
    }
}


