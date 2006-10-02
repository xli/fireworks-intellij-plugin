package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.TestStateListener;

public class RemoveSelfIfSuccessTestStateListener implements TestStateListener {
    public void endTestShadow(ComparableTestShadow shadow, boolean wasSuccessful, int times) {
        if (wasSuccessful) {
            shadow.removeSelfFromContainer();
        }
    }
}
