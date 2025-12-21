package patterns.behavioral;

import model.Item;
import patterns.creational.ShopManager;

/**
 * ItemAvailabilityHandler validates that the item is available in the shop.
 * Fourth handler in the validation chain.
 */
public class ItemAvailabilityHandler extends PurchaseHandler {
    
    /**
     * Validates that the item is available in the shop (not sold out).
     * @param request the purchase request
     * @return true if item is available, false if sold out
     */
    @Override
    protected boolean validate(PurchaseRequest request) {
        Item item = request.getItem();
        ShopManager shop = ShopManager.getInstance();
        
        boolean isAvailable = shop.isItemAvailable(item);
        
        logValidation(request, isAvailable,
            "Availability Check: " + item.getName() + 
            " is " + (isAvailable ? "available" : "sold out") + 
            (isAvailable ? " Ã¢Å“â€œ" : " Ã¢Å“â€”"));
        
        return isAvailable;
    }
}


