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

import com.thoughtworks.shadow.ComparableTestShadow;

public class ShadowClassTreeNode extends BaseShadowTreeNode {

    private final ComparableTestShadow shadow;
    private String className;
    private String packageName;

    public ShadowClassTreeNode(ComparableTestShadow shadow, ShadowTreeNode parent) {
        super(parent, shadow);
        this.shadow = shadow;
    }

    public ShadowClassTreeNode(ComparableTestShadow shadow) {
        this(shadow, null);
    }

    public boolean isRemovable() {
        return true;
    }

    public void removeSelf() {
        shadow.removeSelfFromContainer();
    }

    public String label() {
        return className;
    }

    public String accessory() {
        return packageName;
    }

    public void visitTestClassName(String testClassName) {
        int index = testClassName.lastIndexOf(".");
        className = testClassName.substring(index + 1);
        if (index >= 0) {
            packageName = testClassName.substring(0, index);
        }
    }

    public void visitTestMethodName(String testMethodName) {
    }

    public void end() {
    }
}
