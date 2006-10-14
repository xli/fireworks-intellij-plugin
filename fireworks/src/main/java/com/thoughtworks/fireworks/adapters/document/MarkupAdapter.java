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

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.project.Project;

public class MarkupAdapter {
    private final Document doc;
    private final Project project;

    public MarkupAdapter(Document doc, Project project) {
        this.doc = doc;
        this.project = project;
    }

    public boolean hasErrors() {
        RangeHighlighter[] highlighters = doc.getMarkupModel(project).getAllHighlighters();
        for (int i = 0; i < highlighters.length; i++) {
            if (isErrorMarkup(highlighters[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isErrorMarkup(RangeHighlighter highlighter) {
        return highlighter != null && highlighter.getLayer() == HighlighterLayer.ERROR;
    }
}
