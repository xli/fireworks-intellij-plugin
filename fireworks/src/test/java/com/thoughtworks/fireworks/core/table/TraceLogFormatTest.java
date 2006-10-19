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

public class TraceLogFormatTest extends TestCase {
    private TraceLogFormat format;

    protected void setUp() throws Exception {
        format = new TraceLogFormat();
    }

    public void testShouldBeEmptyIfTraceLogIsIncorrect() throws Exception {
        assertEquals("", format.format(null));
        assertEquals("", format.format(""));
        assertEquals("", format.format("not start with trace log prefix"));
    }

    public void testFormatTraceLog() throws Exception {
        assertEquals("except", format.format(TraceLog.toTraceLog("except")));
        assertEquals("(message)except", format.format(TraceLog.toTraceLog("except(message)")));
    }
}
