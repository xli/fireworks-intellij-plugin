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

import com.intellij.openapi.vfs.VirtualFileAdapter;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.psi.PsiFile;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.TestShadowMap;

public class DeleteTestShadowListener extends VirtualFileAdapter {
    private final TestShadowMap testShadowMap;
    private final ProjectAdapter project;

    public DeleteTestShadowListener(TestShadowMap testShadowMap, ProjectAdapter project) {
        this.testShadowMap = testShadowMap;
        this.project = project;
    }

    public void beforeFileDeletion(final VirtualFileEvent event) {
        PsiFile psiFile = project.getPsiManager().findFile(event.getFile());
        if (psiFile != null) {
            DocumentAdaptee doc = getDocument(psiFile);
            if (doc != null) {
                testShadowMap.removeTestShadow(doc.getSunshine(), doc.getJavaFileClassName());
            }
        }
    }

    private DocumentAdaptee getDocument(PsiFile psiFile) {
        try {
            return project.createDocumentAdaptee(psiFile);
        } catch (DocumentAdapterException e) {
            return null;
        }
    }
}
