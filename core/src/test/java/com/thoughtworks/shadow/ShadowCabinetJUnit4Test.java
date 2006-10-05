package com.thoughtworks.shadow;

import com.thoughtworks.shadow.tests.*;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;
import junit.framework.TestListener;

public class ShadowCabinetJUnit4Test extends TestCase {
    private ShadowCabinet cabinet;

    protected void setUp() throws Exception {
        cabinet = new ShadowCabinet();
    }

    public void testShouldRunJUnit4TestSuccessfully() throws Exception {
        ShineTestClassShadow test = new ShineTestClassShadow(JU4Success.class.getName(), TestUtils.sunshine());
        cabinet.add(new ComparableTestShadow(test));
        TestShadowResult result = new TestShadowResult();
        Mock runListenerAdaptee = Turtle.mock(RunListenerAdaptee.class);
        result.addListener((TestListener) runListenerAdaptee.mockTarget());
        cabinet.action(result);
        assertEquals(1, result.runCount());
        assertEquals(0, result.failureCount());
        assertEquals(0, result.errorCount());
        assertEquals(0, result.ignoreCount());
    }

    public void testShouldRunJUnit4TestFailed() throws Exception {
        ShineTestClassShadow test = new ShineTestClassShadow(JU4Failure.class.getName(), TestUtils.sunshine());
        cabinet.add(new ComparableTestShadow(test));
        TestShadowResult result = new TestShadowResult();
        Mock runListenerAdaptee = Turtle.mock(RunListenerAdaptee.class);
        result.addListener((TestListener) runListenerAdaptee.mockTarget());
        cabinet.action(result);
        assertEquals(1, result.runCount());
        assertEquals(1, result.failureCount());
        assertEquals(0, result.errorCount());
        assertEquals(0, result.ignoreCount());
    }

    public void testShouldRunJUnit4TestIgnored() throws Exception {
        ShineTestClassShadow test = new ShineTestClassShadow(JU4Ignore.class.getName(), TestUtils.sunshine());
        cabinet.add(new ComparableTestShadow(test));
        TestShadowResult result = new TestShadowResult();
        Mock runListenerAdaptee = Turtle.mock(RunListenerAdaptee.class);
        result.addListener((TestListener) runListenerAdaptee.mockTarget());
        cabinet.action(result);
        assertEquals(0, result.runCount());
        assertEquals(0, result.failureCount());
        assertEquals(0, result.errorCount());
        assertEquals(1, result.ignoreCount());
    }

    public void testParseTestFailedMessage() throws Exception {
        ShineTestClassShadow test = new ShineTestClassShadow(JU4Failure.class.getName(), TestUtils.sunshine());
        cabinet.add(new ComparableTestShadow(test));
        TestShadowResult result = new TestShadowResult();
        Mock runListenerAdaptee = Turtle.mock(RunListenerAdaptee.class);
        result.addListener((TestListener) runListenerAdaptee.mockTarget());
        cabinet.action(result);

        assertEquals(JU4Failure.FAILED_MESSAGE, result.failures().nextElement().thrownException().getMessage());
    }

    public void testParseTestFailedMessageFromTraceLog() throws Exception {
        ShineTestClassShadow test = new ShineTestClassShadow(JU4ThrowRuntimeException.class.getName(), TestUtils.sunshine());
        cabinet.add(new ComparableTestShadow(test));
        TestShadowResult result = new TestShadowResult();
        Mock runListenerAdaptee = Turtle.mock(RunListenerAdaptee.class);
        result.addListener((TestListener) runListenerAdaptee.mockTarget());
        cabinet.action(result);

        assertEquals(JU4ThrowRuntimeException.FAILED_MESSAGE, result.failures().nextElement().thrownException().getMessage());
    }

    public void testAllTypesIncluded() throws Exception {
        ShineTestClassShadow test = new ShineTestClassShadow(JU4AllTypes.class.getName(), TestUtils.sunshine());
        cabinet.add(new ComparableTestShadow(test));
        TestShadowResult result = new TestShadowResult();
        Mock runListenerAdaptee = Turtle.mock(RunListenerAdaptee.class);
        result.addListener((TestListener) runListenerAdaptee.mockTarget());
        cabinet.action(result);
        assertEquals(2, result.runCount());
        assertEquals(1, result.failureCount());
        assertEquals(0, result.errorCount());
        assertEquals(1, result.ignoreCount());
    }

}
