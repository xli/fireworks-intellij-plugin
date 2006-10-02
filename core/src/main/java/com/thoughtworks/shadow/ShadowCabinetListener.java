package com.thoughtworks.shadow;

public interface ShadowCabinetListener {
    void afterAddTest(ComparableTestShadow test);

    void afterRemoveTest(ComparableTestShadow test);

    void endAction();

    void startAction();
}
