package patterns.structural;

import model.Item;
import java.util.HashMap;
import java.util.Map;

/**
 * DefenseBoostDecorator adds a defense bonus to any item.
 * Part of the Decorator pattern - wraps an item and enhances its defense stat.
 */
public class DefenseBoostDecorator extends ItemDecorator {
    private final int defenseBonus;
    
    /**
     * Creates a new DefenseBoostDecorator that adds defense bonus to the item.
     * @param item the item to decorate
     * @param defenseBonus the defense bonus to add
     */
    public DefenseBoostDecorator(Item item, int defenseBonus) {
        super(item);
        this.defenseBonus = defenseBonus;
    }
    
    /**
     * Returns the name with defense boost indicator.
     * @return modified name showing the defense boost
     */
    @Override
    public String getName() {
        return wrappedItem.getName() + " (+DEF +" + defenseBonus + ")";
    }
    
    /**
     * Returns the price including upgrade cost.
     * Each +1 defense costs 15 gold.
     * @return modified price
     */
    @Override
    public double getPrice() {
        return wrappedItem.getPrice() + (defenseBonus * 15);
    }
    
    /**
     * Returns the stats with increased defense value.
     * @return modified stats map with defense bonus
     */
    @Override
    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>(wrappedItem.getStats());
        stats.put("Defense", stats.getOrDefault("Defense", 0) + defenseBonus);
        return stats;
    }
    
    /**
     * Returns the description with upgrade information.
     * @return modified description
     */
    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + "\n[UPGRADE] +" + defenseBonus + " Defense bonus added";
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


