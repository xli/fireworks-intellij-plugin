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
package com.thoughtworks.fireworks.adapters.astnode;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class ASTNodeChildrenAdapter implements ASTNodeAdaptee {
    private final ASTNode[] children;

    ASTNodeChildrenAdapter(@NotNull ASTNode[] children) {
        this.children = children;
    }

    public boolean equalsInLanguage(ASTNodeAdaptee that) {
        if (this == that) {
            return true;
        }
        if (that == null || getClass() != that.getClass()) {
            return false;
        }
        return equalsChildren((ASTNodeChildrenAdapter) that);
    }

    private boolean equalsChildren(ASTNodeChildrenAdapter that) {
        if (children.length != that.children.length) {
            return false;
        }
        for (int i = 0; i < children.length; i++) {
            if (!equalsInLanguage(children[i], that.children[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean equalsInLanguage(ASTNode child, ASTNode thatChild) {
        return toAdaptee(child).equalsInLanguage(toAdaptee(thatChild));
    }

    private ASTNodeAdaptee toAdaptee(ASTNode node) {
        return ASTNodeAdapteeFactory.getInstance().toASTNodeAdapter(node);
    }
}
