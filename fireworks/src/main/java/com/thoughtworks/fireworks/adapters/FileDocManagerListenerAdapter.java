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
import com.intellij.openapi.fileEditor.FileDocumentManagerAdapter;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.controllers.TestCaseOpenedListener;

public class FileDocManagerListenerAdapter extends FileDocumentManagerAdapter {
    private final TestCaseOpenedListener listener;
    private final ProjectAdapter project;

    public FileDocManagerListenerAdapter(TestCaseOpenedListener listener, ProjectAdapter project) {
        this.listener = listener;
        this.project = project;
    }

    public void beforeDocumentSaving(Document document) {
        listener.checkDocument(document(document));
    }

    private DocumentAdaptee document(Document document) {
        return project.createDocumentAdaptee(document);
    }
}
