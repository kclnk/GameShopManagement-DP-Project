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
 * Panel for upgrading items with decorators.
 * Allows selecting items and applying various enhancements.
 */
public class UpgradesPanel extends JPanel implements ShopObserver {
    private Player player;
    private ShoppingFacade facade;
    private ShopObservable observable;
    private CommandHistory cmdHistory;
    
    private JComboBox<ItemWrapper> itemSelector;
    private JCheckBox attackBoostCB;
    private JCheckBox defenseBoostCB;
    private JCheckBox healthBoostCB;
    private JComboBox<String> elementalCombo;
    private JButton upgradeButton;
    private JLabel costLabel;
    
    /**
     * Wrapper class to display items in combo box.
     */
    private static class ItemWrapper {
        Item item;
        ItemWrapper(Item item) { this.item = item; }
        @Override
        public String toString() { 
            return item != null ? item.getName() + " [" + item.getRarity() + "]" : "No items"; 
        }
    }
    
    /**
     * Creates the upgrades panel.
     * @param player The player
     * @param facade Shopping facade for operations
     * @param observable Observable for notifications
     * @param cmdHistory Command history for undo/redo
     */
    public UpgradesPanel(Player player, ShoppingFacade facade, ShopObservable observable, CommandHistory cmdHistory) {
        this.player = player;
        this.facade = facade;
        this.observable = observable;
        this.cmdHistory = cmdHistory;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(new Color(35, 35, 40));
        
        // Title
        JLabel titleLabel = new JLabel("ITEM UPGRADES");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(100, 220, 220));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Item selector
        JLabel selectLabel = new JLabel("Select item to upgrade:");
        selectLabel.setForeground(new Color(100, 220, 220));
        selectLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        selectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        itemSelector = new JComboBox<>();
        itemSelector.setMaximumSize(new Dimension(400, 30));
        itemSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
        itemSelector.setBackground(new Color(200, 200, 200));
        itemSelector.setForeground(Color.BLACK);
        updateItemList();
        itemSelector.addActionListener(e -> updateUpgradeOptions());
        
        // Upgrade options
        JLabel upgradesLabel = new JLabel("Available upgrades:");
        upgradesLabel.setForeground(new Color(0, 200, 200));
        upgradesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        upgradesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        attackBoostCB = new JCheckBox("Attack Boost (+15 ATK, 300 gold)");
        attackBoostCB.setBackground(new Color(35, 35, 40));
        attackBoostCB.setForeground(new Color(220, 220, 220));
        attackBoostCB.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        attackBoostCB.setAlignmentX(Component.LEFT_ALIGNMENT);
        attackBoostCB.addActionListener(e -> updateUpgradeOptions());
        
        defenseBoostCB = new JCheckBox("Defense Boost (+20 DEF, 400 gold)");
        defenseBoostCB.setBackground(new Color(35, 35, 40));
        defenseBoostCB.setForeground(new Color(220, 220, 220));
        defenseBoostCB.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        defenseBoostCB.setAlignmentX(Component.LEFT_ALIGNMENT);
        defenseBoostCB.addActionListener(e -> updateUpgradeOptions());
        
        healthBoostCB = new JCheckBox("Health Boost (+50 HP, 200 gold)");
        healthBoostCB.setBackground(new Color(35, 35, 40));
        healthBoostCB.setForeground(new Color(220, 220, 220));
        healthBoostCB.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        healthBoostCB.setAlignmentX(Component.LEFT_ALIGNMENT);
        healthBoostCB.addActionListener(e -> updateUpgradeOptions());
        
        // Note: Elemental enchantments temporarily disabled
        JLabel elementalLabel = new JLabel("(Elemental enchantments coming soon!)");
        elementalLabel.setForeground(Color.GRAY);
        elementalLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        elementalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        /*
        elementalCombo = new JComboBox<>(new String[]{"None", "Fire", "Ice", "Lightning"});
        elementalCombo.setMaximumSize(new Dimension(200, 30));
        elementalCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        elementalCombo.setBackground(new Color(60, 60, 60));
        elementalCombo.setForeground(Color.BLACK);
        elementalCombo.addActionListener(e -> updateUpgradeOptions());
        */
        
        // Cost and upgrade button
        costLabel = new JLabel("Total cost: 0 gold");
        costLabel.setForeground(new Color(100, 220, 220));
        costLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        costLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        upgradeButton = new JButton("UPGRADE");
        upgradeButton.setBackground(new Color(0, 200, 200));
        upgradeButton.setForeground(Color.BLACK);
        upgradeButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        upgradeButton.setFocusPainted(false);
        upgradeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        upgradeButton.setMaximumSize(new Dimension(220, 45));
        upgradeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        upgradeButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 180, 180), 2),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        
        // Add hover effect
        upgradeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (upgradeButton.isEnabled()) {
                    upgradeButton.setBackground(new Color(0, 220, 220));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (upgradeButton.isEnabled()) {
                    upgradeButton.setBackground(new Color(0, 200, 200));
                }
            }
        });
        
        upgradeButton.addActionListener(e -> onUpgradeClick());
        
        // Add all components
        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(selectLabel);
        add(Box.createVerticalStrut(5));
        add(itemSelector);
        add(Box.createVerticalStrut(20));
        add(upgradesLabel);
        add(Box.createVerticalStrut(10));
        add(attackBoostCB);
        add(Box.createVerticalStrut(5));
        add(defenseBoostCB);
        add(Box.createVerticalStrut(5));
        add(healthBoostCB);
        add(Box.createVerticalStrut(15));
        add(elementalLabel);
        // add(elementalCombo); // Disabled for now
        add(Box.createVerticalStrut(20));
        add(costLabel);
        add(Box.createVerticalStrut(10));
        add(upgradeButton);
        add(Box.createVerticalGlue());
        
        updateUpgradeOptions();
    }
    
    /**
     * Updates the item selector with current inventory.
     */
    private void updateItemList() {
        itemSelector.removeAllItems();
        
        List<Item> backpackItems = player.getInventory().getBackpackItems();
        List<Item> equippedItems = player.getInventory().getEquippedItems();
        
        for (Item item : backpackItems) {
            itemSelector.addItem(new ItemWrapper(item));
        }
        for (Item item : equippedItems) {
            itemSelector.addItem(new ItemWrapper(item));
        }
        
        if (itemSelector.getItemCount() == 0) {
            itemSelector.addItem(new ItemWrapper(null));
        }
    }
    
    /**
     * Updates upgrade options and cost display.
     */
    private void updateUpgradeOptions() {
        // Calculate total cost
        double cost = 0;
        if (attackBoostCB.isSelected()) cost += 300;
        if (defenseBoostCB.isSelected()) cost += 400;
        if (healthBoostCB.isSelected()) cost += 200;
        // if (!elementalCombo.getSelectedItem().equals("None")) cost += 500; // Disabled
        
        costLabel.setText("Total cost: " + String.format("%.0f", cost) + " gold");
        
        // Update button state
        ItemWrapper wrapper = (ItemWrapper) itemSelector.getSelectedItem();
        boolean hasItem = wrapper != null && wrapper.item != null;
        boolean hasUpgrades = attackBoostCB.isSelected() || defenseBoostCB.isSelected() || 
                              healthBoostCB.isSelected(); // || !elementalCombo.getSelectedItem().equals("None");
        
        if (!hasItem) {
            upgradeButton.setEnabled(false);
            upgradeButton.setText("NO ITEM SELECTED");
            upgradeButton.setBackground(new Color(80, 80, 80));
        } else if (!hasUpgrades) {
            upgradeButton.setEnabled(false);
            upgradeButton.setText("SELECT UPGRADES");
            upgradeButton.setBackground(new Color(80, 80, 80));
        } else if (cost > player.getGold()) {
            upgradeButton.setEnabled(false);
            upgradeButton.setText("INSUFFICIENT GOLD");
            upgradeButton.setBackground(new Color(150, 0, 0));
        } else {
            upgradeButton.setEnabled(true);
            upgradeButton.setText("UPGRADE");
            upgradeButton.setBackground(new Color(0, 200, 200));
        }
    }
    
    /**
     * Handles upgrade button click.
     */
    private void onUpgradeClick() {
        ItemWrapper wrapper = (ItemWrapper) itemSelector.getSelectedItem();
        if (wrapper == null || wrapper.item == null) return;
        
        Item selectedItem = wrapper.item;
        ItemDecorator currentDecorator = null;
        double totalCost = 0;
        
        // Apply selected decorators (building chain)
        if (attackBoostCB.isSelected()) {
            if (currentDecorator == null) {
                currentDecorator = new AttackBoostDecorator(selectedItem, 15);
            } else {
                currentDecorator = new AttackBoostDecorator(currentDecorator, 15);
            }
            totalCost += 300;
        }
        
        if (defenseBoostCB.isSelected()) {
            if (currentDecorator == null) {
                currentDecorator = new DefenseBoostDecorator(selectedItem, 20);
            } else {
                currentDecorator = new DefenseBoostDecorator(currentDecorator, 20);
            }
            totalCost += 400;
        }
        
        if (healthBoostCB.isSelected()) {
            if (currentDecorator == null) {
                currentDecorator = new HealthBoostDecorator(selectedItem, 50);
            } else {
                currentDecorator = new HealthBoostDecorator(currentDecorator, 50);
            }
            totalCost += 200;
        }
        
        // No decorator selected
        if (currentDecorator == null) {
            JOptionPane.showMessageDialog(this,
                "Please select at least one upgrade!",
                "No Upgrade Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verify gold
        if (totalCost > player.getGold()) {
            JOptionPane.showMessageDialog(this,
                "Insufficient gold!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        // elementalCombo.setSelectedIndex(0); // Disabled
        }
        
        // Execute upgrade command
        UpgradeCommand cmd = new UpgradeCommand(player, selectedItem, currentDecorator, totalCost, observable);
        cmdHistory.executeCommand(cmd);
        
        // Reset selections
        attackBoostCB.setSelected(false);
        defenseBoostCB.setSelected(false);
        healthBoostCB.setSelected(false);
        // elementalCombo.setSelectedIndex(0); // Disabled - not initialized
        
        updateItemList();
        updateUpgradeOptions();
        
        JOptionPane.showMessageDialog(this,
            "Upgrade successful!\n" +
            "Item upgraded: " + selectedItem.getName() + "\n" +
            "Cost: " + String.format("%.0f", totalCost) + " gold",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void onInventoryChanged(Player player) {
        updateItemList();
        updateUpgradeOptions();
    }
    
    @Override
    public void onPlayerGoldChanged(Player player) {
        updateUpgradeOptions();
    }
    
    @Override
    public void onShopInventoryChanged(ShopManager shop) {
        // Not directly relevant
    }
    
    @Override
    public void onItemEquipped(Item item, Player player) {
        updateItemList();
    }
    
    @Override
    public void onItemUnequipped(Item item, Player player) {
        updateItemList();
    }
}


