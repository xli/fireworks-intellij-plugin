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

import com.intellij.psi.PsiElement;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.TestShadowMap;
import com.thoughtworks.shadow.Sunshine;

public class RefactoringTestShadowListener implements RefactoringElementListener {
    private final Sunshine sunshine;
    private final String className;
    private final ProjectAdapter project;
    private final TestShadowMap testShadowMap;

    public RefactoringTestShadowListener(TestShadowMap testShadowMap,
                                         Sunshine sunshine,
                                         String className,
                                         ProjectAdapter project) {
        this.testShadowMap = testShadowMap;
        this.sunshine = sunshine;
        this.className = className;
        this.project = project;
    }

    public void elementMoved(PsiElement newElement) {
        refactorTestShadowMap(newDocument(newElement));
    }

    public void elementRenamed(PsiElement newElement) {
        refactorTestShadowMap(newDocument(newElement));
    }

    public void refactorTestShadowMap(DocumentAdaptee newDoc) {
        Sunshine newSunshine = newDoc.getSunshine();
        String newClassName = newDoc.getJavaFileClassName();
        testShadowMap.removeTestShadow(sunshine, className);
        testShadowMap.addTestShadow(newSunshine, newClassName);
    }

    private DocumentAdaptee newDocument(PsiElement newElement) {
        return project.createDocumentAdaptee(newElement.getContainingFile());
    }
}
