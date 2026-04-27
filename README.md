# ⚔️ Empire — Turn-Based Strategy Game

A turn-based empire-building strategy game built in **Java Swing**. Choose a starting city, construct buildings, recruit armies, march across the map, lay siege to enemy cities, and conquer the ancient world — all within 50 turns.

---

## 🎮 Gameplay Overview

1. **Start a campaign** — Enter your name and pick a starting city (Cairo, Rome, or Sparta).
2. **Manage your economy** — Build Farms and Markets to generate food and gold each turn.
3. **Raise armies** — Construct military buildings (Barracks, Archery Range, Stable) and recruit units.
4. **March & conquer** — Send armies toward enemy cities, lay siege, and fight turn-based battles.
5. **Win condition** — Control all three cities before turn 50.

### Resources

| Resource | Purpose |
|----------|---------|
| **Gold** | Used to construct/upgrade buildings and recruit units. |
| **Food** | Consumed each turn as army upkeep. Starvation causes attrition. |

### Cities

The game world contains three cities connected by roads:

- **Cairo**
- **Rome**
- **Sparta**

Each city has its own defending army and supports economic and military buildings.

---

## 🏗️ Buildings

### Economic Buildings

| Building | Cost | Produces |
|----------|------|----------|
| **Farm** | 1,000 | Food (500 / 700 / 1,000 by level) |
| **Market** | 1,500 | Gold (1,000 / 1,500 / 2,000 by level) |

### Military Buildings

| Building | Cost | Recruits |
|----------|------|----------|
| **Barracks** | 2,000 | Infantry |
| **Archery Range** | 1,500 | Archers |
| **Stable** | 2,500 | Cavalry |

All buildings start at **level 1** and can be upgraded to **level 3**. Buildings have a one-turn cooldown after construction or upgrade.

---

## ⚔️ Units

| Unit | Strengths | Weaknesses |
|------|-----------|------------|
| **Archer** | Strong vs. Archers | Weak vs. Cavalry |
| **Infantry** | Strong vs. Archers | Weak vs. other Infantry & Cavalry |
| **Cavalry** | Strong vs. Archers | Weak vs. other Cavalry |

Each unit type has three levels with increasing soldier counts and damage factors.

---

## 🏰 Combat

- **Manual battles** — Select your attacking unit, pick a defending target, and resolve attacks turn by turn.
- **Auto-resolve** — The game randomly pits attackers against defenders until one army is eliminated.
- **Siege** — Lay siege to a city for up to 3 turns (defenders take attrition damage). After 3 turns the battle must be resolved.

---

## 🔧 Save & Load

The game supports serialization-based save/load. Progress is saved to `saves/empire-save.dat` and can be resumed from the title screen.

---

## 📁 Project Structure

```
EMPIREFINAL/
├── resources/               # Game data (CSV files)
│   ├── distances.csv        # City-to-city distances
│   ├── cairo_army.csv       # Initial defending army for Cairo
│   ├── rome_army.csv        # Initial defending army for Rome
│   └── sparta_army.csv      # Initial defending army for Sparta
├── src/
│   ├── buildings/           # Building hierarchy
│   │   ├── Building.java          # Abstract base (cost, level, cooldown)
│   │   ├── EconomicBuilding.java  # Abstract — harvest()
│   │   ├── Farm.java
│   │   ├── Market.java
│   │   ├── MilitaryBuilding.java  # Abstract — recruit()
│   │   ├── ArcheryRange.java
│   │   ├── Barracks.java
│   │   └── Stable.java
│   ├── engine/              # Core game logic
│   │   ├── Game.java        # Game loop, turns, save/load
│   │   ├── Player.java      # Player actions (build, recruit, siege)
│   │   ├── City.java        # City state and structures
│   │   ├── Distance.java    # Distance between two cities
│   │   └── TurnSummary.java # End-of-turn report
│   ├── exceptions/          # Custom exception hierarchy
│   │   ├── EmpireException.java
│   │   ├── BuildingException.java
│   │   ├── ArmyException.java
│   │   └── ... (11 exception classes)
│   ├── units/               # Unit hierarchy
│   │   ├── Unit.java        # Abstract base (attack, damage)
│   │   ├── Archer.java
│   │   ├── Cavalry.java
│   │   ├── Infantry.java
│   │   ├── Army.java        # Collection of units
│   │   └── Status.java      # Enum: IDLE, MARCHING, BESIEGING
│   ├── view/                # Swing GUI (25 classes)
│   │   ├── Gui.java         # main() entry point
│   │   ├── Enter.java       # Title / new-game screen
│   │   ├── WorldMap.java    # Main game map view
│   │   ├── CityView.java    # City management screen
│   │   ├── BattleView.java  # Turn-based battle screen
│   │   ├── UITheme.java     # Shared look-and-feel utilities
│   │   └── ...
│   └── tests/
│       └── GameRulesTest.java
└── README.md
```

---

## 🚀 How to Build & Run

### Prerequisites

- **Java JDK 8** or higher

### Compile

```bash
javac -d bin -sourcepath src src/view/Gui.java
```

### Run

```bash
java -cp "bin;resources" view.Gui
```

> On Linux / macOS, use `:` instead of `;` as the classpath separator:
> ```bash
> java -cp "bin:resources" view.Gui
> ```

---

## 🧪 Running Tests

```bash
javac -d bin -sourcepath src src/tests/GameRulesTest.java
java -cp "bin;resources" tests.GameRulesTest
```

---

## 📝 License

This project was developed as a university course assignment.
