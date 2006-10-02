package com.thoughtworks.shadow.tests;

import junit.framework.TestCase;

public class AllTypes extends TestCase {

    public static String[] getTestMethodNames() {
        return new String[]{"testSuccess",
                "testShouldFailed",
                "testShouldThrowException"};
    }

    public void testShouldThrowException() throws Exception {
        throw new Exception("cause a junit error");
    }

    public void testShouldFailed() throws Exception {
        fail("cause a junit failure");
    }

    public void testSuccess() {
    }

}
