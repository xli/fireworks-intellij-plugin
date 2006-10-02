package com.thoughtworks.fireworks.stubs;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestShadow;
import junit.framework.TestCase;

public class FailureTestShadow extends TestCase implements TestShadow {
    public static final String TEST_METHOD = "testShouldFail";

    public FailureTestShadow() {
        super(TEST_METHOD);
    }

    public void testShouldFail() throws Exception {
        fail();
    }

    public void accept(ShadowVisitor visitor) {
        visitor.visitTestClassName(getClass().getName());
        visitor.visitTestMethodName(TEST_METHOD);
        visitor.end();
    }
}

