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

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.developer.Thought;

public class AllEditorsOpenedAdapter implements Thought {
    private final ProjectAdapter project;

    public AllEditorsOpenedAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public boolean isWorking() {
        return !documentsInSourceOrTestContentAreValidAndTheyAreNotXmlOrDtdFiles();
    }

    public boolean hasNonViewerEditorAndWritable() {
        Editor[] editors = EditorFactory.getInstance().getAllEditors();

        for (int i = 0; i < editors.length; i++) {
            if (isWritableNonViewer(editors[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean documentsInSourceOrTestContentAreValidAndTheyAreNotXmlOrDtdFiles() {
        Editor[] editors = EditorFactory.getInstance().getAllEditors();

        for (int i = 0; i < editors.length; i++) {
            if (isWritableNonViewer(editors[i])) {
                DocumentAdaptee doc = toDocumentAdaptee(editors[i]);
                if (doc.hasErrors() && doc.isInSourceOrTestContent() && doc.isNotXml() && doc.isNotDtd()) {
                    return false;
                }
            }
        }
        return true;
    }

    private DocumentAdaptee toDocumentAdaptee(Editor editor) {
        return project.createDocumentAdaptee(editor.getDocument());
    }

    private boolean isWritableNonViewer(Editor editor) {
        return !editor.isViewer() && editor.getDocument().isWritable();
    }
}
