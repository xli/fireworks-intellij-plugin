package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.shadow.ComparableTestShadow;

public class ShadowClassTreeNode extends BaseShadowTreeNode {
    private final ComparableTestShadow shadow;
    private String className;
    private String packageName;

    public ShadowClassTreeNode(ComparableTestShadow shadow, ShadowTreeNode parent) {
        super(parent, shadow);
        this.shadow = shadow;
    }

    public ShadowClassTreeNode(ComparableTestShadow shadow) {
        this(shadow, null);
    }

    public boolean isRemovable() {
        return true;
    }

    public void removeSelf() {
        shadow.removeSelfFromContainer();
    }

    public String label() {
        return className;
    }

    public String accessory() {
        return packageName;
    }

    public void visitTestClassName(String testClassName) {
        int index = testClassName.lastIndexOf(".");
        className = testClassName.substring(index + 1);
        if (index >= 0) {
            packageName = testClassName.substring(0, index);
        }
    }

    public void visitTestMethodName(String testMethodName) {
    }

    public void end() {
    }
}
