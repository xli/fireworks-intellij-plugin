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

import com.thoughtworks.fireworks.core.tree.RemovableSource;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import javax.swing.tree.TreePath;
import java.awt.event.ActionListener;

public class RemoveTestNodeActionListenerTest extends TestCase {
    public void testShouldRemoveSelfWhenActionPerformed() throws Exception {
        Mock removable = Turtle.mock(RemovableSource.class);
        Mock tree = Turtle.mock(TestTree.class);
        tree.ifCall("getSelectionPath").willReturn(new TreePath(removable.mockTarget()));
        ActionListener listener = new RemoveTreeNodeActionListener((TestTree) tree.mockTarget());
        listener.actionPerformed(null);
        removable.assertDid("removeSelf");
    }
}
