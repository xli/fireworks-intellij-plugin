package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class EmptyStrShadow implements Shadow {
    private final String str;

    public EmptyStrShadow(String str) {
        this.str = str;
    }

    public void accept(ShadowVisitor visitor) {
    }

    public String toString() {
        return str;
    }
}
