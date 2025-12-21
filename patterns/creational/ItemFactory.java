package patterns.creational;

import model.*;

/**
 * Factory for creating different types of items.
 */
public class ItemFactory {
    
    public static Item createWeapon(String name, double price, int attackBonus) {
        return new Weapon(name, price, attackBonus);
    }
    
    /**
     * Creates a new Armor item.
     * @param name the armor name
     * @param price the gold cost
     * @param defenseBonus the defense increase
     * @return a new Armor instance
     */
    public static Item createArmor(String name, double price, int defenseBonus) {
        return new Armor(name, price, defenseBonus);
    }
    
    /**
     * Creates a new Potion item.
     * @param name the potion name
     * @param price the gold cost
     * @param healthBonus the health restoration amount
     * @return a new Potion instance
     */
    public static Item createPotion(String name, double price, int healthBonus) {
        return new Potion(name, price, healthBonus);
    }
    
    /**
     * Creates a new Trinket item.
     * @param name the trinket name
     * @param price the gold cost
     * @param manaBonus the mana increase
     * @return a new Trinket instance
     */
    public static Item createTrinket(String name, double price, int manaBonus) {
        return new Trinket(name, price, manaBonus);
    }
    
    /**
     * Main factory method that creates an item based on the type parameter.
     * This method routes to the appropriate specific creation method.
     * 
     * @param type the type of item to create ("WEAPON", "ARMOR", "POTION", "TRINKET")
     * @param name the item name
     * @param price the gold cost
     * @param value the stat bonus (attack/defense/health/mana depending on type)
     * @return a new Item instance of the specified type
     * @throws IllegalArgumentException if the type is invalid or null
     */
    public static Item createItem(String type, String name, double price, int value) {
        // Validate type parameter
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Item type cannot be null or empty");
        }
        
        // Route to appropriate factory method based on type
        switch (type.toUpperCase().trim()) {
            case "WEAPON":
                return createWeapon(name, price, value);
                
            case "ARMOR":
                return createArmor(name, price, value);
                
            case "POTION":
                return createPotion(name, price, value);
                
            case "TRINKET":
                return createTrinket(name, price, value);
                
            default:
                throw new IllegalArgumentException(
                    "Invalid item type: " + type + ". Valid types are: WEAPON, ARMOR, POTION, TRINKET"
                );
        }
    }
    
    /**
     * Prints information about what item types the factory can create.
     */
    public static void printFactoryInfo() {
        System.out.println("\n===== ITEM FACTORY INFO =====");
        System.out.println("ItemFactory can create the following item types:");
        System.out.println("  - WEAPON: Increases attack power");
        System.out.println("  - ARMOR: Increases defense");
        System.out.println("  - POTION: Restores health");
        System.out.println("  - TRINKET: Increases mana");
        System.out.println("=============================\n");
    }
}


