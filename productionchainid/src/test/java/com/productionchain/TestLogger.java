package com.productionchain;

/**
 * Utility class for consistent test logging across all unit tests.
 * Provides structured logging with clear phases and formatting.
 *
 * Log Format:
 * - [TEST] TestName | Phase | Action
 * - [STATE] ClassName | attribute=value, attribute=value
 * - [VERIFY] Assertion | Expected=value, Actual=value [PASS/FAIL]
 *
 * Usage Example:
 * <pre>
 * TestLogger.test("testAddItem", "SETUP", "Creating storage with capacity=100");
 * TestLogger.state("AdvancedStorage", storage.toString());
 * TestLogger.test("testAddItem", "ACTION", "Adding Wood: qty=10");
 * TestLogger.verify("Should contain Wood", true, result, result);
 * </pre>
 */
public class TestLogger {

    private static final String SEPARATOR = "=".repeat(80);
    private static final String THIN_SEPARATOR = "-".repeat(80);

    /**
     * Log test phase information (SETUP, ACTION, VERIFY, CLEANUP)
     * @param testName Name of the test method
     * @param phase Current phase (SETUP, ACTION, VERIFY, CLEANUP)
     * @param action Description of what's happening
     */
    public static void test(String testName, String phase, String action) {
        System.out.println(String.format("[TEST] %s | %s | %s", testName, phase, action));
    }

    /**
     * Log the current state of an object
     * @param className Name of the class
     * @param stateDescription String representation of object state
     */
    public static void state(String className, String stateDescription) {
        System.out.println(String.format("[STATE] %s | %s", className, stateDescription));
    }

    /**
     * Log verification of an assertion
     * @param description What is being verified
     * @param expected Expected value
     * @param actual Actual value
     * @param passed Whether the assertion passed
     */
    public static void verify(String description, Object expected, Object actual, boolean passed) {
        String status = passed ? "PASS ✓" : "FAIL ✗";
        System.out.println(String.format("[VERIFY] %s | Expected=%s, Actual=%s [%s]",
            description, expected, actual, status));
    }

    /**
     * Log the start of a test with a visual separator
     * @param testName Name of the test method
     */
    public static void startTest(String testName) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("▶ Starting Test: " + testName);
        System.out.println(SEPARATOR);
    }

    /**
     * Log the end of a test with a visual separator
     * @param testName Name of the test method
     * @param passed Whether all assertions passed
     */
    public static void endTest(String testName, boolean passed) {
        System.out.println(THIN_SEPARATOR);
        String status = passed ? "✓ PASSED" : "✗ FAILED";
        System.out.println("▶ Test Complete: " + testName + " - " + status);
        System.out.println(SEPARATOR + "\n");
    }

    /**
     * Log a section header within a test
     * @param sectionName Name of the section
     */
    public static void section(String sectionName) {
        System.out.println("\n" + THIN_SEPARATOR);
        System.out.println("  " + sectionName);
        System.out.println(THIN_SEPARATOR);
    }

    /**
     * Log detailed information (for debugging)
     * @param message Debug message
     */
    public static void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }
}
