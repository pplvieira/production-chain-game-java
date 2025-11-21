# Testing and Code Coverage Explained

## Table of Contents
1. [What are Unit Tests?](#what-are-unit-tests)
2. [How Maven Runs Tests](#how-maven-runs-tests)
3. [Understanding Code Coverage](#understanding-code-coverage)
4. [JaCoCo Coverage Tool](#jacoco-coverage-tool)
5. [Test Structure](#test-structure)
6. [Running Tests](#running-tests)
7. [Reading Test Output](#reading-test-output)

---

## What are Unit Tests?

Unit tests are small programs that verify individual components (classes and methods) work correctly in isolation.

### Why Test?
- **Catch bugs early**: Find problems before code reaches production
- **Document behavior**: Tests show how code should be used
- **Enable refactoring**: Change code confidently knowing tests will catch breaks
- **Regression prevention**: Ensure old bugs don't come back

### Test Anatomy (Arrange-Act-Assert)

Every test follows three phases:

```java
@Test
public void testAddItem_ValidItem_ItemAdded() {
    // 1. ARRANGE - Set up test conditions
    AdvancedStorage storage = new AdvancedStorage(100.0, allowedItems);

    // 2. ACT - Execute the method being tested
    storage.addItemCheckCapacity("Wood", 10, 5.0);

    // 3. ASSERT - Verify the result matches expectations
    assertTrue(storage.hasItem("Wood", 10));
    assertEquals(10.0, storage.getItemQuantity("Wood"), 0.001);
}
```

---

## How Maven Runs Tests

### Maven Build Lifecycle

When you run `mvn test`, Maven executes these phases in order:

```
1. clean          → Delete old compiled code
2. validate       → Check project structure
3. compile        → Compile src/main/java/**/*.java
4. test-compile   → Compile src/test/java/**/*Test.java
5. test           → Run all tests and generate reports
```

### Project Structure

```
productionchainid/
├── src/
│   ├── main/
│   │   └── java/           ← Production code
│   │       └── com/productionchain/
│   │           ├── buildings/
│   │           ├── mechanics/
│   │           └── enginedatatypes/
│   └── test/
│       └── java/           ← Test code
│           └── com/productionchain/
│               ├── AdvancedStorageTest.java
│               ├── RecipeTest.java
│               └── ...
└── target/                 ← Compiled code and reports
    ├── classes/            ← Compiled production code
    ├── test-classes/       ← Compiled test code
    └── surefire-reports/   ← Test results
```

### Test Discovery

Maven automatically finds and runs all classes that:
- Are located in `src/test/java/`
- Have names matching `*Test.java` or `Test*.java`
- Contain methods annotated with `@Test`

---

## Understanding Code Coverage

Code coverage measures **what percentage of your production code is executed by tests**.

### Why Coverage Matters

- ✅ **High coverage** (70%+): Most code paths are tested
- ⚠️ **Medium coverage** (40-70%): Some code untested, risky
- ❌ **Low coverage** (<40%): Many bugs likely hiding

### Types of Coverage

#### 1. Line Coverage
**What**: Percentage of code lines executed

```java
public boolean removeItem(String item, double qty) {
    if (!items.containsKey(item)) return false;  // Line 1
    if (!hasItem(item, qty)) return false;       // Line 2
    // ... removal code ...                      // Lines 3-10
    return true;                                 // Line 11
}
```

- If test only removes items that exist: **Lines 1,3-11 executed** = 91% coverage
- If test also tries to remove non-existent items: **All lines executed** = 100% coverage

#### 2. Branch Coverage
**What**: Percentage of if/else paths taken

```java
if (capacity > used) {  // Branch point
    addItem();          // Branch A
} else {
    rejectItem();       // Branch B
}
```

- **50% branch coverage**: Only tested one path
- **100% branch coverage**: Tested both paths

#### 3. Method Coverage
**What**: Percentage of methods called by tests

```java
public class Storage {
    public void addItem() { }      // Called by tests ✓
    public void removeItem() { }   // Called by tests ✓
    public void clear() { }        // Never called ✗
}
```
**Coverage**: 2/3 methods = 67%

---

## JaCoCo Coverage Tool

JaCoCo (Java Code Coverage) is a tool that tracks which lines of code your tests execute.

### How JaCoCo Works

1. **Instrumentation**: JaCoCo modifies bytecode to track execution
2. **Test Run**: As tests run, JaCoCo records what executes
3. **Report Generation**: Creates HTML report showing coverage

### Enabling JaCoCo

Already configured in `pom.xml`:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
</plugin>
```

### Generating Coverage Report

```bash
# Run tests and generate coverage report
mvn clean test jacoco:report

# Open the report in browser (Windows)
start target\site\jacoco\index.html

# Open the report (Mac/Linux)
open target/site/jacoco/index.html
```

### Reading Coverage Reports

JaCoCo report uses color coding:

- 🟢 **Green**: Code executed by tests
- 🟡 **Yellow**: Partially executed (some branches)
- 🔴 **Red**: Not executed by tests

**Example Report View:**
```
Package: com.productionchain.mechanics

Class                    Line Coverage    Branch Coverage
AdvancedStorage          95% (88/93)      87% (14/16)      🟢
Storage                  78% (45/58)      65% (11/17)      🟡
RecipeHandler            45% (23/51)      33% (5/15)       🔴
```

**Click on a class to see line-by-line coverage:**
```java
public class AdvancedStorage {
    🟢 public void addItem(String item, double qty) {
    🟢     if (!allowedItems.contains(item)) {      // Both branches tested
    🟢         return;
    🟢     }
    🔴     if (qty < 0) {                          // Never tested!
    🔴         throw new IllegalArgumentException();
    🔴     }
    🟢     items.put(item, qty);
    🟢 }
}
```

---

## Test Structure

### JUnit 4 Annotations

```java
public class AdvancedStorageTest {

    private AdvancedStorage storage;

    @Before
    public void setUp() {
        // Runs BEFORE each @Test method
        // Use for: Creating test objects, initializing state
        storage = new AdvancedStorage(100.0, allowedItems);
    }

    @After
    public void tearDown() {
        // Runs AFTER each @Test method
        // Use for: Cleanup, closing resources
    }

    @BeforeClass
    public static void setUpClass() {
        // Runs ONCE before all tests in class
        // Use for: Expensive setup (database connections, etc.)
    }

    @AfterClass
    public static void tearDownClass() {
        // Runs ONCE after all tests in class
        // Use for: Cleanup of class-level resources
    }

    @Test
    public void testSomething() {
        // Actual test method
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        // Test that expects an exception
    }

    @Test(timeout = 1000)
    public void testPerformance() {
        // Test must complete within 1000ms
    }

    @Ignore("Not implemented yet")
    @Test
    public void testFutureFeature() {
        // Test is skipped
    }
}
```

### Common Assertions

```java
// Equality
assertEquals("message", expected, actual);
assertEquals(10.0, quantity, 0.001);  // For doubles with tolerance

// Truth
assertTrue("message", condition);
assertFalse("message", condition);

// Null checks
assertNull("message", object);
assertNotNull("message", object);

// Same object
assertSame("message", expected, actual);

// Arrays
assertArrayEquals("message", expectedArray, actualArray);

// Always fail
fail("This should not be reached");
```

### Test Naming Convention

Use descriptive names that explain:
1. **Method being tested**
2. **Test scenario**
3. **Expected result**

**Format**: `test<Method>_<Scenario>_<ExpectedResult>`

**Examples:**
```java
testAddItem_ValidItem_ItemAdded()
testAddItem_ExceedsCapacity_NotAdded()
testRemoveItem_InsufficientQuantity_ReturnsFalse()
testFIFO_OldestBatchRemovedFirst()
```

---

## Running Tests

### Run All Tests

```bash
mvn test
```

**Output:**
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.productionchain.AdvancedStorageTest
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.productionchain.RecipeTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
...
[INFO] Results:
[INFO] Tests run: 65, Failures: 0, Errors: 0, Skipped: 0
```

### Run Specific Test Class

```bash
mvn test -Dtest=AdvancedStorageTest
```

### Run Single Test Method

```bash
mvn test -Dtest=AdvancedStorageTest#testAddItem_ValidItem_ItemAdded
```

### Run Tests Matching Pattern

```bash
mvn test -Dtest=*StorageTest
```

### Skip Tests (During Build)

```bash
mvn install -DskipTests
```

---

## Reading Test Output

### Successful Test Run

```
[INFO] Running com.productionchain.AdvancedStorageTest
================================================================================
▶ Starting Test: testAddItem_ValidItem_ItemAdded
================================================================================
[TEST] testAddItem_ValidItem_ItemAdded | SETUP | Creating storage
[STATE] AdvancedStorage | AdvancedStorage[capacity=100.0, used=0.0]
[TEST] testAddItem_ValidItem_ItemAdded | ACTION | Adding Wood: qty=10
[STATE] AdvancedStorage | AdvancedStorage[capacity=100.0, used=10.0]
[VERIFY] Should contain Wood | Expected=true, Actual=true [PASS ✓]
[VERIFY] Quantity should be 10.0 | Expected=10.0, Actual=10.0 [PASS ✓]
--------------------------------------------------------------------------------
▶ Test Complete: testAddItem_ValidItem_ItemAdded - ✓ PASSED
================================================================================

[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.191 s
```

### Failed Test

```
[INFO] Running com.productionchain.AdvancedStorageTest
[ERROR] Tests run: 12, Failures: 1, Errors: 0, Skipped: 0

[ERROR] testRemoveItem_InsufficientQuantity_ReturnsFalse
  Time elapsed: 0.012 s  <<< FAILURE!

java.lang.AssertionError: Should still have original quantity
  expected:<10.0> but was:<0.0>
  at com.productionchain.AdvancedStorageTest.testRemoveItem(AdvancedStorageTest.java:75)
```

**This tells you:**
- **Which test failed**: `testRemoveItem_InsufficientQuantity_ReturnsFalse`
- **What was expected**: `10.0`
- **What was actual**: `0.0`
- **Where it failed**: Line 75 of AdvancedStorageTest.java

### Test Reports

After running tests, check:

```
target/surefire-reports/
├── com.productionchain.AdvancedStorageTest.txt
├── TEST-com.productionchain.AdvancedStorageTest.xml
└── ... (one file per test class)
```

---

## Current Test Suite

### Test Coverage Summary

| Test Class | Tests | Lines Covered | Purpose |
|-----------|-------|--------------|---------|
| AdvancedStorageTest | 12 | FIFO storage | Batch management, FIFO, spoilage |
| IngredientTest | 11 | Ingredient system | Pairs and lists |
| ItemTypeTest | 6 | Item properties | Flags, categories |
| RecipeTest | 8 | Recipe creation | Ingredients → outputs |
| RecipeHandlerTest | 10 | Recipe filtering | Category-based recipes |
| ItemRegistryTest | 4 | Item registry | Add/get/clear items |
| RecipeRegistryTest | 4 | Recipe registry | Add/get/clear recipes |
| ConstantsTest | 4 | Constants classes | Singleton pattern |
| ResourceLoaderTest | 6 | File loading | Resource paths |
| **Total** | **65** | **High coverage** | **All core systems** |

### Test Organization

```
src/test/java/com/productionchain/
├── TestLogger.java              ← Logging utility
├── AdvancedStorageTest.java    ← Most complex tests
├── IngredientTest.java
├── ItemTypeTest.java
├── RecipeTest.java
├── RecipeHandlerTest.java
├── ItemRegistryTest.java
├── RecipeRegistryTest.java
├── ConstantsTest.java
└── ResourceLoaderTest.java
```

---

## Best Practices

### 1. Test Independence
Each test should be independent and not rely on other tests.

**Bad:**
```java
@Test public void testA() { storage.addItem("Wood", 10); }
@Test public void testB() { // Assumes testA ran first!
    assertEquals(10, storage.getItemQuantity("Wood"));
}
```

**Good:**
```java
@Test public void testA() {
    storage.addItem("Wood", 10);
    assertEquals(10, storage.getItemQuantity("Wood"));
}

@Test public void testB() {
    storage.addItem("Wood", 10);  // Set up own state
    assertEquals(10, storage.getItemQuantity("Wood"));
}
```

### 2. Test One Thing
Each test should verify one specific behavior.

**Bad:**
```java
@Test public void testStorage() {
    storage.addItem("Wood", 10);
    storage.removeItem("Wood", 5);
    storage.degradeItems();
    storage.addItem("Stone", 20);
    // Testing 4 different things!
}
```

**Good:**
```java
@Test public void testAddItem_ValidItem_ItemAdded() { }
@Test public void testRemoveItem_SufficientQuantity_ItemRemoved() { }
@Test public void testDegradeItems_ExpiredItems_Removed() { }
```

### 3. Use Descriptive Names
Test names should be self-documenting.

**Bad:**
```java
@Test public void test1() { }
@Test public void testStorage() { }
```

**Good:**
```java
@Test public void testAddItem_ExceedsCapacity_NotAdded() { }
@Test public void testFIFO_OldestBatchRemovedFirst() { }
```

### 4. Test Edge Cases
Don't just test the happy path.

```java
@Test public void testAddItem_ValidItem_ItemAdded() { }           // Happy path
@Test public void testAddItem_NullItem_ThrowsException() { }      // Edge case
@Test public void testAddItem_NegativeQuantity_ThrowsException() { } // Edge case
@Test public void testAddItem_ExceedsCapacity_NotAdded() { }      // Edge case
@Test public void testAddItem_DisallowedItem_NotAdded() { }       // Edge case
```

### 5. Keep Tests Fast
Tests should run quickly (< 1 second each).
- Use @Before for common setup
- Avoid Thread.sleep()
- Mock expensive operations (database, network)

---

## Next Steps

1. **Run the tests**: `mvn clean test`
2. **Generate coverage report**: `mvn jacoco:report`
3. **Open report**: `target/site/jacoco/index.html`
4. **Add logging to tests**: Follow [TEST_LOGGING_GUIDE.md](TEST_LOGGING_GUIDE.md)
5. **Aim for 80%+ coverage**: Write tests for uncovered code

---

## Common Issues

### Issue: Tests pass locally but fail in CI
**Solution**: Check test dependencies on environment, time, or file system

### Issue: Flaky tests (sometimes pass, sometimes fail)
**Solution**: Tests likely depend on timing, random values, or test order

### Issue: "No tests found"
**Solution**: Check test class names end with `Test.java` and methods have `@Test`

### Issue: Coverage report empty
**Solution**: Run `mvn clean test jacoco:report` (not just `mvn test`)

---

## Resources

- **JUnit 4 Documentation**: https://junit.org/junit4/
- **JaCoCo Documentation**: https://www.jacoco.org/jacoco/
- **Maven Surefire Plugin**: https://maven.apache.org/surefire/maven-surefire-plugin/
- **Test Logging Guide**: [TEST_LOGGING_GUIDE.md](TEST_LOGGING_GUIDE.md)
