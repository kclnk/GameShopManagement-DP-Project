package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Trinket item that increases mana.
 */
public class Trinket implements Item {
    private final String name;
    private final double price;
    private final int manaBonus;
    private final Map<String, Integer> stats;
    private final String rarity;
    private final String description;
    
    /**
     * Creates a new Trinket item.
     * @param name the name of the trinket
     * @param price the gold cost
     * @param manaBonus the mana increase
     */
    public Trinket(String name, double price, int manaBonus) {
        this.name = name;
        this.price = price;
        this.manaBonus = manaBonus;
        this.rarity = "RARE";
        this.description = "A mystical trinket that increases mana";
        
        // Initialize stats map
        this.stats = new HashMap<>();
        this.stats.put("Mana", manaBonus);
        this.stats.put("Attack", 0);
        this.stats.put("Defense", 0);
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
        return new HashMap<>(stats); // Return a copy to preserve immutability
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public void printStats() {
        System.out.println("===== Item Details =====");
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + " gold");
        System.out.println("Rarity: " + rarity);
        System.out.println("Description: " + description);
        System.out.println("Stats:");
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            System.out.println("  " + stat.getKey() + ": " + stat.getValue());
        }
        System.out.println("========================");
    }
    
    @Override
    public String toString() {
        StringBuilder statString = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            if (count > 0) statString.append(", ");
            statString.append(stat.getKey()).append(":").append(stat.getValue());
            count++;
        }
        statString.append("}");
        
        return "[" + rarity + "] " + name + " - Price: " + price + " gold - Stats: " + statString;
    }
}



