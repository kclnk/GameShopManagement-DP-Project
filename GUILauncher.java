import javax.swing.*;
import model.*;
import patterns.creational.*;
import patterns.structural.*;
import ui.*;

/**
 * GUI Launcher for the shop system.
 */
public class GUILauncher {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("=== League of Legends Shop System ===");
        System.out.println("Initializing...\n");
        
        Player player = new Player("Summoner", 18, 10000);
        System.out.println("Player created: " + player.getName() + " (Level " + player.getLevel() + ")");
        System.out.println("Starting gold: " + player.getGold());
        
        ShopManager shop = ShopManager.getInstance();
        System.out.println("Shop initialized with " + shop.getAvailableItems().size() + " items");
        
        shop.addItemToShop(ItemFactory.createItem("WEAPON", "Iron Sword", 600, 15));
        shop.addItemToShop(ItemFactory.createItem("WEAPON", "Steel Longsword", 1200, 30));
        shop.addItemToShop(ItemFactory.createItem("WEAPON", "Dragon Blade", 2500, 60));
        shop.addItemToShop(ItemFactory.createItem("WEAPON", "Infinity Edge", 3400, 85));
        
        shop.addItemToShop(ItemFactory.createItem("ARMOR", "Leather Vest", 400, 10));
        shop.addItemToShop(ItemFactory.createItem("ARMOR", "Chain Mail", 800, 25));
        shop.addItemToShop(ItemFactory.createItem("ARMOR", "Plate Armor", 1800, 50));
        shop.addItemToShop(ItemFactory.createItem("ARMOR", "Thornmail", 2900, 80));
        
        shop.addItemToShop(ItemFactory.createItem("POTION", "Health Potion", 150, 100));
        shop.addItemToShop(ItemFactory.createItem("TRINKET", "Ruby Crystal", 400, 50));
        shop.addItemToShop(ItemFactory.createItem("TRINKET", "Amplifying Tome", 900, 100));
        shop.addItemToShop(ItemFactory.createItem("TRINKET", "Rabadon's Deathcap", 3800, 200));
        
        Item legendaryItem = new ItemBuilder()
            .setName("Infinity Stone")
            .setPrice(99999)
            .setRarity("Legendary")
            .addStat("Attack", 150)
            .addStat("Critical Chance", 100)
            .addStat("Attack Speed", 50)
            .build();
        shop.addItemToShop(legendaryItem);
        
        System.out.println("Shop now has " + shop.getAvailableItems().size() + " items available");
        
        ShoppingFacade facade = new ShoppingFacade(player);
        
        System.out.println("\nLaunching GUI...\n");
        
        SwingUtilities.invokeLater(() -> {
            new MainFrame(player, facade);
        });
    }
}


