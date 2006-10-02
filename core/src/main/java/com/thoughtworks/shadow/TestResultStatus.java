package com.thoughtworks.shadow;

import junit.framework.TestResult;

public class TestResultStatus {
    private final int runCount;
    private final int failureCount;
    private final int errorCount;

    public TestResultStatus(int runCount, int failureCount, int errorCount) {
        this.runCount = runCount;
        this.failureCount = failureCount;
        this.errorCount = errorCount;
    }

    public TestResultStatus(TestResult testResult) {
        this(testResult.runCount(), testResult.failureCount(), testResult.errorCount());
    }

    public boolean wasSuccessful() {
        return runCount > 0 && errorCount == 0 && failureCount == 0;
    }

    public boolean isNoTest() {
        return runCount == 0;
    }

    public String summary() {
        return "Tests run: " + runCount + ", Failures: " + failureCount + ", Errors: " + errorCount;
    }
}
