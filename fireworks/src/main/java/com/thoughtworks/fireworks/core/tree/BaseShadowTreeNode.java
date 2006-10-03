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

public abstract class BaseShadowTreeNode implements ShadowTreeNode, ShadowVisitor {
    private final ShadowTreeNode parent;
    private final Shadow shadow;

    public BaseShadowTreeNode(ShadowTreeNode parent, Shadow shadow) {
        this.shadow = shadow;
        this.parent = parent;
        shadow.accept(this);
    }

    public ShadowTreeNode parent() {
        return parent;
    }

    public boolean isSource() {
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BaseShadowTreeNode))
            return false;

        final BaseShadowTreeNode that = (BaseShadowTreeNode) o;

        return shadow.equals(that.shadow);
    }

    public int hashCode() {
        return shadow.hashCode();
    }

    public void accept(ShadowVisitor visitor) {
        shadow.accept(visitor);
    }
}
