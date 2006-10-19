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

import com.intellij.openapi.editor.event.DocumentEvent;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.adapters.psi.PsiFileAdapter;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;

public class DocumentEventAdapter {
    private final DocumentEvent event;
    private final ProjectAdapter project;

    public DocumentEventAdapter(DocumentEvent event, ProjectAdapter project) {
        this.event = event;
        this.project = project;
    }

    public boolean documentContentChangedExcludeComment() {
        return isContentChanged() && isCommentChanged();
    }

    private boolean isCommentChanged() {
        PsiFileAdapter file = new PsiFileAdapter(project, event.getDocument());
        return !file.isCommentAt(event.getOffset());
    }

    private boolean isContentChanged() {
        String newFragment = event.getNewFragment().toString().trim();
        String oldFragment = event.getOldFragment().toString().trim();
        return (newFragment.length() > 0 || oldFragment.length() > 0);
    }

    public boolean documentInSourceOrTestContent() {
        return toDocument().isInSourceOrTestContent();
    }

    private DocumentAdaptee toDocument() {
        return project.createDocumentAdaptee(event.getDocument());
    }
}
