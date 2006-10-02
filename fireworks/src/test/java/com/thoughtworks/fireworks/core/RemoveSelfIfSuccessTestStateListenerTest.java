package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import com.thoughtworks.shadow.TestStateListener;
import junit.framework.TestCase;

public class RemoveSelfIfSuccessTestStateListenerTest extends TestCase {
    private TestStateListener listener;
    private ComparableTestShadow shadow;
    private ShadowCabinet cabinet;

    protected void setUp() throws Exception {
        listener = new RemoveSelfIfSuccessTestStateListener();
        shadow = new ComparableTestShadow(this);
        cabinet = new ShadowCabinet();
        cabinet.add(shadow);
    }

    public void testShouldRemoveSelfFromCabinetIfTestIsSuccessfully() throws Exception {
        listener.endTestShadow(shadow, true, 1);
        assertEquals(0, cabinet.size());
    }

    public void testShouldNotRemoveSelfIfTestIsFailure() throws Exception {
        listener.endTestShadow(shadow, false, 1);
        assertEquals(1, cabinet.size());
    }
}
