package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class ShadowStrDecorator implements Shadow {
    private final Shadow shadow;
    private final String str;

    public ShadowStrDecorator(Shadow shadow, String str) {
        this.shadow = shadow;
        this.str = str;
    }

    public void accept(ShadowVisitor visitor) {
        shadow.accept(visitor);
    }

    public String toString() {
        return str;
    }
}
