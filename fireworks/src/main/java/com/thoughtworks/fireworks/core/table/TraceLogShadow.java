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

import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class TraceLogShadow implements Shadow {
    private final TraceLogFilter filter = new TraceLogFilter();
    private final TraceLogFormat format = new TraceLogFormat();
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
            if (filter.shouldFilter(trace)) {
                continue;
            }
            return format.format(trace);
        }
        return t.getStackTrace()[0].toString().trim();
    }
}
