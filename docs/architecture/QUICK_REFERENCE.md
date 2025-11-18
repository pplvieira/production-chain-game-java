# Quick Reference Guide: Medieval Production Chain Game

## Key Facts at a Glance

| Metric | Value |
|--------|-------|
| **Java Files** | 33 total (23 active, 11 legacy) |
| **Lines of Code** | ~4,214 (2,980 active, 1,234 legacy) |
| **Packages** | 4 (buildings, mechanics, enginedatatypes, datatypesold) |
| **Java Version** | 1.8 (outdated, should be 11+) |
| **Main Dependency** | Jackson 2.17.0 (JSON handling) |
| **Test Coverage** | 0% (JUnit available but unused) |
| **Game Status** | Work in Progress (started Jan 2025) |

---

## Architecture Overview

```
GAME CORE
  ├─ Registries (static singletons)
  │  ├─ ItemRegistry (all game items)
  │  ├─ RecipeRegistry (all recipes)
  │  └─ AnimalTypeRegistry (all animal species)
  │
  ├─ Building System
  │  ├─ Types (definitions)
  │  │  ├─ ProductionBuildingType (crafting)
  │  │  └─ AnimalBuildingType (housing)
  │  │
  │  └─ Instances (placed in world)
  │     ├─ ProductionBuildingInstance
  │     └─ AnimalBuildingInstance
  │
  ├─ Storage System
  │  ├─ Storage (basic, rarely used)
  │  └─ AdvancedStorage (item batches with durability)
  │
  └─ Mechanics
     ├─ RecipeHandler (manages recipe categories)
     ├─ RecipeSystem (scrapped, ignore)
     └─ Upgrade system (not implemented)
```

---

## What's Working

### ✓ Fully Implemented

1. **Item System**
   - ItemType definitions with categories
   - ItemRegistry with JSON persistence
   - Item properties: weight, storage space, durability, tradeability

2. **Recipe System**
   - Recipe definitions (name, category, ingredients, outputs)
   - RecipeRegistry with JSON persistence
   - Category-based filtering
   - Recipe execution with ingredient validation

3. **Building System**
   - Two building types: Production and Animal
   - Building type hierarchies (inheritance)
   - Building instances with ownership, position
   - JSON persistence for types and instances

4. **Storage System**
   - Item capacity limits
   - FIFO spoilage/durability system
   - Item batch tracking with degradation
   - Allowed items whitelist

5. **Production Mechanics**
   - Set active recipe → Validate → Execute
   - Ingredient consumption
   - Output creation
   - Storage validation

### ⚠️ Partially Implemented

- Animal system (types defined, instances created, but lifecycle incomplete)
- Building degradation (structure exists, not functional)
- Turn system (skeleton only)
- Building upgrades (framework exists, not implemented)

### ✗ Not Started

- Trade/Economy system
- Map/Pathfinding
- UI/Frontend
- Multiplayer
- Database (currently JSON-based)

---

## Critical Issues to Fix

### PRIORITY 1: Delete Legacy Code
```
DELETE: entire com.productionchain.datatypesold package
  ├─ 11 files
  ├─ 1,234 lines of duplicated/old code
  ├─ Causes confusion
  └─ 29% of codebase!
```

### PRIORITY 2: Fix Portability
```
PROBLEM: Hardcoded Windows paths
  ├─ BuildingInstance.java:71
  ├─ AnimalBuildingType.java:94
  └─ ProductionBuildingType.java:113

FIX: Use relative paths or class resources
  ├─ Use ClassLoader.getResource()
  ├─ Use Path with current directory
  └─ Use @TestResource annotations
```

### PRIORITY 3: Remove Debug Code
```
PROBLEM: Scattered println() statements
  ├─ 5+ instances across files
  ├─ Pollute console output
  └─ Inefficient

FIX: Use proper logging
  ├─ Add SLF4J dependency
  ├─ Replace println with logger.info()
  └─ Configure log levels
```

### PRIORITY 4: Test Coverage
```
PROBLEM: No tests, 0% coverage
  ├─ JUnit dependency exists but unused
  ├─ All tests embedded in main() methods
  └─ No CI/CD integration

FIX: Create src/test/java directory
  ├─ RecipeExecutionTest
  ├─ StorageTest
  ├─ AdvancedStorageTest
  ├─ RegistryTest
  └─ BuildingInstanceTest
```

---

## Package Guide

### com.productionchain.buildings (1,098 lines)
**Best practices: B+ | Quality: Good**

- **BuildingType** - Abstract base, well-structured
- **ProductionBuildingType** - Extends properly, has hardcoded paths
- **AnimalBuildingType** - Extends properly, incomplete
- **BuildingInstance** - Complex, good recipe handling, poor deserialization
- **ProductionBuildingInstance** - Simple, mostly stub
- **AnimalBuildingInstance** - Good statistics aggregation, incomplete updates
- **BuildingTypeList<T>** - Excellent generic implementation

### com.productionchain.mechanics (939 lines)
**Best practices: B | Quality: Good**

- **RecipeHandler** - Simple, effective filter wrapper
- **RecipeSystem** - REMOVE (60% commented out)
- **Storage** - Good implementation, unused
- **AdvancedStorage** - Excellent FIFO spoilage system
- **AnimalType** - Comprehensive blueprint, complete
- **AnimalInstance** - Incomplete lifecycle
- **AnimalTypeRegistry** - Good registry implementation
- **Upgrade** - DELETE (5 empty lines)

### com.productionchain.enginedatatypes (726 lines)
**Best practices: B+ | Quality: Excellent**

- **ItemRegistry** - Clean, efficient, good pattern
- **ItemType** - Complete definitions, good structure
- **RecipeRegistry** - Well-organized, category filtering
- **Recipe** - Clean implementation, good composition
- **IngredientPair** - Simple, effective, has debug print
- **IngredientsList** - Good container implementation

### com.productionchain.datatypesold (1,234 lines)
**Best practices: D | Quality: LEGACY**

**DELETE ENTIRE PACKAGE**
- Contains duplicates of refactored classes
- Prevents code maintenance
- Confuses new developers
- No active usage

---

## How to Use the Game Engine

### 1. Initialize Game Data
```java
// Load all registries from JSON
ItemRegistry.loadFromJson("items.json");
RecipeRegistry.loadFromJson("recipes.json");
BuildingTypeList<ProductionBuildingType> buildings = new BuildingTypeList<>(ProductionBuildingType.class);
buildings.loadFromJson("buildings.json");
```

### 2. Create Building Instance
```java
ProductionBuildingType hut = buildings.getBuildingByName("Hut");
ProductionBuildingInstance myHut = new ProductionBuildingInstance(hut, "Player1", 0, 0, 10);
```

### 3. Execute Recipe
```java
Recipe chop = RecipeRegistry.getRecipeByName("Chop wood");
myHut.setActiveRecipe(chop);
myHut.performRecipe();  // Executes, removes ingredients, adds outputs
```

### 4. Check Status
```java
myHut.getStorage();          // View stored items
myHut.getAvailableRecipes(); // See possible recipes
myHut.getCondition();        // Check building health
```

### 5. Update Turn
```java
myHut.updateNextTurn(); // Called each game turn (not fully implemented)
myHut.getStorage().degradeItems(); // Spoil items manually
```

---

## Files You Need to Know About

### **MUST READ FIRST**
1. `/home/user/production-chain-game-java/CODEBASE_ANALYSIS.md` - Full analysis (21KB)
2. `/home/user/production-chain-game-java/JAVA_FILES_INVENTORY.txt` - Detailed class listing (13KB)
3. `com/productionchain/Main.java` - Best usage example

### **CRITICAL PROBLEMS**
- BuildingInstance.java:71-100 - Hardcoded path loading
- com/productionchain/datatypesold/ - Entire package to delete
- AnimalBuildingInstance.java:114-116 - Unimplemented feature

### **GOOD CODE TO LEARN FROM**
- AdvancedStorage.java - Excellent FIFO implementation
- BuildingTypeList.java - Good generics usage
- RecipeHandler.java - Clean responsibility separation
- ItemRegistry.java - Solid registry pattern

---

## Next Steps (Priority Order)

### Week 1: CRITICAL
- [ ] Delete datatypesold package (saves 30% code bloat)
- [ ] Create src/test/java structure
- [ ] Fix hardcoded Windows paths
- [ ] Add proper logging (remove println)

### Week 2: HIGH
- [ ] Create BuildingTypeService/ItemService (singleton pattern)
- [ ] Write basic tests (RecipeExecution, Storage, Registry)
- [ ] Complete AnimalBuildingInstance.updateNextTurn()
- [ ] Extract magic strings to constants

### Week 3: MEDIUM
- [ ] Add turn/update loop system
- [ ] Implement building condition degradation
- [ ] Add configuration file support (application.properties)
- [ ] Update to Java 11+

### Week 4+: POLISH
- [ ] Add javadocs to all public methods
- [ ] Implement Upgrade system
- [ ] Add data export/import
- [ ] Improve documentation

---

## Common Tasks

### How to add a new item type
```java
ItemRegistry.addItem(new ItemType("Iron", "Metal", 2.0, 15.0, 50.0, true, true, true));
ItemRegistry.saveToJson("ItemRegistry_test.json");
```

### How to add a new recipe
```java
List<IngredientPair> ingredients = List.of(new IngredientPair("Wood", 2));
List<IngredientPair> outputs = List.of(new IngredientPair("Coal", 3));
Recipe charcoal = new Recipe("Charcoal", "Basic kiln", "Burn wood", 
    1, 1, new IngredientsList(ingredients), new IngredientsList(outputs), 1.0, true);
RecipeRegistry.addRecipe(charcoal);
RecipeRegistry.saveToJson("RecipeRegistry_test.json");
```

### How to test a recipe execution
```java
ProductionBuildingType kiln = buildings.getBuildingByName("Kiln");
ProductionBuildingInstance kilnInstance = new ProductionBuildingInstance(kiln, "Player1", 0, 0, 20);
kilnInstance.addItem("Wood", 2, 10);  // Add wood with 10 turns durability
kilnInstance.setActiveRecipe(charcoal);
kilnInstance.performRecipe();  // Executes
System.out.println(kilnInstance.getStorage());  // Should show Coal now
```

---

## Metrics & Statistics

### Code Quality Score by Package
```
buildings:        B+ (good, some issues)
mechanics:        B  (good, incomplete features)
enginedatatypes:  B+ (excellent)
datatypesold:     D  (DELETE THIS)
Overall:          B  (with cleanup: A-)
```

### Lines of Code Distribution
```
Active Code:      2,980 lines (71%)  ██████░
Legacy Code:      1,234 lines (29%)  ░
                  ─────────────────
Total:            4,214 lines (100%)
```

### Feature Completion
```
Items/Recipes:    ████████░░ 90%
Buildings:        ████████░░ 85%
Storage:          ████████░░ 85%
Production:       ██████░░░░ 70%
Animals:          ███░░░░░░░ 35%
Trading:          ░░░░░░░░░░ 0%
UI:               ░░░░░░░░░░ 0%
```

---

## Useful Commands

### Compile the project
```bash
cd /home/user/production-chain-game-java/productionchainid
mvn clean compile
```

### Run the main test
```bash
mvn exec:java -Dexec.mainClass="com.productionchain.Main"
```

### Find all TODO comments
```bash
grep -r "TODO" src/main/java/
```

### Find all hardcoded paths
```bash
grep -r "C:\\\\" src/main/java/
grep -r "/home/user" src/main/java/
```

### Count lines of code
```bash
find src/main/java -name "*.java" -exec wc -l {} + | tail -1
```

---

## Architecture Decisions Made

### ✓ Good Decisions
1. **Polymorphic JSON** - @JsonTypeInfo allows different building types in same array
2. **Generic BuildingTypeList<T>** - Reusable container for any building type
3. **Registry Pattern** - Static access to game data (ItemRegistry, etc.)
4. **Composition** - Buildings contain Storage and RecipeHandler (not inherit)
5. **Category-based Filtering** - Recipes/items organized by category

### ✗ Bad Decisions
1. **Static Mutable State** - Registries as static fields make testing hard
2. **Hardcoded Paths** - Makes code non-portable
3. **Deserialization Side Effects** - BuildingInstance loads registry during JSON read
4. **Magic Strings** - Recipe categories and item names scattered throughout
5. **Incomplete Features** - RecipeSystem, Upgrade left half-done

---

## Quick Fixes (30 minutes each)

### Fix 1: Remove hardcoded paths
- Replace C:\...\ProductionBuildings.json with classpath resource loading
- Files: BuildingInstance.java, AnimalBuildingType.java, ProductionBuildingType.java

### Fix 2: Add logging framework
- Add SLF4J to pom.xml
- Replace all println() with logger.info()
- Files: ~8 files affected

### Fix 3: Extract magic strings
- Create ItemConstants.java
- Create RecipeConstants.java
- Replace hardcoded "Wood", "Stick", etc.

### Fix 4: Remove debug prints
- Delete println in BuildingType.java:112
- Delete println in IngredientPair.java:13
- Search and remove all "[WARNING]", "[ERROR]" debug prints

### Fix 5: Delete RecipeSystem.java
- 60% commented out
- Not used anywhere
- Clean removal with no dependencies

---

## Resources

### Internal
- **CODEBASE_ANALYSIS.md** - Full 21KB analysis report
- **JAVA_FILES_INVENTORY.txt** - Detailed class descriptions
- **Release Notes.md** - Development history (Jan-Apr 2025)
- **Main.java** - Best usage example

### External
- Jackson Documentation: https://github.com/FasterXML/jackson-databind
- Java 8 Streams: https://docs.oracle.com/javase/8/docs/api/java/util/stream/
- Maven Guide: https://maven.apache.org/guides/

---

## Summary

This is a **well-structured foundation** with excellent patterns for production management games. Main issues are:

1. **29% dead code** (datatypesold package) - DELETE
2. **Hardcoded paths** - FIX (30 min)
3. **No tests** - CREATE (1 day)
4. **Incomplete features** - COMPLETE (1 week)

With these fixes, this becomes a **solid A-grade codebase** ready for commercial-grade game development.

