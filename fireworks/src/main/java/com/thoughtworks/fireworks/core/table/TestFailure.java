/*
 *    Copyright (c) 2006 LiXiao.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
