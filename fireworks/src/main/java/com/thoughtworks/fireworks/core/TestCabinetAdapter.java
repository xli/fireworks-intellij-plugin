package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.Cabinet;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestCabinetAdapter implements Cabinet {
    private final Test test;

    public TestCabinetAdapter(Test test) {
        this.test = test;
    }

    public void action(TestResult result) {
        test.run(result);
    }
}
