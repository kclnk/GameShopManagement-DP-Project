package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Weapon item that increases attack power.
 */
public class Weapon implements Item {
    private final String name;
    private final double price;
    private final int attackBonus;
    private final Map<String, Integer> stats;
    private final String rarity;
    private final String description;
    
    /**
     * Creates a new Weapon item.
     * @param name the name of the weapon
     * @param price the gold cost
     * @param attackBonus the attack power increase
     */
    public Weapon(String name, double price, int attackBonus) {
        this.name = name;
        this.price = price;
        this.attackBonus = attackBonus;
        this.rarity = "COMMON";
        this.description = "A weapon that increases attack power";
        
        // Initialize stats map
        this.stats = new HashMap<>();
        this.stats.put("Attack", attackBonus);
        this.stats.put("Defense", 0);
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



