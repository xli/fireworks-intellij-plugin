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
