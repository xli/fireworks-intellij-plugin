package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class ShadowSummaryTreeNodeTest extends TestCase {
    private Mock config;
    private Integer maxSize;
    private ShadowSummaryTreeNode node;

    protected void setUp() throws Exception {
        config = Turtle.mock(FireworksConfig.class);
        maxSize = new Integer(1);
        config.ifCall("maxSize").willReturn(maxSize);
        node = new ShadowSummaryTreeNode((FireworksConfig) config.mockTarget());
    }

    public void testParentShouldBeNull() throws Exception {
        assertNull(node.parent());
    }

    public void testShouldBeEmptyStrIfConfigIsNull() throws Exception {
        assertEquals("", new ShadowSummaryTreeNode().accessory());
    }

    public void testEmptySummaryTreeNode() throws Exception {
        assertFalse(node.isRemovable());
        assertFalse(node.isSource());

        assertEquals("0 Test Class", node.label());
        assertEquals("Max: " + maxSize, node.accessory());
    }

    public void testRemoveselfShouldDoNothing() throws Exception {
        node.removeSelf();
    }

    public void testChildrenIncreased() throws Exception {
        node.childrenIncreased();
        assertEquals("1 Test Class", node.label());
        node.childrenIncreased();
        assertEquals("2 Test Class", node.label());
    }

    public void testChildrenDecreased() throws Exception {
        node.childrenIncreased();
        node.childrenIncreased();
        assertEquals("2 Test Class", node.label());
        node.childrenDecreased();
        assertEquals("1 Test Class", node.label());
        node.childrenDecreased();
        assertEquals("0 Test Class", node.label());
    }
}
