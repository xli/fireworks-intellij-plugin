package com.thoughtworks.shadow.tests;

import junit.framework.TestCase;

import java.text.MessageFormat;

public class Failure extends TestCase {
    public static String getTestName() {
        return MessageFormat.format("testShouldFailed({0})", new Object[]{Failure.class.getName()});
    }

    public Failure() {
        super("testShouldFailed");
    }

    public void testShouldFailed() throws Exception {
        fail("cause a junit failure");
    }
}
