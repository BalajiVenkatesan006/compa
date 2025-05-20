# 🏰 Dungeon Crawler RPG

A classic turn-based dungeon crawler with modern Java architecture, featuring immutable state and strategy patterns.

## 🎮 Features

- 🔥 Turn-based combat with critical hits
- 🗺️ 5x5 grid dungeon exploration
- 📈 RPG progression system (XP, levels, stats)
- 💾 Save/Load game functionality
- 🎨 Colorful console interface
- 🧱 Clean architecture with design patterns

## ⚙️ How to Build & Run

### Prerequisites
- Java 21+
- Maven 3.8+

```bash
# Clone the repository
git clone https://github.com/yourusername/dungeon-crawler.git

# Build the project
mvn clean package

# Run the game
java -jar target/dungeon-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar

# Run tests
mvn test
```


# Game Flow

1) Create your character by entering a name

2) Explore the dungeon room by room

3) Battle monsters (30% encounter chance when moving)

4) Gain XP and level up your character

5) Save your progress before quitting


## 🕹️ How to Play

### Basic Controls
| Command | Action                |
|---------|-----------------------|
| N       | Move North            |
| S       | Move South            |
| E       | Move East             |
| W       | Move West             |
| Q       | Quit Game             |
| 1       | Attack (in combat)    |
| 2       | Defend (in combat)    |
| 3       | Flee (in combat)      |

> Type the letter and press Enter to execute commands

