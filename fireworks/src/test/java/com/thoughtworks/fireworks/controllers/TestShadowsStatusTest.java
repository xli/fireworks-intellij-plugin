package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.tree.ShadowSummaryTreeNode;
import com.thoughtworks.fireworks.core.tree.TestStatusSummaryListener;
import com.thoughtworks.fireworks.stubs.FailureTestShadow;
import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import junit.framework.TestCase;
import junit.framework.TestResult;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class TestShadowsStatusTest extends TestCase implements TestStatusSummaryListener {
    private Map<Object, Object> icons = new HashMap();
    private ShadowSummaryTreeNode rootKey;
    private TestShadowsStatus status;
    private ShadowCabinet cabinet;
    private TestResult result;
    private ComparableTestShadow successfulShadow;
    private ComparableTestShadow failureShadow;
    private FailureTestShadow failureTest;
    private Icon summary;

    protected void setUp() throws Exception {
        rootKey = new ShadowSummaryTreeNode();
        status = new TestShadowsStatus(rootKey, new TestStatusSummaryListener[]{this});
        cabinet = new ShadowCabinet();
        cabinet.addListener(status);
        result = new TestResult();
        result.addListener(status);
        successfulShadow = new ComparableTestShadow(new SuccessTestShadow());
        successfulShadow.addListener(status);

        failureTest = new FailureTestShadow();
        failureShadow = new ComparableTestShadow(failureTest);
        failureShadow.addListener(status);
    }

    public void testShouldBeActionPendingAfterStatusInited() throws Exception {
        assertEquals(Icons.actionPending(), getIcon(rootKey));
    }

    public void testSummaryShouldBeValueOfRootKey() throws Exception {
        assertSame(summary, getIcon(rootKey));
    }

    public void testShouldBePendingAfterTheTestShadowAddIntoCabinet() throws Exception {
        cabinet.add(successfulShadow);
        cabinet.add(failureShadow);
        assertEquals(Icons.pending(), getIcon(successfulShadow));
        assertEquals(Icons.pending(), getIcon(failureShadow));
    }

    public void testShouldRemoveStatusAfterTestWasRemoved() throws Exception {
        cabinet.add(successfulShadow);
        cabinet.remove(successfulShadow);
        assertNull(getIcon(successfulShadow));
    }

    public void testOneSucceessfulTest() throws Exception {
        cabinet.add(successfulShadow);
        cabinet.action(result);
        assertEquals(Icons.successIcon(), getIcon(rootKey));
        assertEquals(Icons.successIcon(), getIcon(successfulShadow));
        assertSame(summary, getIcon(rootKey));
    }

    public void testOneFailureTest() throws Exception {
        cabinet.add(failureShadow);
        cabinet.action(result);
        assertEquals(Icons.failureIcon(), getIcon(rootKey));
        assertEquals(Icons.failureIcon(), getIcon(failureShadow));
        assertEquals(Icons.failureIcon(), getIcon(failureTest));
        assertSame(summary, getIcon(rootKey));
    }

    public void testOneFailureTestAndOneSuccessfulTest() throws Exception {
        cabinet.add(failureShadow);
        cabinet.add(successfulShadow);
        cabinet.action(result);
        assertEquals(Icons.failureIcon(), getIcon(rootKey));
        assertEquals(Icons.failureIcon(), getIcon(failureShadow));
        assertEquals(Icons.successIcon(), getIcon(successfulShadow));
        assertSame(summary, getIcon(rootKey));
    }


    public void testShouldBeFailureIconIfNoTestRan() throws Exception {
        cabinet.action(result);
        assertEquals(Icons.failureIcon(), getIcon(rootKey));
    }

    public void summaryStatusChanged(Icon status) {
        summary = status;
    }

    private Object getIcon(Object key) {
        return icons.get(key);
    }

    public void statusChanged(Object key, Icon status) {
        icons.put(key, status);
    }
}
