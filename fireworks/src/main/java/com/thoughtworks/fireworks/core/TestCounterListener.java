package com.thoughtworks.fireworks.core;

public interface TestCounterListener {
    void testResult(int runCount, int failureCount, int errorCount);
}
