/*
 *    Copyright (c) 2006 LiXiao
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
package com.thoughtworks.shadow;

import com.thoughtworks.shadow.tests.Success;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.internal.ProxyTypeMock;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class ShadowCabinetListenerTest extends TestCase {
    private ProxyTypeMock listener;
    private ShadowCabinet cabinet;
    private ComparableTestShadow testShadow;

    protected void setUp() throws Exception {
        listener = Turtle.mock(ShadowCabinetListener.class);
        cabinet = new ShadowCabinet();
        cabinet.addListener((ShadowCabinetListener) listener.mockTarget());

        testShadow = new ComparableTestShadow(new Success());
    }

    public void testShouldNotRemoveTestThenAddContainedTestShadow() throws Exception {
        cabinet.setMaxSize(1);
        cabinet.add(testShadow);
        cabinet.add(testShadow);
        listener.assertNotDid("afterRemoveTest");
    }

    public void testRemoveListener() throws Exception {
        cabinet.removeListener((ShadowCabinetListener) listener.mockTarget());
        cabinet.add(testShadow);
        cabinet.action(new TestResult());
        listener.assertNotDid("startAction");
    }

    public void testShouldFireStartAndThenEndActionEventWhenAction() throws Exception {
        cabinet.add(testShadow);
        cabinet.action(new TestResult());
        listener.assertDid("startAction").then("endAction");
    }

    public void testShouldFireAfterAddTestEventWhenAddTestIntoCabinet() throws Exception {
        cabinet.add(testShadow);
        listener.assertDid("afterAddTest").with(testShadow);
    }

    public void testShouldFireAfterRemoveTestEventWhenRemoveTestFromCabinet() throws Exception {
        cabinet.add(testShadow);
        cabinet.remove(testShadow);
        listener.assertDid("afterRemoveTest").with(testShadow);
    }

    public void testShouldNotFireAfterAddTestEventIfTestExists() throws Exception {
        cabinet.add(testShadow);
        listener.assertDid("afterAddTest").with(testShadow);
        cabinet.add(testShadow);
        listener.assertNotDid("afterAddTest");
    }

    public void testShouldNotFireAfterRemoveTestEventIfTestDoesNotExist() throws Exception {
        cabinet.remove(testShadow);
        listener.assertNotDid("afterRemoveTest");
    }

    public void testShouldFireRemoveEventAndAddEventIfTestAddedReplacesOtherOne() throws Exception {
        cabinet.setMaxSize(1);
        cabinet.add(testShadow);
        ComparableTestShadow newTest = new ComparableTestShadow(new Success());
        cabinet.add(newTest);
        listener.assertDid("afterRemoveTest").with(testShadow);
        listener.assertDid("afterAddTest").with(newTest);
    }
}
