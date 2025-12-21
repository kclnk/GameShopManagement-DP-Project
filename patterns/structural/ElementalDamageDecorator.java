package patterns.structural;

import model.Item;
import java.util.HashMap;
import java.util.Map;

/**
 * ElementalDamageDecorator adds elemental damage effects to any item.
 * Part of the Decorator pattern - wraps an item and adds elemental properties.
 * Supports Fire, Ice, and Lightning elements.
 */
public class ElementalDamageDecorator extends ItemDecorator {
    private final String elementType;
    
    /**
     * Creates a new ElementalDamageDecorator that adds elemental damage to the item.
     * @param item the item to decorate
     * @param elementType the type of element ("Fire", "Ice", "Lightning")
     */
    public ElementalDamageDecorator(Item item, String elementType) {
        super(item);
        this.elementType = elementType;
    }
    
    /**
     * Returns the name with elemental indicator.
     * @return modified name showing the element type
     */
    @Override
    public String getName() {
        return wrappedItem.getName() + " [" + elementType.toUpperCase() + "]";
    }
    
    /**
     * Returns the price with 50% increase for elemental enchantment.
     * @return modified price (1.5x original)
     */
    @Override
    public double getPrice() {
        return wrappedItem.getPrice() * 1.5;
    }
    
    /**
     * Returns the stats with added elemental damage.
     * Adds 20 points of the corresponding elemental damage type.
     * @return modified stats map with elemental damage
     */
    @Override
    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>(wrappedItem.getStats());
        
        // Add elemental damage based on type
        if (elementType.equalsIgnoreCase("Fire")) {
            stats.put("FireDamage", stats.getOrDefault("FireDamage", 0) + 20);
        } else if (elementType.equalsIgnoreCase("Ice")) {
            stats.put("IceDamage", stats.getOrDefault("IceDamage", 0) + 20);
        } else if (elementType.equalsIgnoreCase("Lightning")) {
            stats.put("LightningDamage", stats.getOrDefault("LightningDamage", 0) + 20);
        }
        
        return stats;
    }
    
    /**
     * Returns the description with elemental enchantment information.
     * @return modified description
     */
    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + "\n[ENCHANTMENT] Infused with " + elementType + " damage!";
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
