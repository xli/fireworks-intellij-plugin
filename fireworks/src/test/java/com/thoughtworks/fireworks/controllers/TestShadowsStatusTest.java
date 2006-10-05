/*
 *    Copyright (c) 2006 LiXiao.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.tree.ShadowSummaryTreeNode;
import com.thoughtworks.fireworks.core.tree.TestStatusSummaryListener;
import com.thoughtworks.fireworks.stubs.FailureTestShadow;
import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import com.thoughtworks.shadow.TestShadowResult;
import com.thoughtworks.shadow.junit.IgnoredTestCase;
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
        result = new TestShadowResult();
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

    public void testOneIgnoredTest() throws Exception {
        IgnoredTestCase ignoredTest = new IgnoredTestCase("class", "method");
        ComparableTestShadow shadow = new ComparableTestShadow(ignoredTest);
        shadow.addListener(status);
        cabinet.add(shadow);
        cabinet.action(result);
        assertEquals(Icons.failureIcon(), getIcon(rootKey));
        assertEquals(Icons.successIcon(), getIcon(shadow));
        assertEquals(Icons.ignoredIcon(), getIcon(ignoredTest));
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
