package com.thoughtworks.fireworks.stubs;

import junit.framework.TestCase;

public class Failure extends TestCase {
    public void testShouldFail() throws Exception {
        fail();
    }
}
