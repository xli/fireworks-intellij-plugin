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

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.thoughtworks.fireworks.core.developer.Thought;

public class LiveTemplateAdapter implements Thought {

    static interface EditorAdaptee {
        EditorAdaptee NULL = new EditorAdaptee() {
            public boolean isLiveTemplateWorking() {
                return false;
            }
        };

        boolean isLiveTemplateWorking();
    }

    private final ProjectAdapter project;

    public LiveTemplateAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public boolean isWorking() {
        return activeEditor().isLiveTemplateWorking();
    }

    private EditorAdaptee activeEditor() {
        Editor[] editors = EditorFactory.getInstance().getAllEditors();
        for (int i = 0; i < editors.length; i++) {
            Editor editor = editors[i];
            if (!editor.isViewer() && editor.getContentComponent().isFocusOwner()) {
                return new EditorAdapter(project, editor);
            }
        }
        return EditorAdaptee.NULL;
    }
}
