package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.shadow.ShadowVisitor;

public class ShadowSummaryTreeNode implements ShadowTreeNode {
    private final FireworksConfig config;
    private int childCount;

    public ShadowSummaryTreeNode(FireworksConfig config) {
        this.config = config;
    }

    public ShadowSummaryTreeNode() {
        this(null);
    }

    public boolean isRemovable() {
        return false;
    }

    public void removeSelf() {
    }

    public boolean isSource() {
        return false;
    }

    public String label() {
        return childCount + " Test Class";
    }

    public String accessory() {
        if (config == null) {
            return "";
        }
        return "Max: " + config.maxSize();
    }

    public ShadowTreeNode parent() {
        return null;
    }

    public void childrenIncreased() {
        childCount++;
    }

    public void childrenDecreased() {
        childCount--;
    }

    public void accept(ShadowVisitor visitor) {
    }
}
