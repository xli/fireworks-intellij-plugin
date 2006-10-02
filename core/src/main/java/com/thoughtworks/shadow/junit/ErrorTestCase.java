package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.ShadowVisitor;
import junit.framework.Protectable;

public class ErrorTestCase extends BaseTestCase {
    public ErrorTestCase(String testName, Protectable protectable) {
        super(testName, null, protectable);
    }

    public void accept(ShadowVisitor visitor) {
    }
}
