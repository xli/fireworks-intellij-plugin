package com.thoughtworks.shadow.junit;

import junit.framework.AssertionFailedError;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AssertionFailedLogError extends AssertionFailedError {
    private String trace;

    public AssertionFailedLogError(String msg, String trace) {
        super(msg);
        this.trace = trace;
    }

    public void printStackTrace(PrintWriter s) {
        s.println(trace);
    }

    public void printStackTrace(PrintStream s) {
        s.println(trace);
    }

}
