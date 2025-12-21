package patterns.behavioral;

import model.Item;
import model.Player;
import patterns.creational.ShopManager;

/**
 * Observer interface for shop event notifications.
 */
public interface ShopObserver {
    /**
     * Called when player's gold amount changes.
     * @param player the player whose gold changed
     */
    void onPlayerGoldChanged(Player player);
    
    /**
     * Called when player's inventory changes (items added/removed).
     * @param player the player whose inventory changed
     */
    void onInventoryChanged(Player player);
    
    /**
     * Called when shop inventory changes (items added/removed/sold).
     * @param shop the shop whose inventory changed
     */
    void onShopInventoryChanged(ShopManager shop);
    
    /**
     * Called when player equips an item.
     * @param item the item that was equipped
     * @param player the player who equipped the item
     */
    void onItemEquipped(Item item, Player player);
    
    /**
     * Called when player unequips an item.
     * @param item the item that was unequipped
     * @param player the player who unequipped the item
     */
    void onItemUnequipped(Item item, Player player);
}


