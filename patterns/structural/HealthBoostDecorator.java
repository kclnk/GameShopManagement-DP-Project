package patterns.structural;

import java.util.HashMap;
import java.util.Map;
import model.Item;

/**
 * HealthBoostDecorator adds a health bonus to any item.
 * Part of the Decorator pattern - wraps an item and enhances its health stat.
 */
public class HealthBoostDecorator extends ItemDecorator {
    private final int healthBonus;
    
    /**
     * Creates a new HealthBoostDecorator that adds health bonus to the item.
     * @param item the item to decorate
     * @param healthBonus the health bonus to add
     */
    public HealthBoostDecorator(Item item, int healthBonus) {
        super(item);
        this.healthBonus = healthBonus;
    }
    
    /**
     * Returns the name with health boost indicator.
     * @return modified name showing the health boost
     */
    @Override
    public String getName() {
        return wrappedItem.getName() + " (+HP +" + healthBonus + ")";
    }
    
    /**
     * Returns the price including upgrade cost.
     * Each +1 health costs 2 gold.
     * @return modified price
     */
    @Override
    public double getPrice() {
        return wrappedItem.getPrice() + (healthBonus * 2);
    }
    
    /**
     * Returns the stats with increased health value.
     * @return modified stats map with health bonus
     */
    @Override
    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>(wrappedItem.getStats());
        stats.put("Health", stats.getOrDefault("Health", 0) + healthBonus);
        return stats;
    }
    
    /**
     * Returns the description with upgrade information.
     * @return modified description
     */
    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + "\n[UPGRADE] +" + healthBonus + " Health bonus added";
    }
    
    @Override
    public String toString() {
        Map<String, Integer> stats = getStats();
        StringBuilder statString = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            if (count > 0) statString.append(", ");
            statString.append(stat.getKey()).append(":").append(stat.getValue());
            count++;
        }
        statString.append("}");
        
        return "[" + getRarity() + "] " + getName() + " Ã¢â‚¬â€œ Price: " + getPrice() + " gold Ã¢â‚¬â€œ Stats: " + statString;
    }
}


