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
import com.intellij.psi.tree.TokenSet;

import java.util.ArrayList;
import java.util.List;

public class ASTNodeAdapteeFactory {
    public static final ASTNodeAdapteeFactory FACTORY = new ASTNodeAdapteeFactory();

    public static ASTNodeAdapteeFactory getInstance() {
        return FACTORY;
    }

    private IgnoredTokenSet ignoredTokenSet = new IgnoredTokenSet();

    private ASTNodeAdapteeFactory() {
    }

    public ASTNodeAdaptee toASTNodeAdapter(ASTNode node) {
        ASTNode[] children = getChildrenIgnoreWhitespaceAndComment(node);
        if (children.length > 0) {
            return new ASTNodeChildrenAdapter(children);
        }
        return new ASTNodeAdapter(node);
    }

    private ASTNode[] getChildrenIgnoreWhitespaceAndComment(ASTNode node) {
        ASTNode[] children = node.getChildren(null);
        List<ASTNode> childrenIgnoreWhitespace = new ArrayList<ASTNode>();
        for (int i = 0; i < children.length; i++) {
            ASTNode child = children[i];
            if (getIgnoredLanguageTokenSet(node).contains(child.getElementType())) {
                continue;
            }
            if (child.getElementType().toString().contains("ERROR")) {
                continue;
            }
            childrenIgnoreWhitespace.add(child);
        }
        return childrenIgnoreWhitespace.toArray(new ASTNode[childrenIgnoreWhitespace.size()]);
    }

    private TokenSet getIgnoredLanguageTokenSet(ASTNode node) {
        return ignoredTokenSet.getByLanguage(node.getPsi().getLanguage());
    }

}
