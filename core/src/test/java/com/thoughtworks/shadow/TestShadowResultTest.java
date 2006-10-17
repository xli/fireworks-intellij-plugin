package com.thoughtworks.shadow;

import com.thoughtworks.shadow.junit.IgnoredTestCase;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;
import junit.framework.TestListener;

public class TestShadowResultTest extends TestCase {
    private TestShadowResult result;
    private IgnoredTestCase ignoredTestCase;

    protected void setUp() throws Exception {
        result = new TestShadowResult();
        ignoredTestCase = new IgnoredTestCase("", "");
    }

    public void testShouldFireTestIgnoredEvent() throws Exception {
        Mock runListenerAdaptee = Turtle.mock(RunListenerAdaptee.class);
        result.addListener((TestListener) runListenerAdaptee.mockTarget());

        ignoredTestCase.run(result);

        runListenerAdaptee.assertDid("testIgnored").with(ignoredTestCase);
    }

    public void testShouldLogAsRunTestIfTestIsIgnored() throws Exception {
        ignoredTestCase.run(result);

        assertEquals(0, result.runCount());
        assertEquals(0, result.failureCount());
        assertEquals(0, result.errorCount());
    }
}
