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

import com.thoughtworks.fireworks.stubs.SimpleShadowVisitor;
import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class BaseShadowTreeNodeAsShadowTest extends TestCase {
    public void testShouldDelegateAcceptShadowVisitorToShadowWhichIsPassedIntoConstructor() throws Exception {
        Mock shadow = Turtle.mock(Shadow.class);
        Shadow treeNode = new BaseShadowTreeNode(null, (Shadow) shadow.mockTarget()) {
            public String label() {
                return null;
            }

            public String accessory() {
                return null;
            }

            public boolean isRemovable() {
                return false;
            }

            public void removeSelf() {
            }

            public void visitTestClassName(String testClassName) {
            }

            public void visitTestMethodName(String testMethodName) {
            }

            public void end() {
            }
        };

        SimpleShadowVisitor visitor = new SimpleShadowVisitor();
        treeNode.accept(visitor);
        shadow.assertDid("accept").with(visitor);
    }
}
