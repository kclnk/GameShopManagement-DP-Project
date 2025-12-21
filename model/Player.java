package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Player class managing stats, gold, level, and inventory.
 */
public class Player {
    private final String name;
    private int level;
    private double gold;
    private final Inventory inventory;
    private final Map<String, Integer> stats;
    
    public Player(String name, int level, double initialGold) {
        this.name = name;
        
        // Validate and set level (1-30)
        if (level < 1) {
            this.level = 1;
        } else if (level > 30) {
            this.level = 30;
        } else {
            this.level = level;
        }
        
        // Validate and set gold (cannot be negative)
        this.gold = Math.max(0, initialGold);
        
        // Create inventory with 6 slots
        this.inventory = new Inventory(6);
        
        // Initialize base stats
        this.stats = new HashMap<>();
        this.stats.put("Attack", 0);
        this.stats.put("Defense", 0);
        this.stats.put("Health", 100);
        this.stats.put("Mana", 50);
        this.stats.put("Level", this.level);
    }
    
    /**
     * Adds gold to the player.
     * @param amount the amount of gold to add
     */
    public void addGold(double amount) {
        if (amount > 0) {
            this.gold += amount;
            System.out.println("Added " + amount + " gold. Current gold: " + this.gold);
        }
    }
    
    /**
     * Removes gold from the player if they have enough.
     * @param amount the amount of gold to remove
     * @return true if gold was removed successfully, false if insufficient gold
     */
    public boolean removeGold(double amount) {
        if (amount < 0) {
            return false;
        }
        if (this.gold >= amount) {
            this.gold -= amount;
            System.out.println("Removed " + amount + " gold. Current gold: " + this.gold);
            return true;
        } else {
            System.out.println("Insufficient gold! Required: " + amount + ", Available: " + this.gold);
            return false;
        }
    }
    
    /**
     * Returns the current gold amount.
     * @return current gold
     */
    public double getGold() {
        return gold;
    }
    
    /**
     * Returns the player's name.
     * @return player name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the player's level.
     * @return current level
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Sets the player's level and updates the stats map.
     * @param newLevel the new level (1-30)
     */
    public void setLevel(int newLevel) {
        if (newLevel < 1) {
            this.level = 1;
        } else if (newLevel > 30) {
            this.level = 30;
        } else {
            this.level = newLevel;
        }
        
        // Update level in stats map
        this.stats.put("Level", this.level);
        System.out.println("Level set to " + this.level);
    }
    
    /**
     * Returns the player's inventory reference.
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    /**
     * Returns a copy of the player's stats map.
     * @return map of stats
     */
    public Map<String, Integer> getStats() {
        return new HashMap<>(stats);
    }
    
    /**
     * Adds an item to the player's inventory.
     * Wrapper method for inventory.addItem().
     * @param item the item to add
     */
    public void addItem(Item item) {
        if (item != null) {
            inventory.addItem(item);
        }
    }
    
    /**
     * Removes an item from the player's inventory.
     * Wrapper method for inventory.removeItem().
     * @param item the item to remove
     * @return true if item was removed, false otherwise
     */
    public boolean removeItemFromInventory(Item item) {
        if (item == null) {
            return false;
        }
        return inventory.removeItem(item);
    }
    
    /**
     * Equips an item from the player's backpack.
     * Wrapper method for inventory.equipItem().
     * @param item the item to equip
     */
    public void equipItem(Item item) {
        if (item != null) {
            inventory.equipItem(item);
        }
    }
    
    /**
     * Unequips an item and moves it to the backpack.
     * Wrapper method for inventory.unequipItem().
     * @param item the item to unequip
     */
    public void unequipItem(Item item) {
        if (item != null) {
            inventory.unequipItem(item);
        }
    }
    
    /**
     * Prints player information including name, level, gold, and stats.
     */
    public void printPlayerInfo() {
        System.out.println("\n========== PLAYER INFO ==========");
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("Gold: " + gold);
        System.out.println("\n--- Base Stats ---");
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            System.out.println("  " + stat.getKey() + ": " + stat.getValue());
        }
        System.out.println("=================================\n");
    }
    
    /**
     * Prints the player's inventory.
     * Delegates to inventory.printInventory().
     */
    public void printInventory() {
        inventory.printInventory();
    }
}



