package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.Utils;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class LogThrowableTest extends TestCase {
    private final String exceptionMessage = "Throwable";
    private final String exceptionTrace = exceptionMessage + Utils.LINE_SEP + "\tat shadow.Failure.testShouldFailed(Failure.java:7)";
    private LogThrowable error;

    protected void setUp() throws Exception {
        error = new LogThrowable(exceptionMessage, exceptionTrace);
    }

    public void testPrintStackTraceWithPrintStream() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        error.printStackTrace(new PrintStream(out, true));
        assertEquals(exceptionTrace + Utils.LINE_SEP, out.toString());
    }

    public void testPrintStackTraceWithPrintWriter() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        error.printStackTrace(new PrintWriter(out, true));
        assertEquals(exceptionTrace + Utils.LINE_SEP, out.toString());
    }

}
