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

import com.thoughtworks.shadow.tests.AllTypes;
import com.thoughtworks.shadow.tests.Failure;
import com.thoughtworks.shadow.tests.Success;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.internal.ProxyTypeMock;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableTestShadowTest extends TestCase {
    private ComparableTestShadow success;
    private ComparableTestShadow failure;
    private Test allTypeTests;
    private Test aSuccessTest;

    protected void setUp() throws Exception {
        aSuccessTest = new Success();
        success = new ComparableTestShadow(aSuccessTest);
        failure = new ComparableTestShadow(new Failure(), "A test must be failure.");
        allTypeTests = new ComparableTestShadow(new AllTypes());
    }

    public void testAcceptVisitorShouldOnlyVisitTestClassName() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);
        success.accept((ShadowVisitor) visitor.mockTarget());
        visitor.assertDid("visitTestClassName").with(Success.class.getName());
        visitor.assertDid("end");
        visitor.assertNotDid("visitTestMethodName");
    }

    public void testAcceptVisitorOrder() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);
        success.accept((ShadowVisitor) visitor.mockTarget());
        visitor.assertDid("visitTestClassName").then("end");
    }

    public void testRemovable() throws Exception {
        ShadowCabinet cabinet = new ShadowCabinet();
        cabinet.add(success);
        success.removeSelfFromContainer();
        assertEquals(0, cabinet.size());
    }

    public void testToString() throws Exception {
        assertEquals(aSuccessTest.toString(), success.toString());
    }

    public void testEqualsAndHashCode() throws Exception {
        ComparableTestShadow aNewSuccess = new ComparableTestShadow(aSuccessTest);

        assertEquals(success, success);
        assertEquals(success, aNewSuccess);
        assertEquals(success.hashCode(), aNewSuccess.hashCode());

        assertFalse(failure.equals(null));
        assertFalse(failure.equals(this));
        assertFalse(failure.equals(aNewSuccess));
        assertFalse(failure.hashCode() == aNewSuccess.hashCode());
    }

    public void testShouldDelegateCountTestCases() throws Exception {
        assertEquals(1, success.countTestCases());
        assertEquals(1, failure.countTestCases());
        assertEquals(1, allTypeTests.countTestCases());
    }

    public void testComparable() throws Exception {
        List list = testList();

        TestResult result = new TestResult();
        success.run(result);
        failure.run(result);
        allTypeTests.run(result);

        Collections.sort(list);

        assertEquals(allTypeTests, list.get(0));
        assertEquals(failure, list.get(1));
        assertEquals(success, list.get(2));
    }

    public void testShouldFireEndTestEvent() throws Exception {
        ProxyTypeMock stateListener = Turtle.mock(TestStateListener.class);
        success.addListener((TestStateListener) stateListener.mockTarget());

        success.run(new TestResult());
        stateListener.assertDid("endTestShadow").with(success, Boolean.TRUE);
    }

    private List testList() {
        List list = new ArrayList();
        list.add(success);
        list.add(allTypeTests);
        list.add(failure);
        return list;
    }
}
