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
package com.thoughtworks.fireworks.core;

import com.thoughtworks.fireworks.stubs.Success;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.TestStateListener;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class IntellijShadowCabinetTest extends TestCase implements ShadowCabinetListener, TestStateListener {
    private ShadowCabinetListener[] listeners;
    private CompilerManagerAdaptee compilerManager;
    private IntellijShadowCabinet cabinet;
    private ComparableTestShadow testAdded;
    private ComparableTestShadow testRemoved;
    private ComparableTestShadow endTestShadowEventShadow;
    private Sunshine sunshine;

    protected void setUp() throws Exception {
        testAdded = null;
        testRemoved = null;
        endTestShadowEventShadow = null;

        listeners = new ShadowCabinetListener[]{this};
        compilerManager = new SimpleCompilerManagerAdapter();
        cabinet = new IntellijShadowCabinet(listeners, compilerManager, this);
        sunshine = TestUtils.sunshine();
        cabinet.addTestShadow(sunshine, Success.class.getName());
    }

    public void testShouldAddTestStateListenerIntoShadow() throws Exception {
        cabinet.action(new TestResult());
        assertEquals(testAdded, endTestShadowEventShadow);
    }

    public void testCabinetAction() throws Exception {
        TestResult result = new TestResult();
        cabinet.action(result);
        TestUtils.assertTestResultRunOnceSuccessfully(result);
    }

    public void testFireAfterAddTestEvent() throws Exception {
        assertNotNull(testAdded);
    }

    public void testFireAfterRemoveTestEvent() throws Exception {
        cabinet.removeTestShadow(sunshine, Success.class.getName());
        assertNotNull(testRemoved);
        assertEquals(testAdded, testRemoved);
    }

    public void testSetMaxSize() throws Exception {
        cabinet.setMaxSize(0);
        TestResult result = new TestResult();
        cabinet.action(result);
        TestUtils.assertNoTestWasRan(result);
    }

    public void testSize() throws Exception {
        assertEquals(1, cabinet.size());
    }

    public void afterAddTest(ComparableTestShadow test) {
        testAdded = test;
    }

    public void afterRemoveTest(ComparableTestShadow test) {
        testRemoved = test;
    }

    public void endAction() {
    }

    public void startAction() {
    }

    public void endTestShadow(ComparableTestShadow shadow, boolean wasSuccessful) {
        endTestShadowEventShadow = shadow;
    }
}
