package patterns.structural;

import java.util.HashMap;
import java.util.Map;
import model.Item;

/**
 * Decorator that adds attack bonus to an item.
 */
public class AttackBoostDecorator extends ItemDecorator {
    private final int attackBonus;
    
    /**
     * Creates a new AttackBoostDecorator that adds attack bonus to the item.
     * @param item the item to decorate
     * @param attackBonus the attack bonus to add
     */
    public AttackBoostDecorator(Item item, int attackBonus) {
        super(item);
        this.attackBonus = attackBonus;
    }
    
    /**
     * Returns the name with attack boost indicator.
     * @return modified name showing the attack boost
     */
    @Override
    public String getName() {
        return wrappedItem.getName() + " (+ATK +" + attackBonus + ")";
    }
    
    /**
     * Returns the price including upgrade cost.
     * Each +1 attack costs 10 gold.
     * @return modified price
     */
    @Override
    public double getPrice() {
        return wrappedItem.getPrice() + (attackBonus * 10);
    }
    
    /**
     * Returns the stats with increased attack value.
     * @return modified stats map with attack bonus
     */
    @Override
    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>(wrappedItem.getStats());
        stats.put("Attack", stats.getOrDefault("Attack", 0) + attackBonus);
        return stats;
    }
    
    /**
     * Returns the description with upgrade information.
     * @return modified description
     */
    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + "\n[UPGRADE] +" + attackBonus + " Attack bonus added";
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


