package com.productionchain.enginedatatypes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Utility class for loading resources from the classpath in a platform-independent way.
 * This replaces hardcoded absolute paths with relative resource loading.
 */
public class ResourceLoader {

    /**
     * Get the absolute path to a resource file in the resources directory.
     *
     * @param relativePath Path relative to src/main/resources (e.g., "buildingtypes/ProductionBuildings.json")
     * @return Absolute path to the resource file
     * @throws IOException if resource not found
     */
    public static String getResourcePath(String relativePath) throws IOException {
        URL resource = ResourceLoader.class.getClassLoader().getResource(relativePath);
        if (resource == null) {
            throw new IOException("Resource not found: " + relativePath);
        }
        try {
            return new File(resource.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new IOException("Invalid resource URI: " + relativePath, e);
        }
    }

    /**
     * Get an InputStream for reading a resource file.
     *
     * @param relativePath Path relative to src/main/resources
     * @return InputStream for the resource
     * @throws IOException if resource not found
     */
    public static InputStream getResourceAsStream(String relativePath) throws IOException {
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(relativePath);
        if (stream == null) {
            throw new IOException("Resource not found: " + relativePath);
        }
        return stream;
    }

    /**
     * Get the folder path containing a specific resource.
     *
     * @param relativePath Path to a file relative to src/main/resources
     * @return Absolute path to the folder containing the resource
     * @throws IOException if resource not found
     */
    public static String getResourceFolderPath(String relativePath) throws IOException {
        String fullPath = getResourcePath(relativePath);
        return new File(fullPath).getParent();
    }

    /**
     * Check if a resource exists.
     *
     * @param relativePath Path relative to src/main/resources
     * @return true if resource exists, false otherwise
     */
    public static boolean resourceExists(String relativePath) {
        return ResourceLoader.class.getClassLoader().getResource(relativePath) != null;
    }
}
