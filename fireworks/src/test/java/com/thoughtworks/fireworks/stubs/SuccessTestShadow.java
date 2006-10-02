package com.thoughtworks.fireworks.stubs;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestShadow;
import junit.framework.TestCase;

public class SuccessTestShadow extends TestCase implements TestShadow {
    public SuccessTestShadow() {
        super("testSuccess");
    }

    public void testSuccess() throws Exception {
    }

    public void accept(ShadowVisitor visitor) {
    }
}
