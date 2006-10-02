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
        console.cleanAndPrint("text");
        assertEquals("text" + Utils.LINE_SEP, writer.toString());
    }

    public void testComponentShouldBeNull() throws Exception {
        assertNull(console.getComponent());
    }

    public void testShouldIgnoreNullOrEmptyText() throws Exception {
        console.cleanAndPrint("");
        console.cleanAndPrint(null);
        assertEquals("", writer.toString());
    }
}
