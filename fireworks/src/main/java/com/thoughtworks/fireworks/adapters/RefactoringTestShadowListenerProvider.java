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
import com.intellij.refactoring.listeners.RefactoringElementListenerProvider;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.controllers.RefactoringListenerProvider;
import com.thoughtworks.fireworks.core.TestShadowMap;
import com.thoughtworks.shadow.Sunshine;
import org.picocontainer.Startable;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class RefactoringTestShadowListenerProvider implements RefactoringElementListenerProvider, RefactoringListenerProvider, Startable {

    private class RefactoringProviderListener implements RefactoringElementListener {
        private final PsiElement element;
        private final RefactoringElementListener listener;

        public RefactoringProviderListener(PsiElement element, RefactoringElementListener listener) {
            this.element = element;
            this.listener = listener;
        }

        public void elementMoved(PsiElement newElement) {
            listener.elementMoved(newElement);
            refactorListenerMap(newElement, element, listener);
        }

        public void elementRenamed(PsiElement newElement) {
            listener.elementRenamed(newElement);
            refactorListenerMap(newElement, element, listener);
        }
    }

    private final TestShadowMap shadowMap;
    private final ProjectAdapter project;

    private final Map<PsiElement, RefactoringElementListener> listenerMap = new HashMap<PsiElement, RefactoringElementListener>();

    public RefactoringTestShadowListenerProvider(TestShadowMap shadowMap, ProjectAdapter project) {
        this.shadowMap = shadowMap;
        this.project = project;
    }

    public RefactoringElementListener getListener(final PsiElement element) {
        RefactoringElementListener listener = listenerMap.get(element);
        if (listener == null) {
            return null;
        }
        return new RefactoringProviderListener(element, listener);
    }

    private void refactorListenerMap(PsiElement newElement, PsiElement element, RefactoringElementListener listener) {
        listenerMap.remove(element);
        listenerMap.put(newElement, listener);
    }

    public void addListener(DocumentAdaptee documentAdaptee, Sunshine sunshine, String className) {
        PsiElement element = getMonitoringDocElement(documentAdaptee);
        RefactoringTestShadowListener listener = new RefactoringTestShadowListener(shadowMap, sunshine, className, project);
        listenerMap.put(element, listener);
    }

    public void removeListener(DocumentAdaptee documentAdaptee) {
        listenerMap.remove(getMonitoringDocElement(documentAdaptee));
    }

    private PsiElement getMonitoringDocElement(DocumentAdaptee documentAdaptee) {
        try {
            return documentAdaptee.getPsiClass();
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void start() {
        project.getRefactoringListenerManager().addListenerProvider(this);
    }

    public void stop() {
        project.getRefactoringListenerManager().removeListenerProvider(this);
    }
}
