package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.ComparableTestShadow;

public class FireworksRunningStatus implements ShadowCabinetListener {
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
}
