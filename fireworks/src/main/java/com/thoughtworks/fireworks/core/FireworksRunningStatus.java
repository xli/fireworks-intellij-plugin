package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.fireworks.core.developer.Thought;

public class FireworksRunningStatus implements ShadowCabinetListener, Thought {
    private boolean working = false;

    public void afterAddTest(ComparableTestShadow test) {
    }

    public void afterRemoveTest(ComparableTestShadow test) {
    }

    public synchronized void endAction() {
        working = false;
    }

    public synchronized void startAction() {
        working = true;
    }

    public synchronized boolean working() {
        return working;
    }

    public boolean isWorking() {
        return working();
    }
}
