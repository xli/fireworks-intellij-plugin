package com.thoughtworks.shadow;

import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.internal.ProxyTypeMock;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestResult;

public class TestResultAssert {

    public static void verifyError(Test test) {
        TestResult result = run(test);
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(1, result.errorCount());
    }

    public static void verifyFailure(Test test) {
        TestResult result = run(test);
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(1, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public static void verifySuccess(Test test) {
        TestResult result = run(test);
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public static void verifySuccess(Test test, int runCount) {
        TestResult result = run(test);
        Assert.assertEquals(runCount, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public static TestResult run(Test test) {
        ProxyTypeMock listener = Turtle.mock(TestListener.class);
        TestResult result = new TestResult();
        result.addListener((TestListener) listener.mockTarget());
        test.run(result);
        listener.assertDid("startTest");
        listener.assertDid("endTest");
        return result;
    }
}
