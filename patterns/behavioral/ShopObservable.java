package patterns.behavioral;

import java.util.ArrayList;
import java.util.List;
import model.Item;
import model.Player;
import patterns.creational.ShopManager;

/**
 * Observable subject that manages and notifies observers of shop events.
 */
public class ShopObservable {
    private final List<ShopObserver> observers;
    
    /**
     * Creates a new ShopObservable with empty observer list.
     */
    public ShopObservable() {
        this.observers = new ArrayList<>();
    }
    
    /**
     * Registers an observer to receive notifications.
     * @param observer the observer to register
     */
    public void registerObserver(ShopObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
            System.out.println("[Observer registered] " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Removes an observer from receiving notifications.
     * @param observer the observer to remove
     */
    public void removeObserver(ShopObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("[Observer removed] " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Notifies all observers that player's gold changed.
     * @param player the player whose gold changed
     */
    public void notifyGoldChanged(Player player) {
        for (ShopObserver observer : observers) {
            observer.onPlayerGoldChanged(player);
        }
    }
    
    /**
     * Notifies all observers that player's inventory changed.
     * @param player the player whose inventory changed
     */
    public void notifyInventoryChanged(Player player) {
        for (ShopObserver observer : observers) {
            observer.onInventoryChanged(player);
        }
    }
    
    /**
     * Notifies all observers that shop inventory changed.
     * @param shop the shop whose inventory changed
     */
    public void notifyShopChanged(ShopManager shop) {
        for (ShopObserver observer : observers) {
            observer.onShopInventoryChanged(shop);
        }
    }
    
    /**
     * Notifies all observers that an item was equipped.
     * @param item the item that was equipped
     * @param player the player who equipped the item
     */
    public void notifyItemEquipped(Item item, Player player) {
        for (ShopObserver observer : observers) {
            observer.onItemEquipped(item, player);
        }
    }
    
    /**
     * Notifies all observers that an item was unequipped.
     * @param item the item that was unequipped
     * @param player the player who unequipped the item
     */
    public void notifyItemUnequipped(Item item, Player player) {
        for (ShopObserver observer : observers) {
            observer.onItemUnequipped(item, player);
        }
    }
    
    /**
     * Returns the number of registered observers.
     * @return observer count
     */
    public int getObserverCount() {
        return observers.size();
    }
}


