package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.shadow.Shadow;

public interface ShadowTreeNode extends Shadow, RemovableSource {
    String label();

    String accessory();

    ShadowTreeNode parent();
}
