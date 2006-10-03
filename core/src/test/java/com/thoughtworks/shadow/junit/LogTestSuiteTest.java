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

import com.thoughtworks.shadow.TestResultAssert;
import com.thoughtworks.shadow.Utils;
import com.thoughtworks.shadow.tests.SixTestMethods;
import com.thoughtworks.shadow.tests.Success;
import com.thoughtworks.shadow.tests.Success2;
import junit.framework.TestCase;

public class LogTestSuiteTest extends TestCase {
    public void testCountTestCases() throws Exception {
        assertEquals(1, new LogTestSuite(Success.class).countTestCases());
        assertEquals(2, new LogTestSuite(Success2.class).countTestCases());
        assertEquals(6, new LogTestSuite(SixTestMethods.class).countTestCases());
    }

    public void testShouldReplaceExistTestCaseWhenAddNewTestCaseByTraceLogIterator() throws Exception {
        String testName = "testSuccess(com.thoughtworks.shadow.tests.Success)";
        String exceptionMessage = "junit.framework.AssertionFailedError";
        String exceptionTrace = exceptionMessage + Utils.LINE_SEP + "\tat stack trace";

        LogTestSuite suite = new LogTestSuite(Success.class);
        suite.add(new TraceLogIterator(new String[]{"", testName + Utils.LINE_SEP + exceptionTrace}, true));
        assertEquals(1, suite.countTestCases());
        TestResultAssert.verifyFailure(suite);
    }
}
