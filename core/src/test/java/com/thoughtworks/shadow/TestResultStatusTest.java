package com.thoughtworks.shadow;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class TestResultStatusTest extends TestCase {
    public void testShouldBeFailureStatusIfThereAreErrorsOrFailures() throws Exception {
        assertFalse(new TestResultStatus(1, 0, 1).wasSuccessful());
        assertFalse(new TestResultStatus(2, 1, 1).wasSuccessful());
        assertFalse(new TestResultStatus(2, 1, 0).wasSuccessful());
    }

    public void testShouldBeFailureStatusIfRunCountIsZero() throws Exception {
        assertFalse(new TestResultStatus(0, 0, 0).wasSuccessful());
    }

    public void testShouldBeNoTestIfRunCountIsZero() throws Exception {
        assertTrue(new TestResultStatus(0, 0, 0).isNoTest());
        assertFalse(new TestResultStatus(1, 0, 0).isNoTest());
    }

    public void testShouldBeSuccessfulIfHasTestRanAndThereAreNotErrorsOrFailures() throws Exception {
        assertTrue(new TestResultStatus(2, 0, 0).wasSuccessful());
        assertTrue(new TestResultStatus(1, 0, 0).wasSuccessful());
    }

    public void testInitByATestResult() throws Exception {
        TestResult testResult = new TestResult();
        assertFalse(new TestResultStatus(testResult).wasSuccessful());
        assertTrue(new TestResultStatus(testResult).isNoTest());

        testResult.startTest(this);
        assertTrue(new TestResultStatus(testResult).wasSuccessful());
        assertFalse(new TestResultStatus(testResult).isNoTest());

        testResult.addFailure(this, new AssertionFailedError());
        assertFalse(new TestResultStatus(testResult).wasSuccessful());
        assertFalse(new TestResultStatus(testResult).isNoTest());

        testResult = new TestResult();
        testResult.startTest(this);
        testResult.addError(this, new AssertionFailedError());
        assertFalse(new TestResultStatus(testResult).wasSuccessful());
        assertFalse(new TestResultStatus(testResult).isNoTest());
    }

    public void testSummary() throws Exception {
        assertEquals("Tests run: 1, Failures: 0, Errors: 0", new TestResultStatus(1, 0, 0).summary());
    }
}
