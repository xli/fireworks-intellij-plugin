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

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.thoughtworks.fireworks.adapters.document.AllEditorsOpenedAdapter;
import com.thoughtworks.fireworks.adapters.document.DocumentChangedAction;
import com.thoughtworks.fireworks.adapters.document.DocumentEventAdapter;
import com.thoughtworks.fireworks.controllers.timer.ConfiguredTimer;
import com.thoughtworks.fireworks.core.ApplicationAdaptee;

import java.awt.*;
import java.awt.event.AWTEventListener;

public class DocumentListenerAdapter implements DocumentListener, AWTEventListener {

    private final ApplicationAdaptee application;
    private final ProjectAdapter project;
    private final ConfiguredTimer timer;
    private final AllEditorsOpenedAdapter editors;
    private Runnable action;

    public DocumentListenerAdapter(ApplicationAdaptee application,
                                   ProjectAdapter project,
                                   ConfiguredTimer timer,
                                   AllEditorsOpenedAdapter editors) {
        this.application = application;
        this.project = project;
        this.timer = timer;
        this.editors = editors;
    }

    public void eventDispatched(AWTEvent event) {
        timer.reschedule();
    }

    public void documentChanged(final DocumentEvent event) {
        if (action != null) {
            application.invokeLater(action);
        }
    }

    public void beforeDocumentChange(final DocumentEvent event) {
        if (documentEvent(event).documentInSourceOrTestContent() && editors.hasNonViewerEditorAndWritable()) {
            action = new DocumentChangedAction(project, event.getDocument(), timer, editors);
        } else {
            action = null;
        }
    }

    private DocumentEventAdapter documentEvent(DocumentEvent event) {
        return new DocumentEventAdapter(event, project);
    }
}
