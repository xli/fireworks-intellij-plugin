package com.thoughtworks.shadow.junit;

import junit.framework.Protectable;

public class ProtectableFactory {
    public static Protectable protectable(final Throwable throwable) {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                throw throwable;
            }
        };
        return protectable;
    }
}
