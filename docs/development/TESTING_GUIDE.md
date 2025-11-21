# Testing Guide - Production Chain Game

This guide explains how to run tests and achieve good code coverage for the Production Chain Game project.

## Test Structure

Tests are located in `productionchainid/src/test/java/com/productionchain/`

### Current Test Coverage

- **ResourceLoaderTest**: Tests resource loading utility (platform-independent path handling)
- **ItemRegistryTest**: Tests item registration and retrieval
- **RecipeRegistryTest**: Tests recipe registration and category filtering
- **ConstantsTest**: Tests constants classes (ItemConstants, RecipeConstants, BuildingConstants)

## Running Tests

### Using Maven

#### Run all tests:
```bash
cd productionchainid
mvn test
```

#### Run a specific test class:
```bash
mvn test -Dtest=ResourceLoaderTest
mvn test -Dtest=ItemRegistryTest
mvn test -Dtest=RecipeRegistryTest
```

#### Run tests with verbose output:
```bash
mvn test -X
```

### Using Maven with Code Coverage (JaCoCo)

#### 1. Add JaCoCo plugin to pom.xml (if not already added)

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.10</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

#### 2. Run tests with coverage:
```bash
mvn clean test jacoco:report
```

#### 3. View coverage report:
Open `target/site/jacoco/index.html` in your browser

### Expected Test Results

All tests should pass with output similar to:
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.productionchain.ResourceLoaderTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.productionchain.ItemRegistryTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.productionchain.RecipeRegistryTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.productionchain.ConstantsTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Writing New Tests

### Test Structure Template

```java
package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MyComponentTest {

    @Before
    public void setUp() {
        // Setup code run before each test
    }

    @Test
    public void testFeature_SuccessCase() {
        // Arrange
        MyComponent component = new MyComponent();

        // Act
        Result result = component.doSomething();

        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Expected value", expectedValue, result.getValue());
    }

    @Test(expected = MyException.class)
    public void testFeature_ErrorCase() {
        // Test that exception is thrown
        MyComponent component = new MyComponent();
        component.doInvalidOperation();
    }
}
```

### Best Practices

1. **Test Naming**: Use descriptive names like `testMethodName_Scenario_ExpectedResult`
   - Example: `testAddItem_ValidItem_ItemIsAdded`

2. **Arrange-Act-Assert Pattern**: Structure tests clearly:
   - **Arrange**: Set up test data and conditions
   - **Act**: Execute the method being tested
   - **Assert**: Verify the results

3. **Test One Thing**: Each test should verify one specific behavior

4. **Use setUp() and tearDown()**: Clean state between tests
   - `@Before`: Runs before each test
   - `@After`: Runs after each test

5. **Test Edge Cases**: Include tests for:
   - Null values
   - Empty collections
   - Boundary conditions
   - Invalid inputs

## Integration Tests

For integration tests (testing multiple components together):

```java
@Test
public void testCompleteRecipeExecution() {
    // Setup items
    ItemRegistry.addItem(new ItemType(ItemConstants.WOOD, ...));

    // Setup recipe
    RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_CHOP_WOOD, ...));

    // Setup building
    ProductionBuildingType hut = new ProductionBuildingType(...);

    // Test complete workflow
    ProductionBuildingInstance building = new ProductionBuildingInstance(hut, ...);
    building.setActiveRecipe(recipe);
    building.produceGoods();

    // Verify results
    assertTrue(building.getStorage().hasItem(ItemConstants.WOOD));
}
```

## Continuous Integration

To run tests automatically in CI/CD:

```yaml
# Example GitHub Actions workflow
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '8'
      - name: Run tests
        run: cd productionchainid && mvn clean test
      - name: Generate coverage report
        run: mvn jacoco:report
```

## Code Coverage Goals

- **Target**: 80% code coverage overall
- **Critical Components**: 90%+ coverage
  - ItemRegistry
  - RecipeRegistry
  - AdvancedStorage
  - ResourceLoader

## Troubleshooting

### Tests fail with "Resource not found"
- Ensure resources are in `src/main/resources/`
- Check that resource names match exactly (case-sensitive)

### Tests fail with ClassNotFoundException
- Run `mvn clean install` to rebuild
- Check that test dependencies are in pom.xml

### Tests pass locally but fail in CI
- Check Java version compatibility
- Verify all dependencies are in pom.xml (not just local)
- Ensure no hardcoded paths

## Next Steps

To improve test coverage:

1. **Add tests for**:
   - AdvancedStorage (FIFO operations, spoilage)
   - BuildingInstance (production, degradation)
   - RecipeSystem (recipe validation, execution)

2. **Add integration tests** for:
   - Complete production workflows
   - Building upgrades
   - JSON serialization/deserialization

3. **Add performance tests** for:
   - Large-scale storage operations
   - Recipe registry lookup performance
   - Building instance batch updates
