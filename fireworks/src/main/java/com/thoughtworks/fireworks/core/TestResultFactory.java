package com.thoughtworks.fireworks.core;

import junit.framework.TestListener;
import junit.framework.TestResult;

public class TestResultFactory {
    private final TestListener[] testListeners;
    private final TestCounterListener[] counterListeners;

    public TestResultFactory(TestListener[] testListeners, TestCounterListener[] counterListeners) {
        this.testListeners = testListeners == null ? new TestListener[0] : testListeners;
        this.counterListeners = counterListeners == null ? new TestCounterListener[0] : counterListeners;
    }

    public TestResult createTestResult() {
        TestResult result = new TestResult();
        result.addListener(getTestCounter());
        for (int i = 0; i < testListeners.length; i++) {
            result.addListener(testListeners[i]);
        }
        return result;
    }

    private TestCounter getTestCounter() {
        TestCounter counter = new TestCounter();
        for (int i = 0; i < counterListeners.length; i++) {
            counter.addListener(counterListeners[i]);
        }
        counter.start();
        return counter;
    }
}
