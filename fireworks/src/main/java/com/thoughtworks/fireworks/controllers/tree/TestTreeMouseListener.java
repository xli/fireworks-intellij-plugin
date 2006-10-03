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
package com.thoughtworks.fireworks.controllers.tree;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestTreeMouseListener extends MouseAdapter {
    private final TestTree tree;

    public TestTreeMouseListener(TestTree tree) {
        this.tree = tree;
    }

    public void mouseClicked(MouseEvent e) {
        TreePath pathForLocation = tree.getPathForLocation(e.getX(), e.getY());
        if (pathForLocation == null) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            tree.setSelectionPath(pathForLocation);
            tree.showMenu(pathForLocation, e.getX(), e.getY());
        }
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
            tree.jumpToSourceOfNode(pathForLocation);
        }
    }
}
