package com.thoughtworks.shadow.junit;

public class FailureTestTraceLog extends TestTraceLog {
    public FailureTestTraceLog(String log) {
        super(log);
    }

    protected Throwable testCaseWillThrow(String msg, String trace) {
        return new AssertionFailedLogError(msg, trace);
    }
}
