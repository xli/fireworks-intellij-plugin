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

import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.shadow.ShadowVisitor;

public class ShadowSummaryTreeNode implements ShadowTreeNode {
    private final FireworksConfig config;
    private int childCount;

    public ShadowSummaryTreeNode(FireworksConfig config) {
        this.config = config;
    }

    public ShadowSummaryTreeNode() {
        this(null);
    }

    public boolean isRemovable() {
        return false;
    }

    public void removeSelf() {
    }

    public boolean isSource() {
        return false;
    }

    public String label() {
        return childCount + " Test Class";
    }

    public String accessory() {
        if (config == null) {
            return "";
        }
        return "Max: " + config.maxSize();
    }

    public ShadowTreeNode parent() {
        return null;
    }

    public void childrenIncreased() {
        childCount++;
    }

    public void childrenDecreased() {
        childCount--;
    }

    public void accept(ShadowVisitor visitor) {
    }
}
