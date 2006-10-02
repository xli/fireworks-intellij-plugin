package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.tree.ShadowTreeNode;
import com.thoughtworks.fireworks.core.tree.TestStatusSummaryListener;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.TestStateListener;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class TestShadowsStatus implements ShadowCabinetListener, TestListener, TestStateListener {
    private Map<Object, Icon> testIcons = new HashMap();
    private final ShadowTreeNode rootKey;
    private final TestStatusSummaryListener[] listens;
    private boolean wasSuccessful;
    private boolean testStatus;
    private int testCount;

    public TestShadowsStatus(ShadowTreeNode rootKey, TestStatusSummaryListener[] listens) {
        this.rootKey = rootKey;
        this.listens = listens;
        put(rootKey, Icons.actionPending());
    }

    public void afterAddTest(ComparableTestShadow test) {
        put(test, Icons.pending());
    }

    public void afterRemoveTest(ComparableTestShadow test) {
        testIcons.remove(test);
        fireStatusChangedEvent(test, null);
    }

    public void endAction() {
        this.wasSuccessful = wasSuccessful & testCount > 0;
        changeStatus(rootKey, wasSuccessful);
    }

    public void startAction() {
        testCount = 0;
        wasSuccessful = true;
    }

    public void addError(Test test, Throwable t) {
        testStatus = false;
        wasSuccessful = false;
    }

    public void addFailure(Test test, AssertionFailedError t) {
        addError(test, t);
    }

    public void endTest(Test test) {
        changeStatus(test, testStatus);
    }

    public void startTest(Test test) {
        testStatus = true;
        testCount++;
    }

    public void endTestShadow(ComparableTestShadow shadow, boolean wasSuccessful, int times) {
        changeStatus(shadow, wasSuccessful);
    }

    public Icon getIcon(Object key) {
        return testIcons.get(key);
    }

    private void changeStatus(Object key, boolean wasSuccessful) {
        if (!wasSuccessful && this.wasSuccessful) {
            this.wasSuccessful = wasSuccessful;
            put(rootKey, Icons.failureIcon());
        }
        put(key, wasSuccessful ? Icons.successIcon() : Icons.failureIcon());
    }

    private void put(Object key, Icon status) {
        testIcons.put(key, status);
        fireStatusChangedEvent(key, status);
        if (key == rootKey) {
            fireSummaryEvent(status);
        }
    }

    private void fireStatusChangedEvent(Object key, Icon status) {
        for (TestStatusSummaryListener listener : listens) {
            listener.statusChanged(key, status);
        }
    }

    private void fireSummaryEvent(Icon status) {
        for (TestStatusSummaryListener listener : listens) {
            listener.summaryStatusChanged(status);
        }
    }
}
