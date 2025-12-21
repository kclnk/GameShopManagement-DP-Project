package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flexible item implementation supporting multiple stats and effects.
 */
public class CustomItem implements Item {
    private final String name;
    private final double price;
    private final String rarity;
    private final Map<String, Integer> stats;
    private final String description;
    private final List<String> effects;
    
    /**
     * Creates a new CustomItem with specified parameters.
     * Typically constructed using ItemBuilder for better readability.
     * 
     * @param name the item name
     * @param price the gold cost
     * @param rarity the rarity level
     * @param stats map of stat names to values
     * @param description item description/flavor text
     * @param effects list of special effects
     */
    public CustomItem(String name, double price, String rarity, 
                     Map<String, Integer> stats, String description, 
                     List<String> effects) {
        this.name = name;
        this.price = price;
        this.rarity = rarity;
        // Defensive copying to ensure immutability
        this.stats = new HashMap<>(stats);
        this.description = description;
        this.effects = new ArrayList<>(effects);
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public double getPrice() {
        return price;
    }
    
    @Override
    public String getRarity() {
        return rarity;
    }
    
    @Override
    public Map<String, Integer> getStats() {
        return new HashMap<>(stats); // Return copy to preserve immutability
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns the list of special effects this item provides.
     * @return copy of effects list
     */
    public List<String> getEffects() {
        return new ArrayList<>(effects); // Return copy to preserve immutability
    }
    
    @Override
    public void printStats() {
        System.out.println("===== ITEM DETAILS =====");
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + " gold");
        System.out.println("Rarity: " + rarity);
        System.out.println("Description: " + description);
        
        System.out.println("\nStats:");
        if (stats.isEmpty()) {
            System.out.println("  (No stats)");
        } else {
            for (Map.Entry<String, Integer> stat : stats.entrySet()) {
                System.out.println("  " + stat.getKey() + ": " + stat.getValue());
            }
        }
        
        System.out.println("\nSpecial Effects:");
        if (effects.isEmpty()) {
            System.out.println("  (No effects)");
        } else {
            for (String effect : effects) {
                System.out.println("  Ã¢â‚¬Â¢ " + effect);
            }
        }
        
        System.out.println("========================");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(rarity).append("] ").append(name);
        sb.append(" Ã¢â‚¬â€œ Price: ").append(price).append(" gold");
        
        // Add stats
        if (!stats.isEmpty()) {
            sb.append(" Ã¢â‚¬â€œ Stats: {");
            int count = 0;
            for (Map.Entry<String, Integer> stat : stats.entrySet()) {
                if (count > 0) sb.append(", ");
                sb.append(stat.getKey()).append(":").append(stat.getValue());
                count++;
            }
            sb.append("}");
        }
        
        // Add effects
        if (!effects.isEmpty()) {
            sb.append(" Ã¢â‚¬â€œ Effects: [");
            for (int i = 0; i < effects.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(effects.get(i));
            }
            sb.append("]");
        }
        
        return sb.toString();
    }
}


