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

import junit.framework.TestCase;

public class TraceLogTest extends TestCase {
    public void testIsTraceLog() throws Exception {
        assertTrue(TraceLog.isTraceLog("\tat xxx"));

        assertFalse(TraceLog.isTraceLog(null));
        assertFalse(TraceLog.isTraceLog(""));
        assertFalse(TraceLog.isTraceLog("not start with trace prefix"));
    }

    public void testToTraceLog() throws Exception {
        assertEquals("\tat xxx", TraceLog.toTraceLog("xxx"));

        assertEquals("\tat ", TraceLog.toTraceLog(null));
    }

    public void testRemoveTraceLogPrefix() throws Exception {
        assertEquals("xxx", TraceLog.removeTraceLogPrefix("\tat xxx"));
        assertEquals("", TraceLog.removeTraceLogPrefix("\tat "));

        assertNull(TraceLog.removeTraceLogPrefix(null));
        assertEquals("xxx", TraceLog.removeTraceLogPrefix("xxx"));
    }
}
