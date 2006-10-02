package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class ShadowMethodTreeNode extends BaseShadowTreeNode implements ShadowVisitor {
    private String accessory;
    private String label;

    public ShadowMethodTreeNode(Shadow shadow, ShadowTreeNode parent) {
        super(parent, shadow);
    }

    public ShadowMethodTreeNode(Shadow shadow) {
        this(shadow, null);
    }

    public boolean isRemovable() {
        return false;
    }

    public void removeSelf() {
    }

    public String label() {
        return label;
    }

    public String accessory() {
        return accessory;
    }

    public void visitTestClassName(String testClassName) {
        accessory = testClassName;
    }

    public void visitTestMethodName(String testMethodName) {
        label = testMethodName;
    }

    public void end() {
    }

}
