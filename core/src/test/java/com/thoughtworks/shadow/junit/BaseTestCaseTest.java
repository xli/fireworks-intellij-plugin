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
package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestResultAssert;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class BaseTestCaseTest extends TestCase {
    private final String testClassName = "testClassName";
    private final String testMethodName = "testMethodName";

    private BaseTestCase baseTestCase;

    protected void setUp() throws Exception {
        baseTestCase = new SuccessfulTestCase(testClassName, testMethodName);
    }

    public void testTestShadowVisitor() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);
        baseTestCase.accept((ShadowVisitor) visitor.mockTarget());
        visitor.assertDid("visitTestMethodName").with(testMethodName);
        visitor.assertDid("visitTestClassName").with(testClassName);
        visitor.assertDid("end");
    }

    public void testShouldVisitTestMethodNameThenEnd() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);
        baseTestCase.accept((ShadowVisitor) visitor.mockTarget());
        visitor.assertDid("visitTestMethodName").then("visitTestClassName");
    }

    public void testShouldVisitTestClassNameThenEnd() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);
        baseTestCase.accept((ShadowVisitor) visitor.mockTarget());
        visitor.assertDid("visitTestClassName").then("end");
    }

    public void testToString() throws Exception {
        assertEquals(testMethodName + "(" + testClassName + ")", baseTestCase.toString());
        assertEquals(testClassName, new SuccessfulTestCase(testClassName).toString());
    }

    public void testCountTestCases() throws Exception {
        assertEquals(1, baseTestCase.countTestCases());
    }

    public void testRun() throws Exception {
        TestResultAssert.verifySuccess(baseTestCase);
    }

    public void testEquals() throws Exception {
        SuccessfulTestCase test = new SuccessfulTestCase(testClassName);
        assertEquals(test, test);
        assertEquals(test, new SuccessfulTestCase(testClassName));
        assertEquals(new SuccessfulTestCase(testClassName, testMethodName), new SuccessfulTestCase(testClassName, testMethodName));
        assertEquals(test, new BaseTestCase(testClassName, null, null));
        assertEquals(new BaseTestCase(testClassName, null, null), test);

        assertFalse(new SuccessfulTestCase(testClassName, testMethodName).equals(new SuccessfulTestCase(testClassName, "another " + testMethodName)));
        assertFalse(test.equals(new SuccessfulTestCase("another " + testClassName)));
        assertFalse(test.equals(new SuccessfulTestCase(testClassName, testMethodName)));
        assertFalse(test.equals(null));
        assertFalse(test.equals(this));
    }

    public void testHashCode() throws Exception {
        SuccessfulTestCase test = new SuccessfulTestCase();
        assertEquals(test.hashCode(), new SuccessfulTestCase().hashCode());

        SuccessfulTestCase test2 = new SuccessfulTestCase(testClassName, testMethodName);
        assertEquals(test2.hashCode(), new SuccessfulTestCase(testClassName, testMethodName).hashCode());

        assertFalse(test.hashCode() == test2.hashCode());
        assertFalse(test.hashCode() == new SuccessfulTestCase(testClassName).hashCode());
        assertFalse(test2.hashCode() == new SuccessfulTestCase(testClassName).hashCode());
    }
}
