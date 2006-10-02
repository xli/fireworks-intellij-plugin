package com.thoughtworks.fireworks.core;

import com.thoughtworks.fireworks.stubs.Success;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class ShadowCabinetActionNotificationTest extends TestCase {
    private TestResult result;
    private CabinetActionNotification actionNotification;

    protected void setUp() throws Exception {
        ShadowCabinet cabinet = new ShadowCabinet();
        cabinet.add(new ComparableTestShadow(new Success()));
        result = new TestResult();
        actionNotification = new CabinetActionNotification(cabinet, result);
    }

    public void testShouldActionWhenNotificationIsHappy() throws Exception {
        actionNotification.finished(false, 0, 0);
        TestUtils.assertTestResultRunOnceSuccessfully(result);
    }

    public void testShouldNotActionIfAbort() throws Exception {
        actionNotification.finished(true, 0, 0);
        assertEquals(0, result.runCount());
    }

    public void testShouldNotActionIfHasErrors() throws Exception {
        actionNotification.finished(false, 1, 0);
        assertEquals(0, result.runCount());
    }

    public void testShouldActionEvenIfThereAreWarnings() throws Exception {
        actionNotification.finished(false, 0, 5);
        TestUtils.assertTestResultRunOnceSuccessfully(result);
    }
}
