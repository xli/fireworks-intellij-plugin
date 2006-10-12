/*
 *    Copyright (c) 2006 LiXiao.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.tree.ShadowTreeNode;
import com.thoughtworks.fireworks.core.tree.TestStatusSummaryListener;
import com.thoughtworks.shadow.*;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class TestShadowsStatus implements ShadowCabinetListener, RunListenerAdaptee, TestStateListener {
    private Map<Object, Icon> testIcons = new HashMap();
    private final ShadowTreeNode rootKey;
    private final TestStatusSummaryListener[] listens;
    private boolean wasSuccessful;
    private boolean testWasSuccessful;
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
        this.wasSuccessful = wasSuccessful && testCount > 0;
        changeStatus(rootKey, wasSuccessful);
    }

    public void startAction() {
        testCount = 0;
        wasSuccessful = true;
    }

    public void addError(Test test, Throwable t) {
        testWasSuccessful = false;
        wasSuccessful = false;
    }

    public void addFailure(Test test, AssertionFailedError t) {
        addError(test, t);
    }

    public void testIgnored(TestShadow testShadow) {
        put(testShadow, Icons.ignoredIcon());
    }

    public void endTest(Test test) {
        changeStatus(test, testWasSuccessful);
    }

    public void startTest(Test test) {
        testWasSuccessful = true;
        testCount++;
    }

    public void endTestShadow(ComparableTestShadow shadow, boolean wasSuccessful) {
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
