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
package com.thoughtworks.fireworks.ui.tree;

import com.thoughtworks.fireworks.core.tree.ShadowMethodTreeNode;
import com.thoughtworks.fireworks.core.tree.ShadowTreeNode;
import com.thoughtworks.fireworks.core.tree.TestStatusSummaryListener;
import com.thoughtworks.shadow.Shadow;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestShadowTreeRenderer implements TreeCellRenderer, TestStatusSummaryListener {

    private Map<ShadowTreeNode, Icon> icons = Collections.synchronizedMap(new HashMap<ShadowTreeNode, Icon>());

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                  boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {
        ShadowTreeNode node = ((ShadowTreeNode) value);

        Component mainComp = mainLabel(tree, node, sel, expanded, leaf, row, hasFocus);
        Component accessoryComp = accessoryLabel(node, tree, sel, row, hasFocus);
        return panel(mainComp, accessoryComp);
    }

    private Component mainLabel(JTree tree, ShadowTreeNode node, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DefaultTreeCellRenderer mainLabel = new DefaultTreeCellRenderer();
        mainLabel.getTreeCellRendererComponent(tree, node.label(), sel, expanded, leaf, row, hasFocus);
        mainLabel.setIcon(icons.get(node));
        return mainLabel;
    }

    private JPanel panel(Component mainLabel, Component accessoryLabel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 1, 1));
        panel.setBackground(mainLabel.getBackground());
        panel.add(mainLabel);
        panel.add(accessoryLabel);
        return panel;
    }

    private Component accessoryLabel(ShadowTreeNode node, JTree tree, boolean sel, int row, boolean hasFocus) {
        DefaultTreeCellRenderer accessoryLabel = new DefaultTreeCellRenderer();
        accessoryLabel.setBackgroundSelectionColor(accessoryLabel.getBackground());
        accessoryLabel.setBorderSelectionColor(accessoryLabel.getBackground());
        accessoryLabel.setTextSelectionColor(Color.GRAY);
        accessoryLabel.setTextNonSelectionColor(Color.GRAY);
        accessoryLabel.setLeafIcon(null);
        accessoryLabel.setClosedIcon(null);
        accessoryLabel.setOpenIcon(null);

        return accessoryLabel.getTreeCellRendererComponent(tree, node.accessory(), sel, false, false, row, hasFocus);
    }

    public void summaryStatusChanged(Icon status) {
    }

    public void statusChanged(Object key, Icon status) {
        icons.put(toShadowTreeNode(key), status);
    }

    private ShadowTreeNode toShadowTreeNode(Object key) {
        if (key instanceof ShadowTreeNode) {
            return (ShadowTreeNode) key;
        }
        return new ShadowMethodTreeNode((Shadow) key, null);
    }
}
