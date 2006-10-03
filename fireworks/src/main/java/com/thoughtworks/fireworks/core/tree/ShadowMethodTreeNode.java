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

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class ShadowMethodTreeNode extends BaseShadowTreeNode implements ShadowVisitor {
    private String accessory;
    private String label;

    public ShadowMethodTreeNode(Shadow shadow, ShadowTreeNode parent) {
        super(parent, shadow);
    }

    public ShadowMethodTreeNode(Shadow shadow) {
        this(shadow, null);
    }

    public boolean isRemovable() {
        return false;
    }

    public void removeSelf() {
    }

    public String label() {
        return label;
    }

    public String accessory() {
        return accessory;
    }

    public void visitTestClassName(String testClassName) {
        accessory = testClassName;
    }

    public void visitTestMethodName(String testMethodName) {
        label = testMethodName;
    }

    public void end() {
    }

}
