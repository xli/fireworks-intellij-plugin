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
package com.thoughtworks.fireworks.adapters;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.project.Project;

import java.util.TimerTask;

class AutoRunTestsTimerTask extends TimerTask {
    private final DocumentListenerAdapter listener;

    AutoRunTestsTimerTask(DocumentListenerAdapter listener) {
        this.listener = listener;
    }

    public void run() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                Editor[] editors = EditorFactory.getInstance().getAllEditors();
                if (hasWritableAndValidDoc(editors)) {
                    listener.fireCabinetActionEvent();
                }
            }
        });
    }

    private boolean hasWritableAndValidDoc(Editor[] editors) {
        boolean hasWritableDoc = false;
        for (int i = 0; i < editors.length; i++) {
            if (isEditingAWritableDoc(editors[i])) {
                hasWritableDoc = true;
            }
            if (isInvalidFileDocument(editors[i])) {
                return false;
            }
        }
        return hasWritableDoc;
    }

    private boolean isEditingAWritableDoc(Editor editor) {
        return editor.isViewer() || !editor.getDocument().isWritable();
    }

    private boolean isInvalidFileDocument(Editor editor) {
        Document doc = editor.getDocument();
        Project project = editor.getProject();
        return isInvalid(doc.getMarkupModel(project));
    }

    private boolean isInvalid(MarkupModel markupModel) {
        RangeHighlighter[] allHighlighters = markupModel.getAllHighlighters();
        for (int i = 0; i < allHighlighters.length; i++) {
            if (allHighlighters[i] != null && allHighlighters[i].getLayer() == HighlighterLayer.ERROR) {
                return true;
            }
        }
        return false;
    }
}
