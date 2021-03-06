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

import com.thoughtworks.shadow.tests.*;
import com.thoughtworks.shadow.ant.AntSunshine;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class ShadowCabinetTest extends TestCase {
    private ShadowCabinet cabinet;
    private ComparableTestShadow successTestShadow;
    private ComparableTestShadow failureTestShadow;
    private ComparableTestShadow errorTestShadow;

    protected void setUp() throws Exception {
        cabinet = new ShadowCabinet();

        successTestShadow = new ComparableTestShadow(new Success());
        failureTestShadow = new ComparableTestShadow(new Failure());
        errorTestShadow = new ComparableTestShadow(new Err());
    }

    public void testParseExceptions() throws Exception {
        ShineTestClassShadow test = new ShineTestClassShadow(ErrExceptionCausedByAnother.class.getName(), TestUtils.sunshine());
        cabinet.add(new ComparableTestShadow(test));
        TestResult result = new TestResult();
        cabinet.action(result);
        assertEquals(1, result.runCount());
        assertEquals(0, result.failureCount());
        assertEquals(1, result.errorCount());
    }

    public void testShouldRunAllTestClassAddedWhenAction() throws Exception {
        TestResult result = new TestResult();
        cabinet.add(successTestShadow);
        cabinet.add(failureTestShadow);
        cabinet.add(errorTestShadow);
        cabinet.add(new ComparableTestShadow(new AllTypes()));
        cabinet.action(result);

        assertEquals(4, result.runCount());
        assertEquals(2, result.failureCount());
        assertEquals(1, result.errorCount());
    }

    public void testShouldBeZeroSize() throws Exception {
        assertEquals(0, cabinet.size());
    }

    public void testRemoveTest() throws Exception {
        cabinet.remove(successTestShadow);
        assertEquals(0, cabinet.size());
        cabinet.add(successTestShadow);
        cabinet.remove(successTestShadow);
        assertEquals(0, cabinet.size());
    }

    public void testShouldIncreaseAfterAddNewTestClass() throws Exception {
        cabinet.add(successTestShadow);
        assertEquals(1, cabinet.size());
    }

    public void testMaxSizeShouldBe5() throws Exception {
        assertEquals(5, cabinet.maxSize());
    }

    public void testSizeOfContainedTestsShouldNotBeLargerThanMaxSize() throws Exception {
        cabinet.setMaxSize(0);
        assertEquals(0, cabinet.maxSize());
        cabinet.add(successTestShadow);
        TestResult result = new TestResult();
        cabinet.action(result);
        assertEquals(0, result.runCount());
    }

    public void testShouldNotAddSameTestClass() throws Exception {
        cabinet.add(successTestShadow);
        cabinet.add(successTestShadow);
        cabinet.add(successTestShadow);
        assertEquals(1, cabinet.size());
    }

    public void testShouldRemoveMaxPassTimesTestFirstWhenAddNewTestAndTheSizeIsSameWithMaxSize() throws Exception {
        cabinet.setMaxSize(2);
        cabinet.add(successTestShadow);
        cabinet.add(failureTestShadow);
        cabinet.action(new TestResult());

        cabinet.add(errorTestShadow);
        TestResult result = new TestResult();
        cabinet.action(result);
        assertEquals(2, result.runCount());
        assertEquals(1, result.failureCount());
        assertEquals(1, result.errorCount());
    }

    public void testShouldRemoveOverTestsAfterSetMaxSize() throws Exception {
        cabinet.add(successTestShadow);
        cabinet.add(failureTestShadow);
        cabinet.setMaxSize(1);
        assertEquals(1, cabinet.size());
    }
}
