package com.thoughtworks.shadow;

import junit.framework.TestCase;

import java.io.File;
import java.net.URL;

public class UtilsTest extends TestCase {
    public void testLoadClassFromClasspaths() throws Exception {
        URL[] classpaths = new URL[]{new File("src/test/class").toURL()};
        Class outClasspathClass = Utils.load(classpaths, "ClassOutOfClasspath");
        assertNotNull(outClasspathClass);
    }

    public void testShouldThrowNullpointerException() throws Exception {
        try {
            Utils.load(new URL[0], null);
            fail();
        } catch (NullPointerException e) {
            assertNotNull(e.getMessage());
        }
    }
}
