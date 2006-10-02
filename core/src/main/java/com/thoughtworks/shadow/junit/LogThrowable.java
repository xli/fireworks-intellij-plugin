package com.thoughtworks.shadow.junit;

import java.io.PrintStream;
import java.io.PrintWriter;

public class LogThrowable extends Throwable {
    private String trace;

    public LogThrowable(String msg, String trace) {
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
