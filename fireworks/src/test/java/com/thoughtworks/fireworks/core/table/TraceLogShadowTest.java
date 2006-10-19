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

import com.thoughtworks.shadow.junit.LogThrowable;
import junit.framework.TestCase;

public class TraceLogShadowTest extends TestCase implements TraceLogViewer {
    private final String log = "trace log";
    private Throwable displayed;
    private TraceLogShadow shadow;
    private LogThrowable throwable;

    protected void setUp() throws Exception {
        throwable = new LogThrowable("msg", TraceLog.toTraceLog(log));
        shadow = new TraceLogShadow(throwable, this);
        displayed = null;
    }

    public void testShouldGetFirstStackTraceElementOfThrowableObjectIfAllTraceLogAreFilteredWhenMakeTraceLogShadowToString() throws Exception {
        LogThrowable throwable = new LogThrowable("msg", "incorrect trace log");
        TraceLogShadow shadow = new TraceLogShadow(throwable, this);

        assertTrue(shadow.toString().startsWith(getClass().getName()));
    }

    public void testShouldGetFirstLineOfTraceLogWhenMakeTraceLogShadowToString() throws Exception {
        assertEquals(log, shadow.toString());
    }

    public void testShouldDisplayThrowableObjectWhenAcceptAnyShadowVisitor() throws Exception {
        shadow.accept(null);
        assertEquals(throwable, displayed);
    }

    public void display(Throwable t) {
        displayed = t;
    }
}
