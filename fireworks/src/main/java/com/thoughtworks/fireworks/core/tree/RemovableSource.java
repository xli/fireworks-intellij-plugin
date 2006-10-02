package com.thoughtworks.fireworks.core.tree;

public interface RemovableSource {
    boolean isSource();

    boolean isRemovable();

    void removeSelf();
}
