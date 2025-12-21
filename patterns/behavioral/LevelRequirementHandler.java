package patterns.behavioral;

import model.Item;
import model.Player;

/**
 * LevelRequirementHandler validates that the player meets the level requirement.
 * Third handler in the validation chain.
 * 
 * Note: Currently all items require level 1 (default).
 * Future enhancement: Add getLevelRequirement() to Item interface.
 */
public class LevelRequirementHandler extends PurchaseHandler {
    
    /**
     * Validates that the player meets the level requirement.
     * @param request the purchase request
     * @return true if player meets level requirement, false otherwise
     */
    @Override
    protected boolean validate(PurchaseRequest request) {
        Player player = request.getPlayer();
        Item item = request.getItem();
        
        // For now, assume all items require level 1 (always passes)
        // In future, items can have getLevelRequirement() method
        int playerLevel = player.getLevel();
        int itemLevelReq = 1; // Default requirement
        
        boolean meetsLevel = playerLevel >= itemLevelReq;
        
        logValidation(request, meetsLevel,
            "Level Check: Player level " + playerLevel + 
            ", item requires " + itemLevelReq + 
            (meetsLevel ? " Ã¢Å“â€œ" : " Ã¢Å“â€”"));
        
        return meetsLevel;
    }
}


