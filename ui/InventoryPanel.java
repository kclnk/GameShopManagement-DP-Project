package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.*;
import patterns.behavioral.*;
import patterns.creational.ShopManager;
import patterns.structural.*;

/**
 * Panel showing player's inventory with equip/unequip functionality.
 */
public class InventoryPanel extends JPanel implements ShopObserver {
    private Player player;
    private ShoppingFacade facade;
    private ShopObservable observable;
    private CommandHistory cmdHistory;
    private JPanel equippedPanel;
    private JPanel backpackPanel;
    
    public InventoryPanel(Player player, ShoppingFacade facade, ShopObservable observable, CommandHistory cmdHistory) {
        this.player = player;
        this.facade = facade;
        this.observable = observable;
        this.cmdHistory = cmdHistory;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(35, 35, 40));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Equipped items section
        JLabel equippedTitle = new JLabel("EQUIPPED ITEMS");
        equippedTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        equippedTitle.setForeground(new Color(100, 220, 220));
        equippedTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        equippedPanel = new JPanel();
        equippedPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
        equippedPanel.setBackground(new Color(45, 45, 50));
        equippedPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 200, 200), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        equippedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        equippedPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        updateEquippedItems();
        
        // Backpack section
        JLabel backpackTitle = new JLabel("BACKPACK");
        backpackTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backpackTitle.setForeground(new Color(100, 220, 220));
        backpackTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        backpackPanel = new JPanel();
        backpackPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
        backpackPanel.setBackground(new Color(45, 45, 50));
        backpackPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 200, 200), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        backpackPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backpackPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        updateBackpackItems();
        
        add(equippedTitle);
        add(Box.createVerticalStrut(5));
        add(equippedPanel);
        add(Box.createVerticalStrut(20));
        add(backpackTitle);
        add(Box.createVerticalStrut(5));
        add(backpackPanel);
        add(Box.createVerticalGlue());
    }
    
    /**
     * Updates the equipped items display.
     */
    private void updateEquippedItems() {
        equippedPanel.removeAll();
        List<Item> equippedItems = player.getInventory().getEquippedItems();
        
        for (Item item : equippedItems) {
            JPanel itemPanel = createItemButton(item, true);
            equippedPanel.add(itemPanel);
        }
        
        if (equippedItems.isEmpty()) {
            JLabel emptyLabel = new JLabel("No items equipped");
            emptyLabel.setForeground(Color.GRAY);
            equippedPanel.add(emptyLabel);
        }
        
        equippedPanel.revalidate();
        equippedPanel.repaint();
    }
    
    /**
     * Updates the backpack items display.
     */
    private void updateBackpackItems() {
        backpackPanel.removeAll();
        List<Item> backpackItems = player.getInventory().getBackpackItems();
        
        for (Item item : backpackItems) {
            JPanel itemPanel = createItemButton(item, false);
            backpackPanel.add(itemPanel);
        }
        
        if (backpackItems.isEmpty()) {
            JLabel emptyLabel = new JLabel("Backpack empty");
            emptyLabel.setForeground(Color.GRAY);
            backpackPanel.add(emptyLabel);
        }
        
        backpackPanel.revalidate();
        backpackPanel.repaint();
    }
    
    /**
     * Creates a panel with button for an inventory item.
     * @param item The item
     * @param isEquipped Whether the item is currently equipped
     * @return JPanel containing item button
     */
    private JPanel createItemButton(Item item, boolean isEquipped) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(new Color(45, 45, 50));
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(getRarityColor(item.getRarity()).darker(), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Main item button (equip/unequip)
        JButton btn = new JButton("<html><center>" + item.getName() + 
                                   "<br><small>" + item.getRarity() + "</small></center></html>");
        btn.setBackground(getRarityColor(item.getRarity()));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Auto-adjust width based on text length
        FontMetrics fm = btn.getFontMetrics(btn.getFont());
        int textWidth = fm.stringWidth(item.getName());
        int buttonWidth = Math.max(110, textWidth + 45);
        btn.setPreferredSize(new Dimension(buttonWidth, 65));
        btn.setMaximumSize(new Dimension(buttonWidth, 65));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Add tooltip with stats
        btn.setToolTipText(formatStatsTooltip(item));
        
        // Unified mouse listener for all interactions
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(getRarityColor(item.getRarity()).brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(getRarityColor(item.getRarity()));
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(evt)) {
                    // Left-click: equip/unequip - check actual current state
                    boolean currentlyEquipped = player.getInventory().getEquippedItems().contains(item);
                    if (currentlyEquipped) {
                        UnequipCommand cmd = new UnequipCommand(player, item, observable);
                        cmdHistory.executeCommand(cmd);
                    } else {
                        EquipCommand cmd = new EquipCommand(player, item, observable);
                        cmdHistory.executeCommand(cmd);
                    }
                } else if (javax.swing.SwingUtilities.isRightMouseButton(evt)) {
                    // Right-click: sell instantly
                    Command sellCmd = new SellCommand(player, item, observable);
                    cmdHistory.executeCommand(sellCmd);
                }
            }
        });
        
        itemPanel.add(btn);
        
        return itemPanel;
    }
    
    /**
     * Gets the color for a rarity.
     * @param rarity The rarity string
     * @return Color for the rarity
     */
    private Color getRarityColor(String rarity) {
        switch(rarity.toUpperCase()) {
            case "COMMON":
                return new Color(120, 120, 120);
            case "UNCOMMON":
                return new Color(0, 180, 0);
            case "RARE":
                return new Color(0, 100, 255);
            case "EPIC":
                return new Color(160, 32, 240);
            case "LEGENDARY":
                return new Color(200, 180, 0);
            default:
                return Color.GRAY;
        }
    }
    
    /**
     * Formats item stats for tooltip.
     * @param item The item
     * @return Formatted stats string
     */
    private String formatStatsTooltip(Item item) {
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<b>").append(item.getName()).append("</b><br>");
        sb.append("Price: ").append(String.format("%.0f", item.getPrice())).append(" gold<br>");
        sb.append("<br>Stats:<br>");
        
        item.getStats().forEach((stat, value) -> {
            if (value != 0) {
                sb.append(stat).append(": +").append(value).append("<br>");
            }
        });
        
        sb.append("</html>");
        return sb.toString();
    }
    
    @Override
    public void onInventoryChanged(Player player) {
        updateEquippedItems();
        updateBackpackItems();
    }
    
    @Override
    public void onPlayerGoldChanged(Player player) {
        // Not directly relevant
    }
    
    @Override
    public void onShopInventoryChanged(ShopManager shop) {
        // Not directly relevant
    }
    
    @Override
    public void onItemEquipped(Item item, Player player) {
        updateEquippedItems();
        updateBackpackItems();
    }
    
    @Override
    public void onItemUnequipped(Item item, Player player) {
        updateEquippedItems();
        updateBackpackItems();
    }
}


