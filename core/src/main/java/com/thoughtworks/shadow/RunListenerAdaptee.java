package com.thoughtworks.shadow;

import junit.framework.TestListener;

public interface RunListenerAdaptee extends TestListener {
    /**
     * @see org.junit.runner.notification.RunListener
     */
    public void testIgnored(TestShadow testShadow);
}
