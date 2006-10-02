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
