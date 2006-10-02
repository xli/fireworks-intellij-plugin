package com.thoughtworks.shadow.ant;

import junit.framework.TestCase;

import java.net.URL;

public class UtilsTest extends TestCase {
    public void testShouldReturnNullIfPathStrsIsNullOrEmpty() throws Exception {
        assertNull(AntUtils.toPath(null, (URL[]) null));
        assertNull(AntUtils.toPath(null, new URL[0]));
    }
}
