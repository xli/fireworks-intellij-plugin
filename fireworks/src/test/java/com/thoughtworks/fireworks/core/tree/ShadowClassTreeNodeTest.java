package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import com.thoughtworks.shadow.junit.SuccessfulTestCase;
import junit.framework.TestCase;

public class ShadowClassTreeNodeTest extends TestCase {
    private ComparableTestShadow shadow;
    private ShadowClassTreeNode treeNode;

    protected void setUp() throws Exception {
        shadow = new ComparableTestShadow(new SuccessfulTestCase());
        treeNode = new ShadowClassTreeNode(shadow);
    }

    public void testRemoveSelf() throws Exception {
        ShadowCabinet cabinet = new ShadowCabinet();
        cabinet.add(shadow);
        treeNode.removeSelf();
        assertEquals(0, cabinet.size());
    }

    public void testEquals() throws Exception {
        assertEquals(treeNode, new ShadowClassTreeNode(shadow));

        assertFalse(treeNode.equals(new ShadowClassTreeNode(new ComparableTestShadow(this))));
        assertFalse(treeNode.equals(null));
        assertFalse(treeNode.equals(this));
    }

    public void testHashCode() throws Exception {
        assertEquals(treeNode.hashCode(), new ShadowClassTreeNode(shadow).hashCode());

        assertFalse(treeNode.hashCode() == new ShadowClassTreeNode(new ComparableTestShadow(this)).hashCode());
    }

    public void testIsRemovable() throws Exception {
        assertTrue(treeNode.isRemovable());
    }

    public void testIsSource() throws Exception {
        assertTrue(treeNode.isSource());
    }

    public void testShouldVisitShadowAndGetClassNameAsLabel() throws Exception {
        assertEquals("SuccessfulTestCase", treeNode.label());
    }

    public void testShouldVisitShadowAndGetPackageNameAsAccessory() throws Exception {
        assertEquals("com.thoughtworks.shadow.junit", treeNode.accessory());
    }
}
