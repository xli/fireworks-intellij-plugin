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
package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.shadow.Utils;
import junit.framework.TestCase;

import java.io.StringWriter;

public class ConsoleViewBufferTest extends TestCase {
    private StringWriter writer;
    private ConsoleViewBuffer console;

    protected void setUp() throws Exception {
        writer = new StringWriter();
        console = new ConsoleViewBuffer(writer);
    }

    public void testShouldBufferLog() throws Exception {
        console.clearAndPrint("text");
        assertEquals("text" + Utils.LINE_SEP, writer.toString());
    }

    public void testComponentShouldBeNull() throws Exception {
        assertNull(console.getComponent());
    }

    public void testShouldIgnoreNullOrEmptyText() throws Exception {
        console.clearAndPrint("");
        console.clearAndPrint(null);
        assertEquals("", writer.toString());
    }
}
