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
import com.thoughtworks.shadow.tests.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestFailure;
import junit.framework.TestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class TextTestRunnerOutputTestCaseTest extends TestCase {
    private final String exceptionMessage = "junit.framework.AssertionFailedError";
    private final String exceptionTrace = exceptionMessage + Utils.LINE_SEP + "\tat stack trace";
    private String errSummary;
    private String failureSummary;
    private String successSummary;
    private int time;

    protected void setUp() throws Exception {
        time = 1000;
        errSummary = summary(".E", 1, 0, 1, new String[]{Err.getTestName()});
        failureSummary = summary(".F", 1, 1, 0, new String[]{Failure.getTestName()});
        successSummary = summary(".", 1, 0, 0);
    }

    public void testShouldThrowIllegalArgumentExceptionIfOutputIsEmpty() throws Exception {
        try {
            new TextTestRunnerOutputTestCase(Success.class, "");
            fail();
        } catch (TestRunnerError e) {
            assertNotNull(e.getMessage());
        }
    }

    public void testParseASuccessfulTestOutput() throws Exception {
        Test test = new TextTestRunnerOutputTestCase(Success.class, successSummary);
        assertEquals(1, test.countTestCases());
        TestResultAssert.verifySuccess(test);
    }

    public void testParseTwoSuccessfulTestsOutput() throws Exception {
        int runCount = 2;
        String summary = summary("..", 2, 0, 0);
        Test test = new TextTestRunnerOutputTestCase(Success2.class, summary);
        assertEquals(runCount, test.countTestCases());
        TestResultAssert.verifySuccess(test, runCount);
    }

    public void testParseAFailureTestOutput() throws Exception {
        Test test = new TextTestRunnerOutputTestCase(Failure.class, failureSummary);
        assertEquals(1, test.countTestCases());
        TestResultAssert.verifyFailure(test);
    }

    public void testParseAErrorTestOutput() throws Exception {
        Test test = new TextTestRunnerOutputTestCase(Err.class, errSummary);
        assertEquals(1, test.countTestCases());
        TestResultAssert.verifyError(test);
    }

    public void testParseTime() throws Exception {
        TextTestRunnerOutputTestCase test = new TextTestRunnerOutputTestCase(Success.class, successSummary);
        assertEquals(time, test.getTime());
    }

    public void testParseFailureInfo() throws Exception {
        Test test = new TextTestRunnerOutputTestCase(Failure.class, failureSummary);
        assertFailureTraceLogs(test, new String[]{Failure.getTestName()});
    }


    public void testParseErrorInfo() throws Exception {
        Test test = new TextTestRunnerOutputTestCase(Err.class, errSummary);
        assertErrorTraceLogs(test, new String[]{Err.getTestName()});
    }

    public void testParseAComplexTestOutput() throws Exception {
        String header = "..E" +
                "some output printed by test case, for example: 'EF.'" +
                ".E..F.";
        String[] errTestNames = new String[]{"test1(" + SixTestMethods.class.getName() + ")", "test2(" + SixTestMethods.class.getName() + ")"};
        String[] failureTestNames = new String[]{"test3(" + SixTestMethods.class.getName() + ")"};
        String summary = summary(header, 6, 1, 2, errTestNames, failureTestNames);
        Test test = new TextTestRunnerOutputTestCase(SixTestMethods.class, summary);
        assertEquals(6, test.countTestCases());

        TestResult result = TestResultAssert.run(test);

        assertEquals(6, result.runCount());
        assertEquals(1, result.failureCount());
        assertEquals(2, result.errorCount());

        assertFailureTraceLogs(test, failureTestNames);
        assertErrorTraceLogs(test, errTestNames);
    }

    private void assertFailureTraceLogs(Test test, String[] testNames) {
        Enumeration failures = TestResultAssert.run(test).failures();
        List testNameList = Arrays.asList(testNames);
        while (failures.hasMoreElements()) {
            TestFailure testFailure = (TestFailure) failures.nextElement();
            assertTrue(testFailure.isFailure());
            assertTrue(testNameList.contains(testFailure.failedTest().toString()));
            assertEquals(exceptionMessage, testFailure.exceptionMessage());
            assertEquals(exceptionTrace, testFailure.trace().trim());
        }
    }

    private void assertErrorTraceLogs(Test test, String[] testNames) {
        Enumeration errors = TestResultAssert.run(test).errors();
        List testNameList = Arrays.asList(testNames);
        while (errors.hasMoreElements()) {
            TestFailure testFailure = (TestFailure) errors.nextElement();
            assertFalse(testFailure.isFailure());
            assertTrue(testNameList.contains(testFailure.failedTest().toString()));
            assertEquals(exceptionMessage, testFailure.exceptionMessage());
            assertEquals(exceptionTrace, testFailure.trace().trim());
        }
    }

    private String summary(String summary, int runCount, int failureCount, int errorCount, String[] testNames) {
        if (failureCount > 0) {
            return summary(summary, runCount, failureCount, errorCount, new String[0], testNames);
        } else {
            return summary(summary, runCount, failureCount, errorCount, testNames, new String[0]);
        }
    }

    private String summary(String summary, int runCount, int failureCount, int errorCount) {
        return summary(summary, runCount, failureCount, errorCount, new String[0], new String[0]);
    }

    private String summary(String summary, int runCount, int failureCount, int errorCount, String[] errTestNames, String[] failureTestNames) {
        StringWriter buffer = new StringWriter();
        PrintWriter writer = new PrintWriter(buffer);
        writer.println(summary);
        writer.println(elapsedTimeAsString(time));

        for (int i = 0; i < errTestNames.length; i++) {
            printErrors(writer, errorCount, errTestNames[i]);
        }
        for (int i = 0; i < failureTestNames.length; i++) {
            printFailures(writer, failureCount, failureTestNames[i]);
        }
        printFooter(writer, runCount, failureCount, errorCount);
        return buffer.toString();
    }

    private void printErrors(PrintWriter writer, int errorCount, String testName) {
        printDefects(writer, errorCount, "error", testName);
    }

    private void printFailures(PrintWriter writer, int failureCount, String testName) {
        printDefects(writer, failureCount, "failure", testName);
    }

    private void printDefects(PrintWriter writer, int count, String type, String testName) {
        if (count == 0) return;
        if (count == 1)
            writer.println("There was " + count + " " + type + ":");
        else
            writer.println("There were " + count + " " + type + "s:");
        for (int i = 1; i <= count; i++) {
            writer.println(testTraceLog(testName));
        }
    }

    private String testTraceLog(String testName) {
        return "1) " + testName + exceptionTrace;
    }

    private void printFooter(PrintWriter writer, int runCount, int failureCount, int errorCount) {
        if (failureCount == 0 && errorCount == 0) {
            writer.println();
            writer.print("OK");
            writer.println(" (" + runCount + " test" + (runCount == 1 ? "" : "s") + ")");
        } else {
            writer.println();
            writer.println("FAILURES!!!");
            writer.println("Tests run: " + runCount +
                    ",  Failures: " + failureCount +
                    ",  Errors: " + errorCount);
        }
        writer.println();
    }

    public String elapsedTimeAsString(long runTime) {
        return "Time: " + NumberFormat.getInstance().format((double) runTime / 1000);
    }
}
