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

import com.thoughtworks.shadow.ant.AntSunshine;
import com.thoughtworks.shadow.junit.BaseTestCase;
import com.thoughtworks.shadow.tests.*;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.internal.ProxyTypeMock;
import junit.framework.*;

import java.net.URL;

public class TestShadowTest extends TestCase {
    private Sunshine sunshine;

    protected void setUp() throws Exception {
        sunshine = TestUtils.sunshine();
    }

    public void testRunTestShadow() throws Exception {
        ProxyTypeMock listener = Turtle.mock(TestListener.class);
        ShineTestClassShadow shadow = new ShineTestClassShadow(AllTypes.class.getName(), sunshine);
        TestResult result = new TestResult();
        result.addListener((TestListener) listener.mockTarget());

        shadow.run(result);

        String[] namesOfTestMethods = AllTypes.getTestMethodNames();

        assertEquals(3, namesOfTestMethods.length);
        for (int i = 0; i < namesOfTestMethods.length; i++) {
            listener.assertDid("startTest").with(new BaseTestCase(AllTypes.class.getName(), namesOfTestMethods[i], null));
        }
    }

    public void testToString() throws Exception {
        String testClassName = Success.class.getName();

        String expected = "Success(com.thoughtworks.shadow.tests)";
        assertEquals(expected, new ShineTestClassShadow(testClassName, null).toString());

        assertEquals("ClassName", new ShineTestClassShadow("ClassName", null).toString());
    }

    public void testEqualsAndHashCode() throws Exception {
        ShineTestClassShadow test1 = new ShineTestClassShadow(Success.class.getName(), sunshine);
        ShineTestClassShadow test2 = new ShineTestClassShadow(Success.class.getName(), sunshine);
        ShineTestClassShadow test3 = new ShineTestClassShadow(Failure.class.getName(), sunshine);
        assertEquals(test1, test1);
        assertEquals(test1, test2);
        assertEquals(test1.hashCode(), test2.hashCode());

        assertFalse(test1.equals(null));
        assertFalse(test1.equals(this));
        assertFalse(test1.equals(test3));
        assertFalse(test1.hashCode() == test3.hashCode());
    }

    public void testRunAllTestMethods() throws Exception {
        ShineTestClassShadow shadow = new ShineTestClassShadow(AllTypes.class.getName(), sunshine);
        assertEquals(3, shadow.countTestCases());

        TestResult result = TestResultAssert.run(shadow);
        Assert.assertEquals(3, result.runCount());
        Assert.assertEquals(1, result.failureCount());
        Assert.assertEquals(1, result.errorCount());
    }

    public void testNoTestMethod() throws Exception {
        ShineTestClassShadow shadow = new ShineTestClassShadow(NoTestMethod.class.getName(), sunshine);
        TestResult result = TestResultAssert.run(shadow);
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(1, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public void testTestClassNotFound() throws Exception {
        ShineTestClassShadow shadow = new ShineTestClassShadow("test class does not exit", sunshine);
        TestResult result = TestResultAssert.run(shadow);
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(1, result.errorCount());
    }

    public void testShouldBeErrorWhenJUnitTestRunnerDoesNotInTheClasspath() throws Exception {
        Sunshine sunshine = new AntSunshine(new URL[]{Utils.toURL(TestUtils.baseDir() + "src/test/class")});
        ShineTestClassShadow shadow = new ShineTestClassShadow("ClassOutOfClasspath", sunshine);
        TestResult result = TestResultAssert.run(shadow);
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(1, result.errorCount());
    }

    public void testSuccess() throws Exception {
        ShineTestClassShadow shadow = new ShineTestClassShadow(Success.class.getName(), sunshine);
        assertEquals(1, shadow.countTestCases());
        TestResultAssert.verifySuccess(shadow);
    }

    public void testFailure() throws Exception {
        ShineTestClassShadow shadow = new ShineTestClassShadow(Failure.class.getName(), sunshine);
        TestResultAssert.verifyFailure(shadow);
    }

    public void testErr() throws Exception {
        ShineTestClassShadow shadow = new ShineTestClassShadow(Err.class.getName(), sunshine);
        TestResultAssert.verifyError(shadow);
    }

    public void testShouldFireStartAndEndTestEvent() throws Exception {
        ProxyTypeMock testListener = Turtle.mock(TestListener.class);
        ShineTestClassShadow shadow = new ShineTestClassShadow(Failure.class.getName(), new Sunshine() {
            public Test shine(String testClassName) {
                return new Success();
            }
        });
        TestResult result = new TestResult();
        result.addListener((TestListener) testListener.mockTarget());
        shadow.run(result);
        testListener.assertDid("startTest").then("endTest");
    }

}
