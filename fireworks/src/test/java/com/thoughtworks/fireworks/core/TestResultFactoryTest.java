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

import com.thoughtworks.shadow.RunListenerAdaptee;
import com.thoughtworks.shadow.junit.IgnoredTestCase;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;

public class TestResultFactoryTest extends TestCase {
    public void testCreateTestResult() throws Exception {
        Mock testListener = Turtle.mock(TestListener.class);
        Mock resultOfTestEndListener = Turtle.mock(ResultOfTestEndListener.class);

        TestListener[] testListeners = new TestListener[]{(TestListener) testListener.mockTarget()};
        ResultOfTestEndListener[] counterListeners = new ResultOfTestEndListener[]{(ResultOfTestEndListener) resultOfTestEndListener.mockTarget()};

        TestResultFactory factory = new TestResultFactory(testListeners, counterListeners);
        TestResult result = factory.createTestResult();
        resultOfTestEndListener.assertDid("testEnd").with(result);

        result.startTest(this);
        result.endTest(this);

        testListener.assertDid("startTest");
        testListener.assertDid("endTest");

        resultOfTestEndListener.assertDid("testEnd").with(result);
        assertEquals(1, result.runCount());
    }

    public void testShouldFireTestIgnoredEventWhenRunIgnoreTestCase() throws Exception {
        Mock testListener = Turtle.mock(RunListenerAdaptee.class);
        TestListener[] testListeners = new TestListener[]{(RunListenerAdaptee) testListener.mockTarget()};
        TestResultFactory factory = new TestResultFactory(testListeners, null);
        TestResult result = factory.createTestResult();

        IgnoredTestCase test = new IgnoredTestCase("", "");
        test.run(result);

        testListener.assertDid("testIgnored");
    }

    public void testShouldIgnoreNullArgs() throws Exception {
        TestResultFactory factory = new TestResultFactory(null, null);
        assertNotNull(factory.createTestResult());
    }
}
