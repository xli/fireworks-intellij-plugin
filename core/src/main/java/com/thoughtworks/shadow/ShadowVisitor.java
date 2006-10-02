package com.thoughtworks.shadow;

public interface ShadowVisitor {
    void visitTestClassName(String testClassName);

    void visitTestMethodName(String testMethodName);

    void end();
}
