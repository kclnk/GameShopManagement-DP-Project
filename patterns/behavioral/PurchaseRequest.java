package patterns.behavioral;

import model.Item;
import model.Player;
import java.util.UUID;

/**
 * PurchaseRequest encapsulates all data needed for purchase validation.
 * Used in Chain of Responsibility pattern to pass data through validation handlers.
 */
public class PurchaseRequest {
    private final Player player;
    private final Item item;
    private final double purchasePrice;
    private final String requestId;
    
    /**
     * Creates a new PurchaseRequest with the item's standard price.
     * @param player the player making the purchase
     * @param item the item to purchase
     */
    public PurchaseRequest(Player player, Item item) {
        this.player = player;
        this.item = item;
        this.purchasePrice = item.getPrice();
        this.requestId = UUID.randomUUID().toString();
    }
    
    /**
     * Creates a new PurchaseRequest with a custom price.
     * Useful for sales, discounts, or special pricing.
     * @param player the player making the purchase
     * @param item the item to purchase
     * @param customPrice the custom price for this transaction
     */
    public PurchaseRequest(Player player, Item item, double customPrice) {
        this.player = player;
        this.item = item;
        this.purchasePrice = customPrice;
        this.requestId = UUID.randomUUID().toString();
    }
    
    /**
     * Gets the player making the purchase.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Gets the item being purchased.
     * @return the item
     */
    public Item getItem() {
        return item;
    }
    
    /**
     * Gets the purchase price for this request.
     * @return the price
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }
    
    /**
     * Gets the unique request ID for logging and tracking.
     * @return the request ID
     */
    public String getRequestId() {
        return requestId;
    }
}


