package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.ant.AntSunshine;
import junit.framework.Assert;
import junit.framework.TestResult;

import java.net.URL;

public class TestUtils {

    public static Sunshine sunshine() {
        return new AntSunshine(classpaths());
    }

    public static URL[] classpaths() {
        URL[] classpaths = new URL[2];
        classpaths[0] = Utils.toURL("target/test-classes");
        classpaths[1] = Utils.toURL("src/test/libs/junit-3.8.2.jar");
        return classpaths;
    }

    public static void assertTestResultRunOnceSuccessfully(TestResult result) {
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public static void assertTestResultRunOnceFailed(TestResult result) {
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(1, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public static void assertNoTestWasRan(TestResult result) {
        Assert.assertEquals(0, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }
}
