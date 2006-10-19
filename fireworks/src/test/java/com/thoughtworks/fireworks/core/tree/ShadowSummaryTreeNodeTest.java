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

import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.fireworks.stubs.SimpleShadowVisitor;
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

    public void testShouldDoNothingWhenAcceptAShadowVisitor() throws Exception {
        node.accept(new SimpleShadowVisitor());
        config.assertDidNothing();
        assertEquals("0 Test Class", node.label());
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
