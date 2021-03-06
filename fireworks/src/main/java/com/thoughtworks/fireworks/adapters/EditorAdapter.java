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

import com.intellij.codeInsight.template.Template;
import com.intellij.openapi.editor.Editor;

class EditorAdapter implements LiveTemplateAdapter.EditorAdaptee {
    private final ProjectAdapter project;
    private final Editor editor;

    public EditorAdapter(ProjectAdapter project, Editor editor) {
        this.project = project;
        this.editor = editor;
    }

    public boolean isLiveTemplateWorking() {
        return getActiveTemplate(editor) != null;
    }

    private Template getActiveTemplate(Editor editor) {
        return project.getTemplateManager().getActiveTemplate(editor);
    }
}
