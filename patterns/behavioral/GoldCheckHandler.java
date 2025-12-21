package patterns.behavioral;

import model.Item;
import model.Player;

/**
 * GoldCheckHandler validates that the player has enough gold for the purchase.
 * First handler in the validation chain.
 */
public class GoldCheckHandler extends PurchaseHandler {
    
    /**
     * Validates that the player has sufficient gold.
     * @param request the purchase request
     * @return true if player has enough gold, false otherwise
     */
    @Override
    protected boolean validate(PurchaseRequest request) {
        Player player = request.getPlayer();
        Item item = request.getItem();
        double price = request.getPurchasePrice();
        
        boolean hasEnoughGold = player.getGold() >= price;
        
        logValidation(request, hasEnoughGold, 
            "Gold Check: Player has " + player.getGold() + 
            ", needs " + price + (hasEnoughGold ? " Ã¢Å“â€œ" : " Ã¢Å“â€”"));
        
        return hasEnoughGold;
    }
}


