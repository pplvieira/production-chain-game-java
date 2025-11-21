package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Test;
import com.productionchain.enginedatatypes.ResourceLoader;
import java.io.IOException;

/**
 * Unit tests for ResourceLoader utility class.
 * Tests resource loading functionality across different platforms.
 */
public class ResourceLoaderTest {

    @Test
    public void testGetResourcePath_ValidResource() throws IOException {
        // Test loading a valid resource
        String path = ResourceLoader.getResourcePath("logback.xml");
        assertNotNull("Resource path should not be null", path);
        assertTrue("Resource path should contain 'logback.xml'", path.contains("logback.xml"));
    }

    @Test(expected = IOException.class)
    public void testGetResourcePath_InvalidResource() throws IOException {
        // Test loading an invalid resource - should throw IOException
        ResourceLoader.getResourcePath("nonexistent-file.txt");
    }

    @Test
    public void testResourceExists_ValidResource() {
        // Test checking if a valid resource exists
        assertTrue("logback.xml should exist", ResourceLoader.resourceExists("logback.xml"));
    }

    @Test
    public void testResourceExists_InvalidResource() {
        // Test checking if an invalid resource exists
        assertFalse("Nonexistent file should return false", ResourceLoader.resourceExists("nonexistent-file.txt"));
    }

    @Test
    public void testGetResourceAsStream_ValidResource() throws IOException {
        // Test getting InputStream for a valid resource
        assertNotNull("InputStream should not be null", ResourceLoader.getResourceAsStream("logback.xml"));
    }

    @Test(expected = IOException.class)
    public void testGetResourceAsStream_InvalidResource() throws IOException {
        // Test getting InputStream for an invalid resource
        ResourceLoader.getResourceAsStream("nonexistent-file.txt");
    }
}
