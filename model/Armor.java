package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Armor item that increases defense.
 */
public class Armor implements Item {
    private final String name;
    private final double price;
    private final int defenseBonus;
    private final Map<String, Integer> stats;
    private final String rarity;
    private final String description;
    
    /**
     * Creates a new Armor item.
     * @param name the name of the armor
     * @param price the gold cost
     * @param defenseBonus the defense increase
     */
    public Armor(String name, double price, int defenseBonus) {
        this.name = name;
        this.price = price;
        this.defenseBonus = defenseBonus;
        this.rarity = "UNCOMMON";
        this.description = "Armor that increases defense";
        
        // Initialize stats map
        this.stats = new HashMap<>();
        this.stats.put("Attack", 0);
        this.stats.put("Defense", defenseBonus);
        this.stats.put("Health", 0);
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



