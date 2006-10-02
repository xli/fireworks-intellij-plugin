package com.thoughtworks.fireworks.core;

import junit.framework.TestCase;
import junit.framework.TestResult;

public class TestCounterTest extends TestCase implements TestCounterListener {
    private int runCount;
    private int failureCount;
    private int errorCount;
    private TestCounter counter;
    private TestResult result;

    protected void setUp() throws Exception {
        runCount = -1;
        failureCount = -1;
        errorCount = -1;
        counter = new TestCounter();
        counter.addListener(this);
        result = new TestResult();
        result.addListener(counter);
    }

    public void testShouldFireAsCabinetListener() throws Exception {
        counter.start();
        assertEquals(0, runCount);
        assertEquals(0, failureCount);
        assertEquals(0, errorCount);
    }

    public void testShouldFireListenerWithTestResult() throws Exception {
        result.startTest(this);
        result.endTest(this);

        result.startTest(this);
        result.addError(this, null);
        result.endTest(this);

        result.startTest(this);
        result.addFailure(this, null);
        result.endTest(this);

        assertEquals(runCount, result.runCount());
        assertEquals(failureCount, result.failureCount());
        assertEquals(errorCount, result.errorCount());
    }

    public void testResult(int runCount, int failureCount, int errorCount) {
        this.runCount = runCount;
        this.failureCount = failureCount;
        this.errorCount = errorCount;
    }
}
