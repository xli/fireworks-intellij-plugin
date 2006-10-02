package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.TestCounterListener;

import java.awt.*;

public class TestResultSummaryBgColor implements TestCounterListener {
    private final TestResultSummaryBgColorListener[] listeners;
    private final int level = 10;

    public TestResultSummaryBgColor(TestResultSummaryBgColorListener[] listeners) {
        this.listeners = listeners;
    }

    public void testResult(int runCount, int failureCount, int errorCount) {
        fireEvent(getColor(runCount, failureCount + errorCount));
    }

    private Color getColor(int runCount, int failureAndErrorCount) {
        if (runCount == 0) {
            return Color.RED;
        }

        return new Color(red(failureAndErrorCount), green(runCount, failureAndErrorCount), 0);
    }

    private int green(int runCount, int failureAndErrorCount) {
        if (failureAndErrorCount < 2) {
            return 255;
        }
        if (failureAndErrorCount > level) {
            return 0;
        }

        return 255 - 255 * failureAndErrorCount / runCount;
    }

    private int red(int failureAndErrorCount) {
        return failureAndErrorCount > 0 ? 255 : 0;
    }

    private void fireEvent(Color color) {
        for (TestResultSummaryBgColorListener listener : listeners) {
            listener.setBackground(color);
        }
    }
}
