# Test Logging Guide

## Overview

This project uses a standardized logging infrastructure to make tests more educational and easier to debug. All test output follows a consistent format that shows exactly what the test is doing at each step.

## Log Format

### Standard Prefixes

```
[TEST]   - Test phase information (SETUP, ACTION, VERIFY, CLEANUP)
[STATE]  - Current state of objects being tested
[VERIFY] - Assertion results with expected vs actual values
[DEBUG]  - Additional debugging information
```

### Example Output

```
================================================================================
▶ Starting Test: testAddItem_ValidItem_ItemAdded
================================================================================
[TEST] testAddItem_ValidItem_ItemAdded | SETUP | Storage created with capacity=100
[STATE] AdvancedStorage | AdvancedStorage[capacity=100.0, used=0.0, available=100.0, items=0, batches=0]
  Storage Contents: (empty)

[TEST] testAddItem_ValidItem_ItemAdded | ACTION | Adding Wood: quantity=10, durability=5.0
[STATE] AdvancedStorage | AdvancedStorage[capacity=100.0, used=10.0, available=90.0, items=1, batches=1]
  Storage Contents:
    - Wood: 1 batch(es), total=10.0 units
      * Batch[0]: quantity=10.0 units, durability=5 turns

[TEST] testAddItem_ValidItem_ItemAdded | VERIFY | Checking assertions
[VERIFY] Storage should contain Wood | Expected=true, Actual=true [PASS ✓]
[VERIFY] Should have correct quantity | Expected=10.0, Actual=10.0 [PASS ✓]

--------------------------------------------------------------------------------
▶ Test Complete: testAddItem_ValidItem_ItemAdded - ✓ PASSED
================================================================================
```

## Using TestLogger

### 1. Import TestLogger

```java
import com.productionchain.TestLogger;
```

### 2. Start and End Tests

```java
@Test
public void testSomething() {
    TestLogger.startTest("testSomething");

    // ... test code ...

    TestLogger.endTest("testSomething", true); // true if all assertions passed
}
```

### 3. Log Test Phases

```java
// Log setup phase
TestLogger.test("testName", "SETUP", "Creating test objects");

// Log action phase
TestLogger.test("testName", "ACTION", "Calling method X with parameters Y");

// Log verification phase
TestLogger.test("testName", "VERIFY", "Checking results");
```

### 4. Log Object State

```java
// Log current state of objects
TestLogger.state("AdvancedStorage", storage.toString());
TestLogger.state("Recipe", recipe.toString());
```

### 5. Log Assertions

```java
// Capture assertion results
boolean result = storage.hasItem("Wood", 10);
TestLogger.verify("Storage should contain Wood", true, result, result);
assertTrue("Storage should contain Wood", result);

double quantity = storage.getItemQuantity("Wood");
boolean passed = Math.abs(quantity - 10.0) < 0.001;
TestLogger.verify("Quantity should be 10.0", 10.0, quantity, passed);
assertEquals("Quantity should be 10.0", 10.0, quantity, 0.001);
```

### 6. Section Headers (Optional)

```java
TestLogger.section("Testing Edge Cases");
// ... edge case tests ...
```

## Complete Test Example

```java
@Test
public void testAddItem_ValidItem_ItemAdded() {
    TestLogger.startTest("testAddItem_ValidItem_ItemAdded");

    // SETUP PHASE
    TestLogger.test("testAddItem_ValidItem_ItemAdded", "SETUP",
        "Storage created with capacity=100, allowedItems=[Wood, Stone]");
    TestLogger.state("AdvancedStorage (initial)", storage.toString());

    // ACTION PHASE
    TestLogger.test("testAddItem_ValidItem_ItemAdded", "ACTION",
        "Adding Wood: quantity=10, durability=5.0");
    storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);
    TestLogger.state("AdvancedStorage (after add)", storage.toString());

    // VERIFY PHASE
    TestLogger.test("testAddItem_ValidItem_ItemAdded", "VERIFY",
        "Checking if storage contains Wood");

    boolean hasWood = storage.hasItem(ItemConstants.WOOD, 10);
    TestLogger.verify("Storage should contain Wood (10 units)",
        true, hasWood, hasWood);
    assertTrue("Storage should contain Wood", hasWood);

    double quantity = storage.getItemQuantity(ItemConstants.WOOD);
    boolean qtyCorrect = Math.abs(quantity - 10.0) < 0.001;
    TestLogger.verify("Should have correct quantity",
        10.0, quantity, qtyCorrect);
    assertEquals("Should have correct quantity", 10.0, quantity, 0.001);

    TestLogger.endTest("testAddItem_ValidItem_ItemAdded", true);
}
```

## Enhanced toString() Methods

All domain classes now have comprehensive toString() methods:

### AdvancedStorage
```
AdvancedStorage[capacity=100.0, used=25.0, available=75.0, items=2, batches=3]
  Allowed Items: [Wood, Stone]
  Storage Contents:
    - Wood: 2 batch(es), total=15.0 units
      * Batch[0]: quantity=10.0 units, durability=5 turns
      * Batch[1]: quantity=5.0 units, durability=3 turns
    - Stone: 1 batch(es), total=10.0 units
      * Batch[0]: quantity=10.0 units, durability=10 turns
```

### Recipe
```
Recipe[name=ChopWood, category=BasicSilviculture, duration=1.0, enabled=true]
  Description: Chop trees to get wood
  Recipe: 0 ingredient(s) → 1 output(s)
  Ingredients: (none - gathering recipe)
  Outputs:
    [0] IngredientPair[item=Wood, count=1.0, probability=1.00]
```

### IngredientsList
```
IngredientsList[size=3]
  [0] IngredientPair[item=Wood, count=5.0, probability=1.00]
  [1] IngredientPair[item=Stone, count=10.0, probability=0.80]
  [2] IngredientPair[item=Coal, count=3.0, probability=1.00]
```

### ItemType
```
ItemType[name=Wood, category=RawMaterial, weight=10.0, storageSpace=1.0]
  Flags: durable=true, storable=true, transportable=true
  Spoilage: howLongToGoBad=50.0 turns
```

### RecipeHandler
```
RecipeHandler[operations=2, categories=3]
  Recipe Categories:
    [0] BasicSilviculture
    [1] BasicMining
    [2] BasicKiln
  Available Recipes: 5
    - ChopWood
    - MineStone
    - CharcoalBurning
    ... and 2 more
```

## Benefits

1. **Educational**: See exactly what tests do step-by-step
2. **Debugging**: Easily identify where tests fail and why
3. **Understanding**: See how objects change during operations
4. **Documentation**: Tests become self-documenting
5. **Consistency**: All tests follow the same format

## Running Tests with Logging

```bash
# Run all tests and see detailed output
mvn test

# Run specific test class
mvn test -Dtest=AdvancedStorageTest

# Run single test method
mvn test -Dtest=AdvancedStorageTest#testAddItem_ValidItem_ItemAdded
```

## Tips

1. **Use descriptive test names** - They appear in log output
2. **Log state before and after actions** - Shows what changed
3. **Always log the verification phase** - Makes assertions clear
4. **Use sections** for complex tests with multiple parts
5. **Keep action descriptions concise** but clear
6. **Include parameter values** in action descriptions

## Example: Complex Multi-Step Test

```java
@Test
public void testFIFO_MultipleOperations() {
    TestLogger.startTest("testFIFO_MultipleOperations");

    TestLogger.section("Setup: Adding Multiple Batches");
    TestLogger.test("testFIFO_MultipleOperations", "SETUP",
        "Adding 3 batches of Wood with different durabilities");

    storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);
    TestLogger.state("After batch 1", storage.toString());

    storage.addItemCheckCapacity(ItemConstants.WOOD, 15, 10.0);
    TestLogger.state("After batch 2", storage.toString());

    storage.addItemCheckCapacity(ItemConstants.WOOD, 20, 8.0);
    TestLogger.state("After batch 3", storage.toString());

    TestLogger.section("Action: Removing Items (FIFO)");
    TestLogger.test("testFIFO_MultipleOperations", "ACTION",
        "Removing 25 Wood (should take from oldest batches first)");

    boolean removed = storage.removeItem(ItemConstants.WOOD, 25);
    TestLogger.state("After removal", storage.toString());

    TestLogger.section("Verification: Check FIFO Behavior");
    TestLogger.verify("Removal should succeed", true, removed, removed);
    assertTrue("Removal should succeed", removed);

    double remaining = storage.getItemQuantity(ItemConstants.WOOD);
    boolean correctAmount = Math.abs(remaining - 20.0) < 0.001;
    TestLogger.verify("Should have 20 Wood remaining",
        20.0, remaining, correctAmount);
    assertEquals("Should have 20 Wood remaining", 20.0, remaining, 0.001);

    TestLogger.endTest("testFIFO_MultipleOperations", true);
}
```

## Next Steps

Apply this logging pattern to all test classes:
1. AdvancedStorageTest (12 tests)
2. IngredientTest (11 tests)
3. ItemTypeTest (6 tests)
4. RecipeTest (8 tests)
5. RecipeHandlerTest (10 tests)
6. ItemRegistryTest (4 tests)
7. RecipeRegistryTest (4 tests)
8. ConstantsTest (4 tests)
9. ResourceLoaderTest (6 tests)

Each test should follow the pattern:
- `TestLogger.startTest()`
- Log SETUP phase with initial state
- Log ACTION phase with what's being done
- Log state after action
- Log VERIFY phase with all assertions
- `TestLogger.endTest()`
