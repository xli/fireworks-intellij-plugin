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
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.thoughtworks.fireworks.adapters.document.AllEditorsOpenedAdapter;
import com.thoughtworks.fireworks.adapters.document.DocumentEventAdapter;
import com.thoughtworks.fireworks.controllers.timer.ConfiguredTimer;

import java.awt.*;
import java.awt.event.AWTEventListener;

public class DocumentListenerAdapter implements DocumentListener, AWTEventListener {

    private final ConfiguredTimer timer;
    private final AllEditorsOpenedAdapter editors;
    private final ProjectAdapter project;

    public DocumentListenerAdapter(ProjectAdapter project, ConfiguredTimer timer, AllEditorsOpenedAdapter editors) {
        this.project = project;
        this.timer = timer;
        this.editors = editors;
    }

    public void eventDispatched(AWTEvent event) {
        timer.reschedule();
    }

    public void documentChanged(final DocumentEvent event) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                project.getPsiDocumentManager().commitDocument(event.getDocument());
                final DocumentEventAdapter eventAdapter = new DocumentEventAdapter(event, project);
                if (eventAdapter.documentInSourceOrTestContent()
                        && eventAdapter.documentContentChangedExcludeComment()
                        && editors.hasNonViewerEditorAndWritable()) {
                    if (editors.documentsInSourceOrTestContentAreValidAndTheyAreNotXmlOrDtdFiles()) {
                        timer.schedule();
                    } else {
                        //doto: cancelTasks => reschedule??
                        timer.reschedule();
                    }
                }
            }
        });
    }

    public void beforeDocumentChange(DocumentEvent event) {
    }
}
