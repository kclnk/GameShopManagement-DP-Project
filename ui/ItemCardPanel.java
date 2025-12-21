package ui;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import model.*;
import patterns.behavioral.*;
import patterns.structural.*;

/**
 * Single item card with name, price, stats, and buy button.
 */
public class ItemCardPanel extends JPanel {
    private Item item;
    private Player player;
    private ShoppingFacade facade;
    private ShopObservable observable;
    private CommandHistory cmdHistory;
    private JButton buyButton;
    
    /**
     * Creates an item card panel.
     * @param item The item to display
     * @param player The player shopping
     * @param facade Shopping facade for operations
     * @param observable Observable for notifications
     * @param cmdHistory Command history for undo/redo
     */
    public ItemCardPanel(Item item, Player player, ShoppingFacade facade, ShopObservable observable, CommandHistory cmdHistory) {
        this.item = item;
        this.player = player;
        this.facade = facade;
        this.observable = observable;
        this.cmdHistory = cmdHistory;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(getRarityColor(item), 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        setBackground(new Color(45, 45, 50));
        setOpaque(true);
        setPreferredSize(new Dimension(260, 190));
        setMaximumSize(new Dimension(260, 190));
        
        // Create card components
        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameLabel.setForeground(getRarityColor(item));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel priceLabel = new JLabel(String.format("%.0f", item.getPrice()) + " gold");
        priceLabel.setForeground(new Color(100, 220, 220));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel rarityLabel = new JLabel("[" + item.getRarity() + "]");
        rarityLabel.setForeground(getRarityColor(item));
        rarityLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        rarityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea statsArea = new JTextArea(formatStats(item));
        statsArea.setForeground(Color.LIGHT_GRAY);
        statsArea.setBackground(new Color(40, 40, 40));
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        statsArea.setEditable(false);
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);
        statsArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buyButton = new JButton("BUY");
        buyButton.setBackground(new Color(0, 200, 200));
        buyButton.setForeground(Color.BLACK);
        buyButton.setFocusPainted(false);
        buyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buyButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setMaximumSize(new Dimension(220, 35));
        buyButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 180, 180), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Add hover effect
        buyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (buyButton.isEnabled()) {
                    buyButton.setBackground(new Color(0, 220, 220));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (buyButton.isEnabled()) {
                    buyButton.setBackground(new Color(0, 200, 200));
                }
            }
        });
        
        buyButton.addActionListener(e -> onBuyClick());
        
        // Add components with spacing
        add(Box.createVerticalStrut(10));
        add(nameLabel);
        add(Box.createVerticalStrut(4));
        add(rarityLabel);
        add(Box.createVerticalStrut(8));
        add(priceLabel);
        add(Box.createVerticalStrut(10));
        add(statsArea);
        add(Box.createVerticalGlue());
        add(buyButton);
        add(Box.createVerticalStrut(10));
        
        updateButtonState();
    }
    
    /**
     * Handles buy button click.
     */
    private void onBuyClick() {
        // Validate purchase first
        ValidationChain chain = new ValidationChain();
        PurchaseRequest request = new PurchaseRequest(player, item);
        
        if (!chain.validate(request)) {
            JOptionPane.showMessageDialog(this,
                "Cannot purchase " + item.getName() + "!\n" +
                "Check: Sufficient gold, inventory space, level requirement.",
                "Purchase Failed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Execute buy command
        BuyCommand cmd = new BuyCommand(player, item, observable);
        cmdHistory.executeCommand(cmd);
        
        JOptionPane.showMessageDialog(this,
            "Successfully purchased " + item.getName() + "!",
            "Purchase Successful",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Updates button state based on player gold and inventory.
     */
    public void updateButtonState() {
        boolean canAfford = player.getGold() >= item.getPrice();
        boolean hasSpace = player.getInventory().hasSpace();
        
        if (!canAfford || !hasSpace) {
            buyButton.setEnabled(false);
            buyButton.setBackground(new Color(80, 80, 80));
            if (!canAfford) {
                buyButton.setText("TOO EXPENSIVE");
            } else {
                buyButton.setText("NO SPACE");
            }
        } else {
            buyButton.setEnabled(true);
            buyButton.setBackground(new Color(0, 200, 200));
            buyButton.setText("BUY");
        }
    }
    
    /**
     * Gets the color associated with item rarity.
     * @param item The item
     * @return Color for the rarity
     */
    private Color getRarityColor(Item item) {
        switch(item.getRarity().toUpperCase()) {
            case "COMMON":
                return Color.WHITE;
            case "UNCOMMON":
                return new Color(0, 255, 0);
            case "RARE":
                return new Color(0, 100, 255);
            case "EPIC":
                return new Color(160, 32, 240);
            case "LEGENDARY":
                return Color.YELLOW;
            default:
                return Color.GRAY;
        }
    }
    
    /**
     * Formats item stats for display.
     * @param item The item
     * @return Formatted stats string
     */
    private String formatStats(Item item) {
        Map<String, Integer> stats = item.getStats();
        StringBuilder sb = new StringBuilder();
        
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            if (entry.getValue() != 0) {
                sb.append("+").append(entry.getValue())
                  .append(" ").append(entry.getKey()).append("\n");
            }
        }
        
        return sb.toString().trim();
    }
}


