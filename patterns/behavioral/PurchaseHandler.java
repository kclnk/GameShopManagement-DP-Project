package patterns.behavioral;

/**
 * Base class for purchase validation handlers.
 */
public abstract class PurchaseHandler {
    protected PurchaseHandler nextHandler;
    
    public PurchaseHandler setNext(PurchaseHandler next) {
        this.nextHandler = next;
        return next; // Allows chaining
    }
    
    public boolean handle(PurchaseRequest request) {
        // Validate at this handler
        if (validate(request)) {
            // Validation passed at this level
            if (nextHandler != null) {
                // Pass to next handler in chain
                return nextHandler.handle(request);
            }
            // No more handlers, all validations passed
            return true;
        }
        // Validation failed at this handler
        return false;
    }
    
    /**
     * Validates the purchase request according to this handler's specific logic.
     * Subclasses must implement this method.
     * @param request the purchase request to validate
     * @return true if validation passes, false otherwise
     */
    protected abstract boolean validate(PurchaseRequest request);
    
    /**
     * Helper method to log validation results.
     * @param request the purchase request
     * @param passed whether validation passed
     * @param reason the reason/description
     */
    protected void logValidation(PurchaseRequest request, boolean passed, String reason) {
        System.out.println("[" + this.getClass().getSimpleName() + "] " + 
                          (passed ? "Ã¢Å“â€œ" : "Ã¢Å“â€”") + " " + reason);
    }
}


