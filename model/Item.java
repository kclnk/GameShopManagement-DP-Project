package model;

import java.util.Map;

/**
 * Interface for all items in the shop system.
 */
public interface Item {
    /**
     * Returns the name of the item.
     * @return the item name (e.g., "Longsword")
     */
    String getName();
    
    /**
     * Returns the gold cost of the item.
     * @return the price in gold
     */
    double getPrice();
    
    /**
     * Returns the rarity of the item.
     * @return rarity as String ("COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY")
     */
    String getRarity();
    
    /**
     * Returns a map of stat names to values.
     * @return map of stats (e.g., "Attack" -> 25, "Defense" -> 0)
     */
    Map<String, Integer> getStats();
    
    /**
     * Returns the item description/flavor text.
     * @return item description
     */
    String getDescription();
    
    /**
     * Prints the item's name, price, rarity, and all stats to console.
     */
    void printStats();
}


