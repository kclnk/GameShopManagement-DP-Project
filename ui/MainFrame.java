package ui;

import java.awt.*;
import javax.swing.*;
import model.*;
import patterns.behavioral.*;
import patterns.structural.*;

/**
 * Main GUI window with tabbed interface for shop, inventory, and upgrades.
 */
public class MainFrame extends JFrame {
    private Player player;
    private ShoppingFacade facade;
    private ShopObservable observable;
    private CommandHistory cmdHistory;
    
    private JTabbedPane tabbedPane;
    private ShopPanel shopPanel;
    private InventoryPanel inventoryPanel;
    private UpgradesPanel upgradesPanel;
    private PlayerStatsPanel statsPanel;
    private LogPanel logPanel;
    
    public MainFrame(Player player, ShoppingFacade facade) {
        this.player = player;
        this.facade = facade;
        this.observable = new ShopObservable();
        this.cmdHistory = new CommandHistory();
        
        // Setup frame properties
        setTitle("League of Legends - Shop System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Create UI components
        initComponents();
        
        setVisible(true);
    }
    
    /**
     * Initializes all GUI components and layouts.
     */
    private void initComponents() {
        // Set main layout
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20));
        
        // Create top panel with player stats
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Create tabbed pane with 3 tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(30, 30, 30));
        tabbedPane.setForeground(new Color(0, 200, 200));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        shopPanel = new ShopPanel(player, facade, observable, cmdHistory);
        inventoryPanel = new InventoryPanel(player, facade, observable, cmdHistory);
        upgradesPanel = new UpgradesPanel(player, facade, observable, cmdHistory);
        
        tabbedPane.addTab("SHOP", shopPanel);
        tabbedPane.addTab("INVENTORY", inventoryPanel);
        tabbedPane.addTab("UPGRADES", upgradesPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Create bottom log panel
        logPanel = new LogPanel();
        add(logPanel, BorderLayout.SOUTH);
        
        // Register observers
        observable.registerObserver(shopPanel);
        observable.registerObserver(inventoryPanel);
        observable.registerObserver(upgradesPanel);
        observable.registerObserver(statsPanel);
        observable.registerObserver(logPanel);
    }
    
    /**
     * Creates the top panel showing player stats.
     * @return JPanel with player information
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 200, 200)));
        
        // Create stats panel with command history
        statsPanel = new PlayerStatsPanel(player, cmdHistory);
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Gets the shop observable for external observer registration.
     * @return The ShopObservable instance
     */
    public ShopObservable getObservable() {
        return observable;
    }
    
    /**
     * Gets the command history for undo/redo operations.
     * @return The CommandHistory instance
     */
    public CommandHistory getCommandHistory() {
        return cmdHistory;
    }
}


