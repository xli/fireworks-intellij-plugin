package com.thoughtworks.fireworks.core;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    public void testGetJavaVersion() throws Exception {
        assertEquals("1.4", Utils.getJavaVersion("JDK1.4.2._08"));
        assertEquals("1.3", Utils.getJavaVersion("1.3"));
        try {
            Utils.getJavaVersion("");
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
        try {
            Utils.getJavaVersion("1.");
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
        try {
            Utils.getJavaVersion("jdk.1");
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }
}
