# simpleSim

A comprehensive **sports simulation engine** built in Java that models realistic football/soccer competitions across multiple continental leagues, national teams, and international tournaments. Simulate entire seasons with dynamic player transfers, injuries, performance tracking, and competitive balance.

## Overview

simpleSim is a feature-rich simulation framework that recreates football competitions with sophisticated mechanics for:

- **Club Management**: Track squads, player development, transfers, and budgets
- **League Play**: Full domestic league simulations with realistic standings and fixtures
- **International Competitions**: Continental cups and World Cup-style tournaments
- **Player Systems**: Individual player tracking, injuries, career progression, and market value
- **Competitive Balance**: Stochastic match simulation with team strength and form considerations
- **Market Dynamics**: Transfer market, player offers, and team recruitment strategies

## Project Structure

### Core Classes

| Class | Purpose |
|-------|---------|
| **Sim.java** | Main simulation engine; manages all entities (leagues, clubs, players, nations) and coordinates season progression |
| **Club_Team.java** | Represents a football club with squad management, budget, recruitment levels, and youth academy system |
| **NationalTeam.java** | Models international teams with squad composition and performance tracking |
| **Player.java** | Individual player entity with attributes, position, contract status, and career history |
| **Squad.java** | Manages team rosters with position-based player organization and emergency promotions |
| **Match.java** | Represents individual matches with team lineups and result tracking |
| **League.java** | Manages domestic league competition structure and standings |
| **Cup.java** | Handles knockout-style cup competitions and tournament brackets |

### Controller Classes

Controllers manage specific continental competitions and tournaments:

- **ControllerEurope.java / ControllerEurope_New.java** - European league and cup competitions
- **ControllerAsia.java** - Asian confederation competitions
- **ControllerAfrica.java** - African confederation competitions
- **ControllerSAmerica.java** - South American competitions
- **ControllerNAmerica.java** - North American competitions
- **Controller_WCL.java** - World Cup and international tournament simulation
- **ControllerCWC.java** - Club World Cup simulation
- **Controller_CC.java** - Continental Championship organization

### Helper Functions

Located in the `Functions/` directory:

- **CatalogueHelper.java** - Data management and player/club catalogues
- **FixturesHelper.java** - Fixture generation and scheduling algorithms
- **MatchSimulator.java** - Stochastic match outcome calculation
- **MarketHelper.java** - Transfer market and player valuation logic
- **GeneratorFunctions.java** - Procedural generation of teams, leagues, and players
- **SetupHelper.java** - Initial season and competition configuration

### Data Models

The `Models/` directory contains data transfer objects:

- **Club.java** - Club entity model
- **Player.java** - Player entity model
- **NationalTeam.java** - International team model
- **Squad.java** - Squad roster model
- **Match.java** - Match result model
- **Nation.java** - Nation/country model
- **Competition.java** - Competition metadata
- **Injury.java** - Player injury tracking
- **Position.java** - Player position definitions
- **PositionHelper.java** - Position utilities

### Competition Stages

Located in `Stages/`:

- **RoundRobin.java** - Group stage and league play
- **Cup.java** - Single and two-legged knockout formats
- **Stage.java** - Base stage interface

## Key Features

### 🏆 Multi-Level Competitions
- Domestic leagues across Europe, Asia, Africa, Americas
- Continental cups and qualifying tournaments
- World Cup-style international competitions
- Club World Cup simulation

### 👥 Player Management
- Individual player tracking with realistic attributes
- Position-based squad organization
- Youth academy system with player development
- Injury simulation and recovery mechanics
- Career history and performance statistics

### 💼 Transfer Market
- Dynamic transfer market with player valuations
- Offer generation based on player demand
- Budget constraints and financial planning
- Recruitment level-based scouting capabilities

### ⚙️ Realistic Simulation
- Stochastic match engine for competitive outcomes
- Team strength and form calculations
- Fixture scheduling across all competitions
- ELO-style ranking system (EloRankings.java)
- Standings calculation with tie-breaking rules

### 📊 Season Progression
- Multi-season continuity with historical tracking
- Team performance metrics and trends
- Competition history maintenance
- Statistical aggregation and reporting

## Configuration

The `Sim.java` class provides several configuration flags:

```java
public static boolean SIMULATE_CONTINENTAL_COMPETITIONS = true;  // Enable continental tournaments
public static boolean TEST = true;                               // Test mode with detailed output
public static boolean SHOW_DETAILS = true;                       // Display detailed simulation info
public static boolean USE_TRANSFER_MARKET = true;                // Enable transfer market mechanics
public static boolean USE_INTERNATIONALS = false;                // Enable international match play
```

The starting season is defined as:
```java
public static final int STARTING_SEASON = 2024;
```

## Usage

### Basic Setup
1. Initialize the Sim with clubs, players, and leagues via Setups.java
2. Configure competition structures using appropriate Controller classes
3. Set season parameters and simulation flags in Sim.java
4. Run simulations through the main simulation loop

### Running a Simulation

```java
// Initialize simulation engine
Sim.initializeLeagues();  // Load league data
Sim.initializeClubs();    // Load club data
Sim.initializePlayers();  // Load player data

// Run season simulation
Sim.simulateSeason();     // Execute full season

// Access results
Map<String, League> leagues = Sim.getAllLeagues();
Map<String, Club_Team> clubs = Sim.getAllClubs();
```

## Testing

The repository includes test classes:

- **FixturesTest.java** - Fixture generation and scheduling tests
- **TestFixtures.java** - Additional fixture validation tests

Run tests to verify:
- Fixture compatibility and scheduling
- Match simulation logic
- Competition progression
- Data consistency

## Technologies

- **Language**: Java
- **Paradigm**: Object-Oriented Design with modular architecture
- **Data Structures**: Maps, Lists, and custom collections for entity management
- **Algorithm Focus**: Fixture scheduling, knockout tree generation, stochastic matching

## Entity Relationships

```
Sim (Main Engine)
├── Leagues
│   ├── Clubs
│   │   ├── Senior Squad
│   │   │   └── Players
│   │   └── Youth Squad
│   │       └── Young Players
│   └── Matches
├── National Teams
│   └── Players
├── Competitions (Cups, Tournaments)
│   └── Stages (Group, Knockout)
└── Transfer Market
    └── Player Offers
```

## File Organization

- **Root directory**: Core simulation classes and main entities
- **Functions/**: Helper utilities for specific simulation aspects
- **Models/**: Data model classes for entities
- **Stages/**: Competition stage implementations
- **.gitignore**: Standard Git ignore configuration

## Contributing

To extend simpleSim:

1. **Add New Competition**: Create a new Controller class following existing patterns
2. **Modify Match Logic**: Update MatchSimulator.java with new simulation rules
3. **Extend Player System**: Add attributes to Player.java and PositionHelper.java
4. **Custom Tournaments**: Use Stages/ as templates for new competition formats

## Performance Considerations

- The stochastic engine uses random number generation for match outcomes
- Large numbers of concurrent simulations may require memory optimization
- Consider using TEST mode for detailed analysis on smaller datasets
- Transfer market calculations can be disabled for faster simulations

## License

Check the repository for licensing information.

## Author

**dgutsav** - Created and maintained this comprehensive football simulation engine.

---

*SimpleSim brings football competition simulation to life with realistic mechanics, multi-level competitions, and dynamic player systems.*
