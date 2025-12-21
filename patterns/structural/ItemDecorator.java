package patterns.structural;

import java.util.HashMap;
import java.util.Map;
import model.Item;

/**
 * Abstract base class for decorating items with additional functionality.
 */
public abstract class ItemDecorator implements Item {
    protected Item wrappedItem;
    
    public ItemDecorator(Item item) {
        this.wrappedItem = item;
    }
    
    /**
     * Returns the name of the wrapped item.
     * Subclasses can override to modify the name.
     * @return the item name
     */
    @Override
    public String getName() {
        return wrappedItem.getName();
    }
    
    /**
     * Returns the price of the wrapped item.
     * Subclasses can override to modify the price.
     * @return the item price
     */
    @Override
    public double getPrice() {
        return wrappedItem.getPrice();
    }
    
    /**
     * Returns the rarity of the wrapped item.
     * @return the item rarity
     */
    @Override
    public String getRarity() {
        return wrappedItem.getRarity();
    }
    
    /**
     * Returns the description of the wrapped item.
     * Subclasses can override to add upgrade descriptions.
     * @return the item description
     */
    @Override
    public String getDescription() {
        return wrappedItem.getDescription();
    }
    
    /**
     * Returns the stats of the wrapped item.
     * Subclasses can override to modify or add stats.
     * @return a copy of the stats map
     */
    @Override
    public Map<String, Integer> getStats() {
        // Return a copy to allow subclasses to modify safely
        return new HashMap<>(wrappedItem.getStats());
    }
    
    /**
     * Prints the stats of the decorated item.
     * Shows the current decorated state, not the wrapped item's original state.
     */
    @Override
    public void printStats() {
        System.out.println("===== Item Details =====");
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice() + " gold");
        System.out.println("Rarity: " + getRarity());
        System.out.println("Description: " + getDescription());
        System.out.println("Stats:");
        Map<String, Integer> stats = getStats();
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            System.out.println("  " + stat.getKey() + ": " + stat.getValue());
        }
        System.out.println("========================");
    }
    
    @Override
    public String toString() {
        return wrappedItem.toString();
    }
}


