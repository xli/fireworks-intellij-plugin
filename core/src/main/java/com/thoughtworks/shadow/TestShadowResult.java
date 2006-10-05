package com.thoughtworks.shadow;

import junit.framework.TestListener;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.List;

public class TestShadowResult extends TestResult {
    private int ignoreCount = 0;
    private List<TestListener> listeners = new ArrayList();

    public int ignoreCount() {
        return ignoreCount;
    }

    public void testIgnored(TestShadow testShadow) {
        ignoreCount++;
        fireTestIgnoredEvent(testShadow);
    }

    private void fireTestIgnoredEvent(TestShadow testShadow) {
        for (int i = 0; i < listeners.size(); i++) {
            TestListener listener = listeners.get(i);
            if (listener instanceof RunListenerAdaptee) {
                ((RunListenerAdaptee) listener).testIgnored(testShadow);
            }
        }
    }

    public synchronized void addListener(TestListener listener) {
        super.addListener(listener);
        listeners.add(listener);
    }

    public synchronized void removeListener(TestListener listener) {
        super.removeListener(listener);
        listeners.remove(listener);
    }
}
