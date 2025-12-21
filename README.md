# League of Legends Shop Manager
## Software Design Patterns Project

## 👥 Team Members

1. **Andrew Wageh - 192100099**
2. **Kirolos Mourice - 192100132**
3. **Mostafa Abdallah - 192100058**
4. **Sherif Diaa - 192100037**
5. **Ahmed Galal - 192200374**
6. **Omar Atwa - 192100141**
7. **Mohamed Saad - 192100137**
8. **Mohamed Safwat - 192100140**

---

## 📋 Project Overview

We used 8 Design pattern methods in our project, The system features a complete GUI where players can buy/sell items, upgrade equipment, manage inventory, and undo operations with full pattern integration.

**Design Patterns Implemented:**
- 3 Creational Patterns
- 2 Structural Patterns
- 3 Behavioral Patterns

---

## 🎯 Individual Contributions

### 1️⃣ Andrew Wageh - **Singleton Pattern**

**Pattern Implemented:** Singleton Pattern  
**Files Created:** `patterns/creational/ShopManager.java`

**Contribution:**
- Implemented the Lazy Singleton pattern to ensure only one shop instance exists throughout the application
- Created private constructor to prevent external instantiation
- Implemented `getInstance()` method for global access point
- Initialized shop with default items in the private constructor

**Problem Solved:**
- Ensured all players access the same shop inventory
- Prevented multiple shop instances that could cause data inconsistency
- Provided centralized shop management

**Lazy Approach:**
```java
private static ShopManager instance = null;
private ShopManager() { /* Initialize shop */ }
public static ShopManager getInstance() {
    if (instance == null) {
        instance = new ShopManager();
    }
    return instance;
}
```

**References Used:**
- Creational Pattern - Singleton Pattern lecture (2)
---

### 2️⃣ Kirolos Mourice - **Factory Method Pattern**

**Pattern Implemented:** Factory Method Pattern  
**Files Created:** `patterns/creational/ItemFactory.java`, `model/Weapon.java`, `model/Armor.java`, `model/Potion.java`, `model/Trinket.java`

**Contribution:**
- Designed and implemented the Factory Method pattern for creating different item types
- Created separate methods for each item type (Weapon, Armor, Potion, Trinket)
- Implemented main `createItem()` method with type-based routing using switch statement
- Defined item model classes with appropriate stats and properties

**Problem Solved:**
- Centralized object creation logic
- Eliminated need for clients to know concrete item class names
- Made it easy to add new item types without modifying client code

**Implementation Approach:**
```java
public static Item createItem(String type, String name, double price, int value) {
    switch (type.toUpperCase()) {
        case "WEAPON": return createWeapon(name, price, value);
        case "ARMOR": return createArmor(name, price, value);
        case "POTION": return createPotion(name, price, value);
        case "TRINKET": return createTrinket(name, price, value);
    }
}
```

**References Used:**
- Creational Patterns - Factory Method lecture (3) 
- Stack Overflow for switch statement best practices
---

### 3️⃣ Mostafa Abdallah - **Builder Pattern**

**Pattern Implemented:** Builder Pattern  
**Files Created:** `patterns/creational/ItemBuilder.java`, `model/CustomItem.java`

**Contribution:**
- Implemented the Builder pattern for constructing complex items with multiple optional parameters
- Created fluent interface with method chaining
- Implemented `build()` method to create final CustomItem object
- Added validation for item properties (price >= 0, non-null names, valid rarity)

**Problem Solved:**
- Avoided telescoping constructors with many parameters
- Made item creation code more readable and maintainable
- Allowed flexible construction of items with varying numbers of stats and effects

**Implementation Approach:**
```java
public ItemBuilder setName(String name) { this.name = name; return this; }
public ItemBuilder setPrice(double price) { this.price = price; return this; }
public ItemBuilder addStat(String stat, int value) { this.stats.put(stat, value); return this; }
public Item build() { return new CustomItem(...); }
```

**References Used:**
- Creational Patterns - Builder lecture (5) 
---

### 4️⃣ Sherif Diaa - **Decorator Pattern**

**Pattern Implemented:** Decorator Pattern  
**Files Created:** `patterns/structural/ItemDecorator.java`, `AttackBoostDecorator.java`, `DefenseBoostDecorator.java`, `HealthBoostDecorator.java`, `ElementalDamageDecorator.java`

**Contribution:**
- Designed and implemented the Decorator pattern for dynamically adding upgrades to items
- Created abstract `ItemDecorator` base class that wraps Item interface
- Implemented four concrete decorators for different stat boosts
- Modified `getStats()`, `getName()`, `getPrice()` methods to add enhancements
- Integrated with UpgradesPanel for GUI functionality

**Problem Solved:**
- Enabled dynamic addition of item upgrades without modifying original item classes
- Allowed stacking multiple decorators for compound upgrades
- Kept upgrade logic separate from core item functionality

**Implementation Approach:**
```java
public class AttackBoostDecorator extends ItemDecorator {
    private final int attackBonus;
    public AttackBoostDecorator(Item item, int bonus) {
        super(item);
        this.attackBonus = bonus;
    }
    @Override
    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>(wrappedItem.getStats());
        stats.put("Attack", stats.getOrDefault("Attack", 0) + attackBonus);
        return stats;
    }
}
```

**References Used:**
- Design Patterns in Java Tutorial (TutorialsPoint)
- Refactoring.Guru - Decorator Pattern
- ChatGPT for clarifying decorator stacking behavior

---

### 5️⃣ Ahmed Galal - **Facade Pattern**

**Pattern Implemented:** Facade Pattern  
**Files Created:** `patterns/structural/ShoppingFacade.java`

**Contribution:**
- Implemented the Facade pattern to simplify complex shopping operations
- Created unified interface methods: `buyItem()`, `sellItem()`, `equipItem()`, `unequipItem()`
- Coordinated interactions between Player, Inventory, and ShopManager
- Handled error checking and validation in single methods
- Integrated facade with all UI panels for consistent operation handling

**Problem Solved:**
- Reduced coupling between UI and multiple subsystems
- Simplified client code - one method call instead of 5-6 steps
- Centralized business logic for shopping operations
- Made code easier to maintain and test

**Implementation Approach:**
```java
public boolean buyItem(Item item) {
    // Step 1: Validate availability
    // Step 2: Check gold
    // Step 3: Check inventory space
    // Step 4-6: Execute purchase
    // All in one method call!
}
```

**References Used:**
- Design Patterns: Elements of Reusable Object-Oriented Software (GoF)
- GeeksforGeeks - Facade Design Pattern
- YouTube tutorial: "Facade Pattern Explained"

---

### 6️⃣ Omar Atwa - **Command Pattern**

**Pattern Implemented:** Command Pattern  
**Files Created:** `patterns/behavioral/Command.java`, `BuyCommand.java`, `SellCommand.java`, `EquipCommand.java`, `UnequipCommand.java`, `UpgradeCommand.java`, `CommandHistory.java`

**Contribution:**
- Designed and implemented the Command pattern for encapsulating actions
- Created Command interface with `execute()` and `undo()` methods
- Implemented five concrete command classes for all user actions
- Built CommandHistory class with stack-based undo/redo functionality
- Integrated commands with all UI buttons and panels
- Added Observer notifications within commands for UI updates

**Problem Solved:**
- Enabled undo/redo functionality for all player actions
- Separated action invocation from action execution
- Made it easy to log, queue, or replay commands
- Provided consistent transaction-like behavior with rollback

**Implementation Approach:**
```java
public class BuyCommand implements Command {
    public void execute() {
        player.removeGold(goldSpent);
        player.addItem(item);
        observable.notifyGoldChanged(player);
    }
    public void undo() {
        player.addGold(goldSpent);
        player.getInventory().removeItem(item);
        observable.notifyGoldChanged(player);
    }
}
```

**References Used:**
- Behavioral Patterns - Command Method lecture (10) 

---

### 7️⃣ Mohamed Saad - **Chain of Responsibility Pattern**

**Pattern Implemented:** Chain of Responsibility Pattern  
**Files Created:** `patterns/behavioral/PurchaseHandler.java`, `GoldCheckHandler.java`, `InventorySpaceHandler.java`, `LevelRequirementHandler.java`, `ItemAvailabilityHandler.java`, `ValidationChain.java`, `PurchaseRequest.java`

**Contribution:**
- Implemented Chain of Responsibility pattern for purchase validation
- Created abstract `PurchaseHandler` base class with `setNext()` and `handle()` methods
- Developed four concrete handlers for different validation checks
- Built `ValidationChain` class to configure and manage the handler chain
- Created `PurchaseRequest` class to encapsulate validation data
- Integrated chain with ItemCardPanel for pre-purchase validation

**Problem Solved:**
- Separated validation logic into independent, reusable handlers
- Made validation pipeline flexible and extensible
- Allowed adding/removing validators without changing client code
- Provided clear validation failure messages at each step

**Implementation Approach:**
```java
// Chain setup
goldCheck.setNext(inventorySpace)
         .setNext(levelReq)
         .setNext(availability);

// Each handler validates and passes to next
public boolean handle(PurchaseRequest request) {
    if (validate(request)) {
        return nextHandler != null ? nextHandler.handle(request) : true;
    }
    return false;
}
```

**References Used:**
- Behavioral Patterns -  Chain of Responsibility lecture (9)
---

### 8️⃣ Mohamed Safwat - **Observer Pattern**

**Pattern Implemented:** Observer Pattern  
**Files Created:** `patterns/behavioral/ShopObserver.java`, `ShopObservable.java`

**Contribution:**
- Designed and implemented the Observer pattern for UI synchronization
- Created `ShopObserver` interface with methods for different event types
- Implemented `ShopObservable` class to manage observers and notifications
- Added observer registration in MainFrame for all UI panels
- Implemented observer methods in all five UI panels (ShopPanel, InventoryPanel, UpgradesPanel, PlayerStatsPanel, LogPanel)
- Integrated observer notifications into Command classes and Facade operations

**Problem Solved:**
- Achieved automatic UI updates when game state changes
- Decoupled UI components from business logic
- Eliminated manual refresh calls throughout codebase
- Ensured consistency across all UI panels

**Implementation Approach:**
```java
// Observable notifies all observers
public void notifyGoldChanged(Player player) {
    for (ShopObserver observer : observers) {
        observer.onPlayerGoldChanged(player);
    }
}

// UI panels implement and respond
@Override
public void onPlayerGoldChanged(Player player) {
    goldLabel.setText("Gold: " + player.getGold());
}
```

**References Used:**
- Design Patterns: Elements of Reusable Object-Oriented Software (GoF book)
- Java Observer Pattern Tutorial (JavaTPoint)
- Refactoring.Guru - Observer Pattern
- YouTube: "Observer Design Pattern Explained"
- GitHub Copilot for Java Swing integration with Observer

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────┐
│                  GUI Layer                      │
│  (MainFrame, ShopPanel, InventoryPanel, etc.)   │
│            Implements ShopObserver              │
└─────────────────────┬───────────────────────────┘
                      │
                      ↓
┌─────────────────────────────────────────────────┐
│              Facade Layer                       │
│             (ShoppingFacade)                    │
│       Simplifies complex operations             │
└─────────────────┬───────────────────────────────┘
                  │
     ┌────────────┼──────────────┐
     ↓            ↓              ↓
┌──────────┐ ┌──────────┐ ┌─────────────┐
│Behavioral│ │Structural│ │  Creational │
│ Patterns │ │ Patterns │ │  Patterns   │
└──────────┘ └──────────┘ └─────────────┘
```

---

## 🎨 Design Patterns Summary

| Pattern | Category | Files | Purpose |
|---------|----------|-------|---------|
| Singleton | Creational | ShopManager.java | Single shop instance |
| Factory Method | Creational | ItemFactory.java | Item creation |
| Builder | Creational | ItemBuilder.java | Complex item construction |
| Decorator | Structural | ItemDecorator.java + 4 decorators | Dynamic upgrades |
| Facade | Structural | ShoppingFacade.java | Simplified operations |
| Command | Behavioral | Command.java + 5 commands | Undo/redo support |
| Chain of Responsibility | Behavioral | PurchaseHandler.java + 4 handlers | Validation chain |
| Observer | Behavioral | ShopObserver.java, ShopObservable.java | UI synchronization |

---

## 🚀 How to Run

1. **Run the application:**
   ```bash
   java -cp bin GUILauncher
   ```
---

## 📚 References & Learning Resources

### Primary References:
1. **Course Lectures** - Dr. [Hossam Hawash]'s Software Design Patterns lectures

### Online Resources:
- **Refactoring.Guru** - Design Patterns (https://refactoring.guru/design-patterns)
- **SourceMaking** - Design Patterns Tutorial (https://sourcemaking.com/design_patterns)
- **Baeldung** - Java Design Patterns (https://www.baeldung.com/design-patterns-series)
- **GeeksforGeeks** - Software Design Patterns

### AI Assistance:
- **ChatGPT** - Used for clarifying pattern concepts, debugging, and best practices
- **GitHub Copilot** - Code completion and syntax suggestions

### Video Tutorials:
- **"YOUTUBE** by Christopher Okhravi & Geekific & Hello Byte

---

## 🎯 Key Features

✅ **8 Design Patterns** fully implemented and integrated  
✅ **Complete GUI** with 3 tabs (Shop, Inventory, Upgrades)  
✅ **Undo/Redo** functionality for all actions  
✅ **Real-time UI updates** using Observer pattern  
✅ **Validation chain** for purchase safety  
✅ **Item upgrades** with stackable decorators  
✅ **Clean architecture** with separation of concerns  
✅ **Extensible design** - easy to add new features  
---

**Course:** Software Design Patterns (SET-412)

**Institution:** [Egyptian Chinese University]  
**Semester:** [Semester 9]  

---

