package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class JumpToClassShadow implements Shadow {
    private final String className;

    public JumpToClassShadow(String className) {
        this.className = className;
    }

    public void accept(ShadowVisitor visitor) {
        visitor.visitTestClassName(className);
        visitor.end();
    }

    public String toString() {
        return className;
    }
}

