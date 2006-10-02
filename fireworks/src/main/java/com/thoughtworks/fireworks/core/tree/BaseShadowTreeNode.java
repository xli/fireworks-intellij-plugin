package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public abstract class BaseShadowTreeNode implements ShadowTreeNode, ShadowVisitor {
    private final ShadowTreeNode parent;
    private final Shadow shadow;

    public BaseShadowTreeNode(ShadowTreeNode parent, Shadow shadow) {
        this.shadow = shadow;
        this.parent = parent;
        shadow.accept(this);
    }

    public ShadowTreeNode parent() {
        return parent;
    }

    public boolean isSource() {
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BaseShadowTreeNode))
            return false;

        final BaseShadowTreeNode that = (BaseShadowTreeNode) o;

        return shadow.equals(that.shadow);
    }

    public int hashCode() {
        return shadow.hashCode();
    }

    public void accept(ShadowVisitor visitor) {
        shadow.accept(visitor);
    }
}
