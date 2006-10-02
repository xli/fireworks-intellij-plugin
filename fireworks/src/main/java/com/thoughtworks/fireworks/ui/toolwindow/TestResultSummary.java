package com.thoughtworks.fireworks.ui.toolwindow;

import com.thoughtworks.fireworks.core.TestCounterListener;
import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.TestResultStatus;

import javax.swing.*;

public class TestResultSummary extends JLabel implements TestCounterListener {

    public TestResultSummary() {
        super(new TestResultStatus(0, 0, 0).summary());
        setBackground(Utils.TRANSPARENT_WHITE);
    }

    public void testResult(int runCount, int failureCount, int errorCount) {
        setText(new TestResultStatus(runCount, failureCount, errorCount).summary());
    }
}
