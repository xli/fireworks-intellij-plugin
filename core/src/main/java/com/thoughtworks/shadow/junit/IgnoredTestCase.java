package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.TestShadowResult;
import junit.framework.TestResult;

public class IgnoredTestCase extends SuccessfulTestCase {

    public IgnoredTestCase(String className, String methodName) {
        super(className, methodName);
    }

    public void run(TestResult result) {
        if (result instanceof TestShadowResult) {
            ((TestShadowResult) result).testIgnored(this);
        }
    }
}
