package ui;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import model.*;
import patterns.behavioral.*;
import patterns.creational.ShopManager;

/**
 * Panel displaying event log for shop transactions.
 */
public class LogPanel extends JPanel implements ShopObserver {
    private JTextArea logTextArea;
    private List<String> events;
    private SimpleDateFormat dateFormat;
    
    /**
     * Creates the log panel.
     */
    public LogPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 220, 120), 2),
            "Event Log",
            0,
            0,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(100, 220, 220)
        ));
        setPreferredSize(new Dimension(0, 160));
        setBackground(new Color(25, 25, 30));
        
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setBackground(new Color(25, 25, 30));
        logTextArea.setForeground(new Color(120, 255, 140));
        logTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        events = new ArrayList<>();
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        
        addLog("System initialized. Welcome to the League of Legends Shop!");
    }
    
    /**
     * Adds a log message.
     * @param message The message to log
     */
    private void addLog(String message) {
        String timestamp = dateFormat.format(new Date());
        String logEntry = "[" + timestamp + "] " + message;
        events.add(logEntry);
        logTextArea.append(logEntry + "\n");
        
        // Auto-scroll to bottom
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }
    
    @Override
    public void onPlayerGoldChanged(Player player) {
        addLog("Gold changed -> " + String.format("%.0f", player.getGold()) + " gold");
    }
    
    @Override
    public void onInventoryChanged(Player player) {
        int backpackSize = player.getInventory().getBackpackItems().size();
        int equippedSize = player.getInventory().getEquippedItems().size();
        addLog("Inventory updated -> Backpack: " + backpackSize + ", Equipped: " + equippedSize);
    }
    
    @Override
    public void onShopInventoryChanged(ShopManager shop) {
        addLog("Shop inventory updated -> " + shop.getAvailableItems().size() + " items available");
    }
    
    @Override
    public void onItemEquipped(Item item, Player player) {
        addLog("Equipped: " + item.getName() + " [" + item.getRarity() + "]");
    }
    
    @Override
    public void onItemUnequipped(Item item, Player player) {
        addLog("Unequipped: " + item.getName() + " [" + item.getRarity() + "]");
    }
    
    /**
     * Gets all logged events.
     * @return List of event strings
     */
    public List<String> getEvents() {
        return new ArrayList<>(events);
    }
    
    /**
     * Clears the event log.
     */
    public void clearLog() {
        events.clear();
        logTextArea.setText("");
        addLog("Event log cleared.");
    }
}


