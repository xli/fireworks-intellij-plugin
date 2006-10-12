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

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.thoughtworks.fireworks.core.FireworksConfig;

import java.awt.*;
import java.awt.event.AWTEventListener;

public class DocumentListenerAdapter implements DocumentListener, AWTEventListener {
    private final AutoRunTaskTimer timer;
    private final FireworksConfig config;
    private final ProjectAdapter project;

    public DocumentListenerAdapter(FireworksConfig config, ProjectAdapter project, AutoRunTaskTimer timer) {
        this.config = config;
        this.project = project;
        this.timer = timer;
    }

    public void documentChanged(DocumentEvent event) {
        final Document document = event.getDocument();
        boolean inputLetters = event.getNewFragment().toString().trim().length() > 0;
        if (inputLetters && project.createDocumentAdaptee(document).isInSourceOrTestContent()) {
            if (hasWritableAndValidDocInEditors(document)) {
                timer.schedule(config.autoRunTestsDelayTime());
            }
        }
    }

    private boolean hasWritableAndValidDocInEditors(Document document) {
        return new EditorsAdapter(docEditors(document)).hasWritableAndValidDoc();
    }

    private Editor[] docEditors(Document document) {
        return EditorFactory.getInstance().getEditors(document);
    }

    public void eventDispatched(AWTEvent event) {
        timer.reSchedule();
    }

    public void beforeDocumentChange(DocumentEvent event) {
    }
}
