package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.TestShadowMap;
import com.thoughtworks.shadow.Sunshine;

public class TestCaseOpenedListener {
    private final TestShadowMap shadowMap;
    private final RefactoringListenerProvider provider;

    public TestCaseOpenedListener(TestShadowMap shadowMap, RefactoringListenerProvider provider) {
        this.shadowMap = shadowMap;
        this.provider = provider;
    }

    public void checkDocument(DocumentAdaptee documentAdaptee) {
        if (isWritableJavaFile(documentAdaptee)) {
            final Sunshine sunshine = documentAdaptee.getSunshine();
            final String className = documentAdaptee.getJavaFileClassName();
            if (documentAdaptee.isExpectedJUnitTestCase()) {
                shadowMap.addTestShadow(sunshine, className);
                provider.addListener(documentAdaptee, sunshine, className);
            } else {
                shadowMap.removeTestShadow(sunshine, className);
                provider.removeListener(documentAdaptee);
            }
        }
    }

    private boolean isWritableJavaFile(DocumentAdaptee documentAdaptee) {
        return documentAdaptee.isWritable() && documentAdaptee.isJavaFile();
    }
}
