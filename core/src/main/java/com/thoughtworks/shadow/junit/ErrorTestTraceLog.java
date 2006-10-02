package com.thoughtworks.shadow.junit;

public class ErrorTestTraceLog extends TestTraceLog {
    public ErrorTestTraceLog(String log) {
        super(log);
    }

    protected Throwable testCaseWillThrow(String msg, String trace) {
        return new LogThrowable(msg, trace);
    }
}
