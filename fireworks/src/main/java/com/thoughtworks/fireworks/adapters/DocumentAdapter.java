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
import com.intellij.psi.PsiClass;
import com.thoughtworks.fireworks.adapters.psi.PsiClassAdapter;
import com.thoughtworks.fireworks.adapters.psi.PsiFileAdapter;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.shadow.Sunshine;

import java.io.FileNotFoundException;

public class DocumentAdapter implements DocumentAdaptee {
    private final ProjectAdapter project;
    private final Document document;

    public DocumentAdapter(Document document, ProjectAdapter project) {
        if (document == null) {
            throw new DocumentAdapterException("Document(" + document + ") should not be null");
        }
        this.document = document;
        this.project = project;
    }

    public boolean isWritable() {
        return document.isWritable();
    }

    public boolean isJavaFile() {
        try {
            return VirtualFileAdapter.getFile(document, project).isJavaSourceFile();
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean isExpectedJUnitTestCase() {
        try {
            return new PsiClassAdapter(getPsiClass()).isJUnitTestCase(project);
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public Sunshine getSunshine() {
        try {
            return VirtualFileAdapter.getFile(document, project).createSunshine();
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public String getJavaFileClassName() {
        try {
            return VirtualFileAdapter.getFile(document, project).getJavaFileClassName();
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public PsiClass getPsiClass() throws FileNotFoundException {
        return VirtualFileAdapter.getFile(document, project).getPsiClass();
    }

    public boolean hasErrors() {
        return project.createMarkupAdapter(document).hasErrors();
    }

    public boolean isNotXml() {
        return new PsiFileAdapter(project, document).isNotXmlLanguage();
    }

    public boolean isNotDtd() {
        return new PsiFileAdapter(project, document).isNotDtdLanguage();
    }

    public boolean isInSourceOrTestContent() {
        try {
            return VirtualFileAdapter.getFile(document, project).isInSourceOrTestContent();
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
