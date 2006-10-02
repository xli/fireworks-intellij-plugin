package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class TestFailure implements ShadowVisitor {
    private final Shadow shadow;

    private Shadow[] columnValues = new Shadow[4];

    public TestFailure(Shadow shadow, Throwable t, TraceLogViewer traceLogViewer) {
        this.shadow = shadow;
        columnValues[ShadowTableModel.MESSAGE] = new EmptyStrShadow(filterMessage(t));
        columnValues[ShadowTableModel.TRACE_LOG] = new TraceLogShadow(t, traceLogViewer);
        shadow.accept(this);
    }

    private String filterMessage(Throwable t) {
        return new JunitAssertionMessageFilter().doFilter(t.getMessage());
    }

    public void visitTestClassName(String testClassName) {
        columnValues[ShadowTableModel.TEST_CLASS] = new EmptyStrShadow(testClassName);
    }

    public void visitTestMethodName(String testMethodName) {
        columnValues[ShadowTableModel.TEST_METHOD] = new ShadowStrDecorator(shadow, testMethodName);
    }

    public void end() {
    }

    public Shadow getShadow(int columnIndex) {
        return columnValues[columnIndex];
    }
}
