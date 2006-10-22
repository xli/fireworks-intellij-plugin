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
package com.thoughtworks.fireworks.adapters.document;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiFile;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.adapters.astnode.ASTNodeAdaptee;
import com.thoughtworks.fireworks.adapters.astnode.ASTNodeAdapteeFactory;
import com.thoughtworks.fireworks.controllers.timer.ConfiguredTimer;

public class DocumentChangedAction implements Runnable {
    private final ProjectAdapter project;
    private final Document document;
    private final ASTNodeAdaptee docNodeBeforeChange;
    private final ConfiguredTimer timer;
    private final AllEditorsOpenedAdapter editors;

    public DocumentChangedAction(ProjectAdapter project, Document document, ConfiguredTimer timer, AllEditorsOpenedAdapter editors) {
        this.project = project;
        this.document = document;
        this.timer = timer;
        this.editors = editors;

        this.docNodeBeforeChange = toASTNodeAdaptee(cloneASTNodeOfDocument(document));
    }

    public void run() {
        project.getPsiDocumentManager().commitDocument(document);
        ASTNodeAdaptee docNodeAfterChange = toASTNodeAdaptee(cloneASTNodeOfDocument(document));
        if (docNodeBeforeChange.equalsInLanguage(docNodeAfterChange)) {
            return;
        }
        if (editors.documentsInSourceOrTestContentAreValidAndTheyAreNotXmlOrDtdFiles()) {
            timer.schedule();
        } else {
            timer.reschedule();
        }
    }

    private ASTNodeAdaptee toASTNodeAdaptee(ASTNode node) {
        return ASTNodeAdapteeFactory.getInstance().toASTNodeAdapter(node);
    }

    private ASTNode cloneASTNodeOfDocument(Document document) {
        PsiFile psiFile = project.getPsiDocumentManager().getPsiFile(document);
        return psiFile.getNode().copyElement();
    }
}
