package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.TestShadowResult;
import junit.framework.TestCase;

public class IgnoredTestCaseTest extends TestCase {
    private IgnoredTestCase test;

    protected void setUp() throws Exception {
        test = new IgnoredTestCase("className", "methodName");
    }

    public void testShouldBeIgnoredTest() throws Exception {
        TestShadowResult result = new TestShadowResult();
        test.run(result);
        assertEquals(1, result.ignoreCount());
        assertEquals(0, result.runCount());
        assertEquals(0, result.failureCount());
        assertEquals(0, result.errorCount());
    }
}
