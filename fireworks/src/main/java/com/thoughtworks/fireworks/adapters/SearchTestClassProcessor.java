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

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.PsiElementProcessor;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.AllTestShadowCabinet;

public class SearchTestClassProcessor implements PsiElementProcessor<PsiClass> {
    private final ProjectAdapter project;
    private final AllTestShadowCabinet cabinet;

    private int found = 0;
    private int classes = 0;

    public SearchTestClassProcessor(ProjectAdapter project, AllTestShadowCabinet cabinet) {
        this.project = project;
        this.cabinet = cabinet;
    }

    public boolean execute(PsiClass element) {
        classes++;
        ProgressIndicatorUtils.displayAsText("Classes in the test source folders: " + classes + " - Found test classes: " + found);
        ProgressIndicatorUtils.displayAsText2("Checking element: " + element);

        if (element.getQualifiedName() == null) {
            return true;
        }
        PsiFile psiFile = element.getContainingFile();
        DocumentAdaptee doc = project.createDocumentAdaptee(psiFile);

        if (doc.isWritable() && doc.isExpectedJUnitTestCase(element)) {
            found++;
            cabinet.addTestShadow(doc.getSunshine(), element.getQualifiedName());
        }
        return true;
    }

}
