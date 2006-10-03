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

import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;
import junit.framework.TestCase;

public class ShadowMethodTreeNodeTest extends TestCase implements Shadow {
    private ShadowMethodTreeNode treeNode;

    protected void setUp() throws Exception {
        treeNode = new ShadowMethodTreeNode(this);
    }

    public void testEquals() throws Exception {
        assertEquals(treeNode, new ShadowMethodTreeNode(this));

        assertFalse(treeNode.equals(new ShadowClassTreeNode(new ComparableTestShadow(this))));
        assertFalse(treeNode.equals(null));
        assertFalse(treeNode.equals(this));
    }

    public void testHashCode() throws Exception {
        assertEquals(treeNode.hashCode(), new ShadowMethodTreeNode(this).hashCode());

        assertFalse(treeNode.hashCode() == new ShadowMethodTreeNode(new SuccessTestShadow()).hashCode());
    }

    public void testIsNotRemovable() throws Exception {
        assertFalse(treeNode.isRemovable());
        treeNode.removeSelf();
    }

    public void testIsSource() throws Exception {
        assertTrue(treeNode.isSource());
    }

    public void testShouldVisitShadowAndGetClassNameAsLabel() throws Exception {
        assertEquals("testMethodName", treeNode.label());
    }

    public void testShouldVisitShadowAndGetPackageNameAsAccessory() throws Exception {
        assertEquals(getClass().getName(), treeNode.accessory());
    }

    public void accept(ShadowVisitor visitor) {
        visitor.visitTestMethodName("testMethodName");
        visitor.visitTestClassName(getClass().getName());
    }
}
