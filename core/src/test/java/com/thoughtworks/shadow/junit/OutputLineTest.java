package com.thoughtworks.shadow.junit;

import junit.framework.TestCase;

public class OutputLineTest extends TestCase {
    public void testToString() throws Exception {
        OutputLine line = new OutputLine("line");
        assertEquals("OutputLine: line", line.toString());
    }
}
