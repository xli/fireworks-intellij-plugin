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
package com.thoughtworks.fireworks.adapters.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;

public class PsiFileAdapter {
    public static final String XML_LANGUAGE_ID = "XML";
    public static final String DTD_LANGUAGE_ID = "DTD";

    private PsiFile psiFile;

    public PsiFileAdapter(ProjectAdapter project, Document document) {
        psiFile = project.getPsiDocumentManager().getPsiFile(document);
    }

    public boolean isNotXmlLanguage() {
        return !XML_LANGUAGE_ID.equalsIgnoreCase(psiFile.getLanguage().getID());
    }

    public boolean isNotDtdLanguage() {
        return !DTD_LANGUAGE_ID.equalsIgnoreCase(psiFile.getLanguage().getID());
    }

    public boolean isCommentAt(int offset) {
        IElementType elementType = getElementType(offset);
        if (elementType == null) {
            return false;
        }

        return isLanguageComment(elementType) || isJavaDocComment(elementType);
    }

    private IElementType getElementType(int offset) {
        PsiElement elementAt = psiFile.findElementAt(offset);
        if (elementAt == null) {
            return null;
        }
        ASTNode node = elementAt.getNode();
        if (node == null) {
            return null;
        }
        return node.getElementType();
    }

    private boolean isJavaDocComment(IElementType elementType) {
        return JavaDocTokenType.ALL_JAVADOC_TOKENS.contains(elementType);
    }

    private boolean isLanguageComment(IElementType elementType) {
        Language language = elementType.getLanguage();
        if (language == null) {
            return false;
        }
        ParserDefinition parserDefinition = language.getParserDefinition();
        if (parserDefinition == null) {
            return false;
        }
        TokenSet commentTokens = parserDefinition.getCommentTokens();
        if (commentTokens == null) {
            return false;
        }

        return commentTokens.contains(elementType);
    }

}
