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

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class TestResultStatusTest extends TestCase {
    public void testShouldBeFailureStatusIfThereAreErrorsOrFailures() throws Exception {
        assertFalse(new TestResultStatus(1, 0, 1, 0).wasSuccessful());
        assertFalse(new TestResultStatus(2, 1, 1, 0).wasSuccessful());
        assertFalse(new TestResultStatus(2, 1, 0, 0).wasSuccessful());
    }

    public void testShouldBeFailureStatusIfAllTestIgnored() throws Exception {
        assertFalse(new TestResultStatus(0, 0, 0, 2).wasSuccessful());
    }

    public void testShouldBeSuccessfulStatusIfSomeTestsAreSuccessfulAndOthersAreIgnored() throws Exception {
        assertTrue(new TestResultStatus(4, 0, 0, 2).wasSuccessful());
        assertTrue(new TestResultStatus(2, 0, 0, 2).wasSuccessful());
    }

    public void testShouldBeFailureStatusIfSomeTestsAreFailureAndOthersAreIgnored() throws Exception {
        assertFalse(new TestResultStatus(4, 1, 0, 2).wasSuccessful());
        assertFalse(new TestResultStatus(4, 0, 1, 2).wasSuccessful());
        assertFalse(new TestResultStatus(4, 1, 1, 2).wasSuccessful());
    }

    public void testShouldBeFailureStatusIfRunCountIsZero() throws Exception {
        assertFalse(new TestResultStatus(0, 0, 0, 0).wasSuccessful());
    }

    public void testShouldBeNoTestIfRunCountIsZero() throws Exception {
        assertTrue(new TestResultStatus(0, 0, 0, 0).isNoTest());
        assertFalse(new TestResultStatus(1, 0, 0, 0).isNoTest());
    }

    public void testShouldBeSuccessfulIfHasTestRanAndThereAreNotErrorsOrFailures() throws Exception {
        assertTrue(new TestResultStatus(2, 0, 0, 0).wasSuccessful());
        assertTrue(new TestResultStatus(1, 0, 0, 0).wasSuccessful());
    }

    public void testInitByATestResult() throws Exception {
        TestShadowResult testResult = new TestShadowResult();
        assertFalse(new TestResultStatus(testResult).wasSuccessful());
        assertTrue(new TestResultStatus(testResult).isNoTest());

        testResult.startTest(this);
        assertTrue(new TestResultStatus(testResult).wasSuccessful());
        assertFalse(new TestResultStatus(testResult).isNoTest());

        testResult.addFailure(this, new AssertionFailedError());
        assertFalse(new TestResultStatus(testResult).wasSuccessful());
        assertFalse(new TestResultStatus(testResult).isNoTest());

        testResult = new TestShadowResult();
        testResult.startTest(this);
        testResult.addError(this, new AssertionFailedError());
        assertFalse(new TestResultStatus(testResult).wasSuccessful());
        assertFalse(new TestResultStatus(testResult).isNoTest());
    }

    public void testSummary() throws Exception {
        assertEquals("Tests run: 1, Failures: 0, Errors: 0, Ignored: 0", new TestResultStatus(1, 0, 0, 0).summary());
    }
}
