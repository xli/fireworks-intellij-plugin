package com.thoughtworks.shadow;

public interface TestStateListener {
    void endTestShadow(ComparableTestShadow shadow, boolean wasSuccessful, int times);
}
