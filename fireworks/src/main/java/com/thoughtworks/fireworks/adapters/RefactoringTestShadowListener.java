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
