package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.Utils;
import junit.framework.TestCase;
import junit.framework.TestFailure;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class AssertionFailedLogErrorTest extends TestCase {
    private final String exceptionMessage = "AssertionFailedError";
    private final String exceptionTrace = exceptionMessage + Utils.LINE_SEP + "\tat shadow.Failure.testShouldFailed(Failure.java:7)";
    private AssertionFailedLogError error;

    protected void setUp() throws Exception {
        error = new AssertionFailedLogError(exceptionMessage, exceptionTrace);
    }

    public void testMessageAndTrace() throws Exception {
        TestFailure failure = new TestFailure(new SuccessfulTestCase(), error);

        assertEquals(exceptionMessage, failure.exceptionMessage());
        assertEquals(exceptionTrace + Utils.LINE_SEP, failure.trace());
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
