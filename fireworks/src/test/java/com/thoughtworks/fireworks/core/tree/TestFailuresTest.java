package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import junit.framework.TestCase;

import java.io.StringWriter;

public class TestFailuresTest extends TestCase {
    private Throwable throwable;
    private TestFailures failures;
    private SuccessTestShadow shadow;
    private ShadowTreeNode parent;

    protected void setUp() throws Exception {
        throwable = new Throwable();
        failures = new TestFailures();
        shadow = new SuccessTestShadow();
        parent = new ShadowSummaryTreeNode();

        failures.addIntoBuffer(shadow, throwable);
        failures.commitBuffer(parent);
    }

    public void testOutputByNode() throws Exception {
        ShadowMethodTreeNode node = new ShadowMethodTreeNode(shadow, parent);
        StringWriter writer = new StringWriter();
        failures.output(node, new ConsoleViewBuffer(writer));

        assertEquals(Utils.toString(throwable).trim(), writer.toString().trim());
    }

    public void testOutputByParentNode() throws Exception {
        StringWriter writer = new StringWriter();
        failures.output(parent, new ConsoleViewBuffer(writer));

        assertEquals(Utils.toString(throwable).trim(), writer.toString().trim());
    }
}
