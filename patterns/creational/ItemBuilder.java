package patterns.creational;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.CustomItem;
import model.Item;

/**
 * Builder for constructing complex CustomItem objects.
 */
public class ItemBuilder {
    private String name;
    private double price;
    private String rarity;
    private Map<String, Integer> stats;
    private String description;
    private List<String> effects;
    
    public ItemBuilder() {
        this.name = "Unknown Item";
        this.price = 0.0;
        this.rarity = "COMMON";
        this.stats = new HashMap<>();
        this.description = "";
        this.effects = new ArrayList<>();
    }
    
    public ItemBuilder setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        return this;
    }
    
    public ItemBuilder setPrice(double price) {
        this.price = Math.max(0, price);
        return this;
    }
    
    public ItemBuilder setRarity(String rarity) {
        if (rarity != null) {
            String upper = rarity.toUpperCase().trim();
            if (upper.equals("COMMON") || upper.equals("UNCOMMON") || 
                upper.equals("RARE") || upper.equals("EPIC") || 
                upper.equals("LEGENDARY")) {
                this.rarity = upper;
            } else {
                System.out.println("Warning: Invalid rarity '" + rarity + "'. Using COMMON.");
                this.rarity = "COMMON";
            }
        }
        return this;
    }
    
    public ItemBuilder addStat(String statName, int value) {
        if (statName != null && !statName.trim().isEmpty()) {
            this.stats.put(statName, value);
        }
        return this;
    }
    
    public ItemBuilder setStats(Map<String, Integer> stats) {
        if (stats != null) {
            this.stats = new HashMap<>(stats);
        }
        return this;
    }
    
    public ItemBuilder setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
        return this;
    }
    
    public ItemBuilder addEffect(String effect) {
        if (effect != null && !effect.trim().isEmpty()) {
            this.effects.add(effect);
        }
        return this;
    }
    
    public ItemBuilder addEffects(List<String> effects) {
        if (effects != null) {
            this.effects.addAll(effects);
        }
        return this;
    }
    
    public Item build() {
        return new CustomItem(
            name, 
            price, 
            rarity, 
            new HashMap<>(stats),
            description, 
            new ArrayList<>(effects)
        );
    }
    
    public ItemBuilder reset() {
        this.name = "Unknown Item";
        this.price = 0.0;
        this.rarity = "COMMON";
        this.stats = new HashMap<>();
        this.description = "";
        this.effects = new ArrayList<>();
        return this;
    }
}


