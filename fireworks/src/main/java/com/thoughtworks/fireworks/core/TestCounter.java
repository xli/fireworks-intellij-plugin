package com.thoughtworks.fireworks.core;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

import java.util.ArrayList;
import java.util.List;

public class TestCounter implements TestListener {
    private List listeners = new ArrayList();
    private int runCount;
    private int failuresCount;
    private int errorsCount;

    public void addError(Test test, Throwable t) {
        errorsCount++;
    }

    public void addFailure(Test test, AssertionFailedError t) {
        failuresCount++;
    }

    public void endTest(Test test) {
        fireEvent();
    }

    private void fireEvent() {
        for (int i = 0; i < listeners.size(); i++) {
            ((TestCounterListener) listeners.get(i)).testResult(runCount, failuresCount, errorsCount);
        }
    }

    public void startTest(Test test) {
        runCount++;
    }

    public void addListener(TestCounterListener listener) {
        listeners.add(listener);
    }

    public void start() {
        fireEvent();
    }
}
