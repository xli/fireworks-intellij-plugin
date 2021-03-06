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

import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import javax.swing.tree.TreePath;
import java.awt.event.ActionListener;

public class JumpToSourceActionListenerTest extends TestCase {
    public void testShouldRemoveSelfWhenActionPerformed() throws Exception {
        Mock tree = Turtle.mock(TestTree.class);
        TreePath selectionPath = new TreePath(this);
        tree.ifCall("getSelectionPath").willReturn(selectionPath);
        ActionListener listener = new JumpToSourceActionListener((TestTree) tree.mockTarget());
        listener.actionPerformed(null);

        tree.assertDid("jumpToSourceOfNode").with(selectionPath);
    }
}
