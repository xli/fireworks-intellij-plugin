package com.thoughtworks.shadow;

import com.thoughtworks.shadow.tests.AllTypes;
import com.thoughtworks.shadow.tests.Err;
import com.thoughtworks.shadow.tests.Failure;
import com.thoughtworks.shadow.tests.Success;
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
