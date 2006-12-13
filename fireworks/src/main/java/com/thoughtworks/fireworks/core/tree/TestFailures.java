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

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.Shadow;

import java.util.*;

public class TestFailures {

    private final Map<ShadowTreeNode, List<Throwable>> testFailures = new HashMap<ShadowTreeNode, List<Throwable>>();

    private void put(ShadowTreeNode node, Throwable t) {
        getFailures(node).add(t);
        putIntoParent(node, t);
    }

    private void putIntoParent(ShadowTreeNode node, Throwable t) {
        if (node.parent() != null) {
            getFailures(node.parent()).add(t);
            putIntoParent(node.parent(), t);
        }
    }

    private List<Throwable> getFailures(ShadowTreeNode node) {
        List<Throwable> failures = testFailures.get(node);
        if (failures == null) {
            failures = new ArrayList<Throwable>();
            testFailures.put(node, failures);
        }
        return failures;
    }

    public void remove(ShadowTreeNode node) {
        removeLogFromNode(testFailures.remove(node), node.parent());
        removeChildren(node);
    }

    private void removeLogFromNode(List<Throwable> failures, ShadowTreeNode node) {
        if (failures == null || failures.isEmpty()) {
            return;
        }
        for (int i = 0; i < failures.size(); i++) {
            getFailures(node).remove(failures.get(i));
        }
    }

    private void removeChildren(ShadowTreeNode node) {
        Set<ShadowTreeNode> shouldBeRemovedChildren = new HashSet<ShadowTreeNode>();
        for (ShadowTreeNode child : testFailures.keySet()) {
            if (child.parent() != null && child.parent().equals(node)) {
                shouldBeRemovedChildren.add(child);
            }
        }

        for (ShadowTreeNode shouldBeRemovedNode : shouldBeRemovedChildren) {
            testFailures.remove(shouldBeRemovedNode);
        }
    }

    public void output(ShadowTreeNode node, ConsoleViewAdaptee consoleView) {
        List<Throwable> failures = getFailures(node);
        StringBuffer buffer = new StringBuffer();
        for (Throwable t : failures) {
            buffer.append(Utils.toString(t));
        }
        consoleView.clearAndPrint(buffer.toString());
    }

    private final Map<Shadow, Throwable> buffer = new HashMap<Shadow, Throwable>();

    public void addIntoBuffer(Shadow shadow, Throwable t) {
        buffer.put(shadow, t);
    }

    public void commitBuffer(ShadowTreeNode parent) {
        for (Iterator<Shadow> iter = buffer.keySet().iterator(); iter.hasNext();) {
            Shadow shadow = iter.next();
            put(new ShadowMethodTreeNode(shadow, parent), buffer.get(shadow));
        }
        buffer.clear();
    }
}
