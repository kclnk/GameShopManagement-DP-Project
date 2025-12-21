package ui;

import java.awt.*;
import javax.swing.*;
import model.*;
import patterns.behavioral.*;
import patterns.creational.ShopManager;

/**
 * Panel displaying player statistics and undo/redo buttons.
 */
public class PlayerStatsPanel extends JPanel implements ShopObserver {
    private Player player;
    private CommandHistory cmdHistory;
    private JLabel nameLabel;
    private JLabel levelLabel;
    private JLabel goldLabel;
    private JLabel inventoryLabel;
    private JButton undoButton;
    
    public PlayerStatsPanel(Player player, CommandHistory cmdHistory) {
        this.player = player;
        this.cmdHistory = cmdHistory;
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(30, 30, 30));
        
        // Player name and level
        nameLabel = new JLabel("Player: " + player.getName() + " (Lvl " + player.getLevel() + ")");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameLabel.setForeground(new Color(100, 220, 220));
        
        // Gold display
        goldLabel = new JLabel("Gold: " + String.format("%.0f", player.getGold()));
        goldLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        goldLabel.setForeground(new Color(100, 220, 220));
        
        // Inventory status
        int backpackSize = player.getInventory().getBackpackItems().size();
        int equippedSize = player.getInventory().getEquippedItems().size();
        int totalItems = backpackSize + equippedSize;
        inventoryLabel = new JLabel("Inventory: " + totalItems + "/6");
        inventoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        inventoryLabel.setForeground(new Color(100, 220, 220));
        
        // Add components with separators
        add(nameLabel);
        add(createSeparator());
        add(goldLabel);
        add(createSeparator());
        add(inventoryLabel);
        add(createSeparator());
        
        // Undo button
        undoButton = new JButton("UNDO");
        undoButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        undoButton.setBackground(new Color(80, 80, 100));
        undoButton.setForeground(new Color(40, 40, 40));
        undoButton.setFocusPainted(false);
        undoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        undoButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 120), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        undoButton.addActionListener(e -> {
            if (cmdHistory.canUndo()) {
                cmdHistory.undo();
                updateButtonStates();
            }
        });
        undoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (undoButton.isEnabled()) {
                    undoButton.setBackground(new Color(100, 100, 120));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                undoButton.setBackground(new Color(80, 80, 100));
            }
        });
        
        add(undoButton);
        
        updateButtonStates();
    }
    
    /**
     * Updates the enabled state of undo/redo buttons.
     */
    private void updateButtonStates() {
        undoButton.setEnabled(cmdHistory.canUndo());
        
        if (!undoButton.isEnabled()) {
            undoButton.setBackground(new Color(50, 50, 60));
        } else {
            undoButton.setBackground(new Color(80, 80, 100));
        }
    }
    
    /**
     * Creates a vertical separator.
     * @return JSeparator component
     */
    private JComponent createSeparator() {
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(2, 20));
        sep.setForeground(new Color(0, 200, 200));
        return sep;
    }
    
    @Override
    public void onPlayerGoldChanged(Player player) {
        goldLabel.setText("Gold: " + String.format("%.0f", player.getGold()));
        updateButtonStates();
    }
    
    @Override
    public void onInventoryChanged(Player player) {
        int backpackSize = player.getInventory().getBackpackItems().size();
        int equippedSize = player.getInventory().getEquippedItems().size();
        int totalItems = backpackSize + equippedSize;
        inventoryLabel.setText("Inventory: " + totalItems + "/6");
        updateButtonStates();
    }
    
    @Override
    public void onShopInventoryChanged(ShopManager shop) {
        // Not directly relevant to player stats
    }
    
    @Override
    public void onItemEquipped(Item item, Player player) {
        // Inventory count will be updated via onInventoryChanged
    }
    
    @Override
    public void onItemUnequipped(Item item, Player player) {
        // Inventory count will be updated via onInventoryChanged
    }
}


