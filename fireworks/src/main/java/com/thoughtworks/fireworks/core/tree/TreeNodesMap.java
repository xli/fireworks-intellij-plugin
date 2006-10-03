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

import java.util.*;

public class TreeNodesMap {

    private Map<ShadowTreeNode, List<ShadowTreeNode>> nodes = new HashMap();
    private final ShadowTreeNode root;

    public TreeNodesMap(ShadowTreeNode root) {
        this.root = root;
        nodes.put(root, new ArrayList());
    }

    public void add(ShadowTreeNode node) {
        getParentChildren(node).add(node);
    }

    public boolean contains(ShadowTreeNode node) {
        return getParentChildren(node).contains(node);
    }

    public void remove(ShadowTreeNode node) {
        getParentChildren(node).remove(node);
        nodes.remove(node);
    }

    public ShadowTreeNode getChild(ShadowTreeNode parent, int index) {
        return getChildren(parent).get(index);
    }

    public int getChildCount(ShadowTreeNode parent) {
        return getChildren(parent).size();
    }

    public int getIndexOfChild(ShadowTreeNode parent, Object child) {
        return getChildren(parent).indexOf(child);
    }

    public List<ShadowTreeNode> clearTestMethodNode() {
        List<ShadowTreeNode> testMethodNodes = new ArrayList();
        for (Iterator<ShadowTreeNode> iter = nodes.keySet().iterator(); iter.hasNext();) {
            ShadowTreeNode node = iter.next();
            boolean isTestClassNode = node.parent() == root;
            if (isTestClassNode) {
                testMethodNodes.addAll(getChildren(node));
            }
        }

        return testMethodNodes;
    }

    private List<ShadowTreeNode> getParentChildren(ShadowTreeNode node) {
        return getChildren(node.parent());
    }

    private List<ShadowTreeNode> getChildren(ShadowTreeNode parent) {
        if (nodes.get(parent) == null) {
            nodes.put(parent, new ArrayList());
        }
        return nodes.get(parent);
    }

}
