package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.fireworks.stubs.FailureTestShadow;
import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.Utils;
import com.thoughtworks.shadow.junit.AssertionFailedLogError;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class TestFailureTest extends TestCase {
    private String msg;
    private String trace;
    private AssertionFailedLogError error;
    private TestFailure failure;
    private Mock logViewer;

    protected void setUp() throws Exception {
        msg = "junit.framework.AssertionFailedError: message";
        trace = msg + Utils.LINE_SEP + "\tat package.TestClass.testShouldFailed(TestClass.java:7)";
        error = new AssertionFailedLogError(msg, trace);
        logViewer = Turtle.mock(TraceLogViewer.class);
        failure = new TestFailure(new FailureTestShadow(), error, (TraceLogViewer) logViewer.mockTarget());
    }

    public void testShouldFormatTraceLog() throws Exception {
        assertEquals("(TestClass.java:7)package.TestClass.testShouldFailed", failure.getShadow(ShadowTableModel.TRACE_LOG).toString());
    }

    public void testShouldFilterJunitPackageInfoInTheJunitAssertionErrorMsg() throws Exception {
        assertEquals(": message", failure.getShadow(ShadowTableModel.MESSAGE).toString());
    }

    public void testShadowOfMessageWhichShouldDoNothingWhenItAcceptsAVisitor() throws Exception {
        assertEmptyShadow(ShadowTableModel.MESSAGE);
    }

    public void testShadowOfClassWhichShouldDoNothingWhenItAcceptsAVisitor() throws Exception {
        assertEmptyShadow(ShadowTableModel.TEST_CLASS);
    }

    public void testShadowOfTraceLogWhichShouldDoNothingWhenItAcceptsAVisitor() throws Exception {
        assertEmptyShadow(ShadowTableModel.TRACE_LOG);
    }

    public void testShadowOfTestMethodWhichShouldDoNothingWhenItAcceptsAVisitor() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);

        Shadow shadow = failure.getShadow(ShadowTableModel.TEST_METHOD);
        shadow.accept((ShadowVisitor) visitor.mockTarget());

        visitor.assertDid("visitTestMethodName").with(FailureTestShadow.TEST_METHOD);
        visitor.assertDid("visitTestClassName").with(FailureTestShadow.class.getName());
        visitor.assertDid("end");
    }

    public void testShouldShowLogWhenShadowOfTraceLogAcceptsAVisitor() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);

        Shadow shadow = failure.getShadow(ShadowTableModel.TRACE_LOG);
        shadow.accept((ShadowVisitor) visitor.mockTarget());

        logViewer.assertDid("display").with(error);
    }

    private void assertEmptyShadow(int columnIndex) {
        Shadow shadow = failure.getShadow(columnIndex);

        shadow.accept(new ShadowVisitor() {
            public void visitTestClassName(String testClassName) {
                throw new UnsupportedOperationException();
            }

            public void visitTestMethodName(String testMethodName) {
                throw new UnsupportedOperationException();
            }

            public void end() {
                throw new UnsupportedOperationException();
            }
        });
    }
}
