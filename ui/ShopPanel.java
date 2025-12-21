package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.*;
import patterns.behavioral.*;
import patterns.creational.ShopManager;
import patterns.structural.*;

/**
 * Panel displaying available shop items.
 */
public class ShopPanel extends JPanel implements ShopObserver {
    private Player player;
    private ShoppingFacade facade;
    private ShopObservable observable;
    private CommandHistory cmdHistory;
    private JPanel itemsGridPanel;
    private List<ItemCardPanel> itemCards;
    
    public ShopPanel(Player player, ShoppingFacade facade, ShopObservable observable, CommandHistory cmdHistory) {
        this.player = player;
        this.facade = facade;
        this.observable = observable;
        this.cmdHistory = cmdHistory;
        
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 200, 200), 2),
            "Available Items",
            0,
            0,
            new Font("Arial", Font.BOLD, 16),
            new Color(0, 200, 200)
        ));
        
        // Create scroll pane with grid
        itemsGridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        itemsGridPanel.setBackground(new Color(35, 35, 40));
        itemsGridPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        itemCards = new ArrayList<>();
        
        // Get items from shop and create cards
        ShopManager shop = ShopManager.getInstance();
        for (Item item : shop.getAvailableItems()) {
            ItemCardPanel card = new ItemCardPanel(item, player, facade, observable, cmdHistory);
            itemCards.add(card);
            itemsGridPanel.add(card);
        }
        
        JScrollPane scrollPane = new JScrollPane(itemsGridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    @Override
    public void onPlayerGoldChanged(Player player) {
        // Update card availability based on gold
        for (ItemCardPanel card : itemCards) {
            card.updateButtonState();
        }
    }
    
    @Override
    public void onInventoryChanged(Player player) {
        // Update card availability based on inventory space
        for (ItemCardPanel card : itemCards) {
            card.updateButtonState();
        }
    }
    
    @Override
    public void onShopInventoryChanged(ShopManager shop) {
        // Refresh all item cards
        itemsGridPanel.removeAll();
        itemCards.clear();
        
        for (Item item : shop.getAvailableItems()) {
            ItemCardPanel card = new ItemCardPanel(item, player, facade, observable, cmdHistory);
            itemCards.add(card);
            itemsGridPanel.add(card);
        }
        
        itemsGridPanel.revalidate();
        itemsGridPanel.repaint();
    }
    
    @Override
    public void onItemEquipped(Item item, Player player) {
        // Not directly relevant to shop panel
    }
    
    @Override
    public void onItemUnequipped(Item item, Player player) {
        // Not directly relevant to shop panel
    }
}


