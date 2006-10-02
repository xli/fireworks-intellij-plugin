package com.thoughtworks.fireworks.stubs;

import com.thoughtworks.shadow.ShadowVisitor;

public class SimpleShadowVisitor implements ShadowVisitor {
    public String testClassName;
    public String testMethodName;

    public void visitTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public void visitTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public void end() {
    }
}
