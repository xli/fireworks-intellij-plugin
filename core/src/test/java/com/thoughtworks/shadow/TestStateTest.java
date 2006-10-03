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

import com.thoughtworks.shadow.tests.Failure;
import com.thoughtworks.shadow.tests.Success;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.internal.ProxyTypeMock;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestStateTest extends TestCase {
    private TestState first;
    private TestState second;
    private ComparableTestShadow aShadow;

    protected void setUp() throws Exception {
        aShadow = new ComparableTestShadow(new Success());
        first = new TestState(aShadow);
        first.startTest(aShadow);
        second = new TestState(aShadow);
        second.startTest(aShadow);
    }

    public void testComparable() throws Exception {
        runTestFailure(first);
        runTestFailure(first);
        runTestFailure(first);
        runTestFailure(second);
        runTestFailure(second);
        TestState last = new TestState(aShadow);
        runTestFailure(last);

        List list = new ArrayList();
        list.add(second);
        list.add(last);
        list.add(first);

        Collections.sort(list);

        assertEquals(first, list.get(0));
        assertEquals(second, list.get(1));
        assertEquals(last, list.get(2));
    }

    public void testShouldFailAnytime() throws Exception {
        second.addError(null, null);
        assertTrue(first.compareTo(second) > 0);
        first.addFailure(null, null);
        assertTrue(first.compareTo(second) == 0);
    }

    public void testAddFailureShouldBeSameWithAddError() throws Exception {
        runTestFailure(first);

        second.startTest(aShadow);
        second.addError(null, null);
        second.endTest(aShadow);

        assertEquals(0, first.compareTo(second));
    }

    public void testShouldBeGreaterThanAnyFailedShadowAfterPass() throws Exception {
        runTestFailure(first);
        runTestFailure(first);
        runTestFailure(first);
        runTestSuccessfully(first);
        runTestFailure(second);
        assertTrue(first.compareTo(second) > 0);
    }

    public void testShouldBeLessThanAnyPassedShadowAfterFail() throws Exception {
        runTestSuccessfully(first);
        runTestSuccessfully(first);
        runTestSuccessfully(first);
        runTestFailure(first);
        runTestSuccessfully(second);

        assertTrue(first.compareTo(second) < 0);
    }

    public void testShouldBeGreaterThanJustFailedShadowIfTheShadowJustPass() throws Exception {
        runTestFailure(first);
        runTestSuccessfully(second);
        runTestSuccessfully(first);
        runTestFailure(second);
        assertTrue(first.compareTo(second) > 0);
    }

    public void testShouldIncreasePassTimes() throws Exception {
        runTestSuccessfully(first);
        runTestSuccessfully(first);
        runTestSuccessfully(second);
        assertTrue(first.compareTo(second) > 0);
    }

    public void testShouldFireEndTestEvent() throws Exception {
        Integer once = new Integer(1);
        Integer twice = new Integer(2);

        ProxyTypeMock stateListener = Turtle.mock(TestStateListener.class);
        first.addListener((TestStateListener) stateListener.mockTarget());

        runTestSuccessfully(first);
        stateListener.assertDid("endTestShadow").with(aShadow, Boolean.TRUE, once);

        runTestSuccessfully(first);
        stateListener.assertDid("endTestShadow").with(aShadow, Boolean.TRUE, twice);

        runTestFailure(first);
        stateListener.assertDid("endTestShadow").with(aShadow, Boolean.FALSE, once);

        runTestFailure(first);
        stateListener.assertDid("endTestShadow").with(aShadow, Boolean.FALSE, twice);
    }

    public void testShouldNotFireEndTestEventIfTheTestIsNotTargetTest() throws Exception {
        ProxyTypeMock stateListener = Turtle.mock(TestStateListener.class);
        first.addListener((TestStateListener) stateListener.mockTarget());

        Failure anotherTest = new Failure();
        runTestSuccessfully(first, anotherTest);
        stateListener.assertNotDid("endTestShadow");
    }

    private void runTestSuccessfully(TestListener listener) {
        runTestSuccessfully(listener, aShadow);
    }

    private void runTestSuccessfully(TestListener listener, Test test) {
        listener.startTest(test);
        listener.endTest(test);
    }

    private void runTestFailure(TestListener listener) {
        listener.startTest(aShadow);
        listener.addFailure(null, null);
        listener.endTest(aShadow);
    }
}
