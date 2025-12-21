package patterns.behavioral;

import model.Player;

/**
 * InventorySpaceHandler validates that the player has available inventory space.
 * Second handler in the validation chain.
 */
public class InventorySpaceHandler extends PurchaseHandler {
    
    /**
     * Validates that the player has inventory space.
     * @param request the purchase request
     * @return true if player has available space, false otherwise
     */
    @Override
    protected boolean validate(PurchaseRequest request) {
        Player player = request.getPlayer();
        boolean hasSpace = player.getInventory().hasSpace();
        
        logValidation(request, hasSpace,
            "Inventory Space Check: Has " + 
            player.getInventory().getAvailableSlots() + 
            " slots available" + (hasSpace ? " Ã¢Å“â€œ" : " Ã¢Å“â€”"));
        
        return hasSpace;
    }
}


