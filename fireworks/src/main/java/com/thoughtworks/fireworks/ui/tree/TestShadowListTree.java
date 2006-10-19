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

import com.thoughtworks.fireworks.controllers.tree.*;
import com.thoughtworks.fireworks.core.ApplicationAdaptee;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.fireworks.core.tree.RemovableSource;
import com.thoughtworks.fireworks.core.tree.ShadowTreeModel;
import com.thoughtworks.fireworks.core.tree.ShadowTreeNode;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowCabinetListener;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TestShadowListTree extends JTree implements Log, TestTree, ShadowCabinetListener {
    private final ApplicationAdaptee application;
    private final JumpToSourceAdaptee jumpToSourceAdapter;
    private final ShadowTreeModel treeModel;
    private final TestTreePopupMenu menu;

    public TestShadowListTree(ApplicationAdaptee application, JumpToSourceAdaptee jumpToSourceAdapter, ShadowTreeModel treeModel, TreeCellRenderer renderer) {
        super(treeModel);
        this.application = application;
        this.jumpToSourceAdapter = jumpToSourceAdapter;
        this.treeModel = treeModel;
        this.menu = new TestTreePopupMenu(this);

        setCellRenderer(renderer);
        getModel().addTreeModelListener(new TestTreeModelListener(this));
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        addMouseListener(new TestTreeMouseListener(this));
    }

    public void output(final ConsoleViewAdaptee consoleView) {
        final ShadowTreeNode node = (ShadowTreeNode) getLastSelectedPathComponent();
        if (node != null) {
            treeModel.output(node, consoleView);
        }
    }

    public void showMenu(TreePath path, int x, int y) {
        menu.showMenu(((RemovableSource) path.getLastPathComponent()), x, y);
    }

    public void jumpToSourceOfNode(TreePath path) {
        ((Shadow) path.getLastPathComponent()).accept(jumpToSourceAdapter);
    }

    public void afterAddTest(ComparableTestShadow test) {
    }

    public void afterRemoveTest(ComparableTestShadow test) {
    }

    public void endAction() {
        application.invokeLater(new Runnable() {
            public void run() {
                reSelectRootNode();
            }
        });
    }

    public void reSelectRootNode() {
        super.clearSelection();
        super.setSelectionPath(rootNodeTreePath());
    }

    public void expandRootNode() {
        super.expandPath(rootNodeTreePath());
    }

    public void startAction() {
    }

    private TreePath rootNodeTreePath() {
        return new TreePath(treeModel.getRoot());
    }

}
