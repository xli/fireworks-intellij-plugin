package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class TraceLogShadow implements Shadow {
    private static final String TRACE_PREFIX = "\tat ";

    private final Throwable t;
    private final TraceLogViewer traceLogViewer;

    public TraceLogShadow(Throwable t, TraceLogViewer traceLogViewer) {
        this.t = t;
        this.traceLogViewer = traceLogViewer;
    }

    public void accept(ShadowVisitor visitor) {
        traceLogViewer.display(t);
    }

    public String toString() {
        return getFirstTraceLog();
    }

    private String getFirstTraceLog() {
        String[] traces = Utils.toString(t).split(Utils.LINE_SEP);
        for (String trace : traces) {
            if (trace.startsWith(TRACE_PREFIX + "junit.framework") || !trace.startsWith(TRACE_PREFIX)) {
                continue;
            }
            return format(trace);
        }
        return t.getStackTrace()[0].toString().trim();
    }

    private String format(String trace) {
        String temp = trace.substring(TRACE_PREFIX.length()).trim();
        int splitIndex = temp.indexOf("(");
        return temp.substring(splitIndex) + temp.substring(0, splitIndex);
    }
}
