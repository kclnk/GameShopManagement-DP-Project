package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages player's equipped items and backpack storage.
 */
public class Inventory {
    private final List<Item> equippedItems;
    private final List<Item> backpackItems;
    private final int maxSlots;
    
    public Inventory(int maxSlots) {
        this.maxSlots = maxSlots;
        this.equippedItems = new ArrayList<>();
        this.backpackItems = new ArrayList<>();
    }
    
    /**
     * Adds an item to the backpack if space is available.
     * @param item the item to add
     * @return true if item was added successfully, false if backpack is full or item is null
     */
    public boolean addItem(Item item) {
        if (item == null) {
            return false;
        }
        if (!hasSpace()) {
            System.out.println("Backpack is full! Cannot add " + item.getName());
            return false;
        }
        backpackItems.add(item);
        System.out.println("Added " + item.getName() + " to backpack.");
        return true;
    }
    
    /**
     * Removes an item from the backpack.
     * @param item the item to remove
     * @return true if item was removed, false if not found or item is null
     */
    public boolean removeItem(Item item) {
        if (item == null) {
            return false;
        }
        boolean removed = backpackItems.remove(item);
        if (removed) {
            System.out.println("Removed " + item.getName() + " from backpack.");
        }
        return removed;
    }
    
    /**
     * Equips an item by moving it from backpack to equipped items.
     * @param item the item to equip
     * @return true if item was equipped successfully, false if item not in backpack
     */
    public boolean equipItem(Item item) {
        if (item == null) {
            return false;
        }
        if (!backpackItems.contains(item)) {
            System.out.println("Item " + item.getName() + " is not in backpack.");
            return false;
        }
        backpackItems.remove(item);
        equippedItems.add(item);
        System.out.println("Equipped " + item.getName() + ".");
        return true;
    }
    
    /**
     * Unequips an item by moving it from equipped to backpack.
     * @param item the item to unequip
     * @return true if item was unequipped successfully, false if not equipped or no space
     */
    public boolean unequipItem(Item item) {
        if (item == null) {
            return false;
        }
        if (!equippedItems.contains(item)) {
            System.out.println("Item " + item.getName() + " is not equipped.");
            return false;
        }
        if (!hasSpace()) {
            System.out.println("Backpack is full! Cannot unequip " + item.getName());
            return false;
        }
        equippedItems.remove(item);
        backpackItems.add(item);
        System.out.println("Unequipped " + item.getName() + ".");
        return true;
    }
    
    /**
     * Returns a copy of the equipped items list.
     * @return list of equipped items
     */
    public List<Item> getEquippedItems() {
        return new ArrayList<>(equippedItems);
    }
    
    /**
     * Returns a copy of the backpack items list.
     * @return list of backpack items
     */
    public List<Item> getBackpackItems() {
        return new ArrayList<>(backpackItems);
    }
    
    /**
     * Checks if the backpack has available space.
     * @return true if there are empty slots, false if full
     */
    public boolean hasSpace() {
        return backpackItems.size() < maxSlots;
    }
    
    /**
     * Returns the number of available slots in the backpack.
     * @return number of empty slots
     */
    public int getAvailableSlots() {
        return maxSlots - backpackItems.size();
    }
    
    /**
     * Finds and returns the first item with matching name.
     * Searches both equipped and backpack items.
     * @param itemName the name of the item to find
     * @return the item if found, null otherwise
     */
    public Item getItem(String itemName) {
        if (itemName == null) {
            return null;
        }
        
        // Search equipped items first
        for (Item item : equippedItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        
        // Search backpack items
        for (Item item : backpackItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        
        return null;
    }
    
    /**
     * Prints all equipped and backpack items in a formatted way.
     */
    public void printInventory() {
        System.out.println("\n===== INVENTORY =====");
        
        System.out.println("\n--- Equipped Items ---");
        if (equippedItems.isEmpty()) {
            System.out.println("  (No items equipped)");
        } else {
            for (int i = 0; i < equippedItems.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + equippedItems.get(i));
            }
        }
        
        System.out.println("\n--- Backpack (" + backpackItems.size() + "/" + maxSlots + ") ---");
        if (backpackItems.isEmpty()) {
            System.out.println("  (Empty)");
        } else {
            for (int i = 0; i < backpackItems.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + backpackItems.get(i));
            }
        }
        
        System.out.println("\nAvailable Slots: " + getAvailableSlots());
        System.out.println("=====================\n");
    }
}


