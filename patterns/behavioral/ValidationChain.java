package patterns.behavioral;

import model.Item;
import model.Player;

/**
 * Manages the complete purchase validation chain.
 */
public class ValidationChain {
    private final PurchaseHandler chain;
    
    /**
     * Creates a new ValidationChain with all handlers configured.
     */
    public ValidationChain() {
        // Create all handlers
        PurchaseHandler goldCheck = new GoldCheckHandler();
        PurchaseHandler inventorySpace = new InventorySpaceHandler();
        PurchaseHandler levelReq = new LevelRequirementHandler();
        PurchaseHandler availability = new ItemAvailabilityHandler();
        
        // Chain them together
        goldCheck.setNext(inventorySpace).setNext(levelReq).setNext(availability);
        
        // Store reference to first handler
        this.chain = goldCheck;
    }
    
    /**
     * Validates a purchase request.
     * @param request the purchase request
     * @return true if all validations pass, false otherwise
     */
    public boolean validate(PurchaseRequest request) {
        return chain.handle(request);
    }
    
    /**
     * Validates a purchase with player and item.
     * @param player the player making the purchase
     * @param item the item to purchase
     * @return true if all validations pass, false otherwise
     */
    public boolean validate(Player player, Item item) {
        PurchaseRequest request = new PurchaseRequest(player, item);
        return validate(request);
    }
    
    /**
     * Validates a purchase and prints detailed results.
     * @param player the player making the purchase
     * @param item the item to purchase
     */
    public void validateAndPrint(Player player, Item item) {
        System.out.println("\n=== VALIDATING PURCHASE ===");
        System.out.println("Player: " + player.getName());
        System.out.println("Item: " + item.getName() + " (Price: " + item.getPrice() + ")");
        System.out.println("---");
        
        PurchaseRequest request = new PurchaseRequest(player, item);
        boolean isValid = validate(request);
        
        System.out.println("---");
        if (isValid) {
            System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬Å“ VALIDATION PASSED - Purchase approved!");
        } else {
            System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬â€ VALIDATION FAILED - Purchase denied!");
        }
        System.out.println("===========================\n");
    }
}
