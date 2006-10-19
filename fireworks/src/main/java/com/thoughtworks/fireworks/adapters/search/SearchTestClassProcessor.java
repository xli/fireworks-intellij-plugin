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
package com.thoughtworks.fireworks.adapters.search;

import com.intellij.psi.PsiClass;
import com.intellij.psi.search.PsiElementProcessor;
import com.thoughtworks.fireworks.adapters.ProgressIndicatorUtils;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.adapters.VirtualFileAdaptee;
import com.thoughtworks.fireworks.adapters.VirtualFileAdapter;
import com.thoughtworks.fireworks.adapters.psi.PsiClassAdapter;
import com.thoughtworks.fireworks.core.TestCollection;

import java.io.FileNotFoundException;

public class SearchTestClassProcessor implements PsiElementProcessor<PsiClass> {
    private final ProjectAdapter project;
    private final TestCollection testCollection;

    private int found = 0;
    private int classes = 0;

    public SearchTestClassProcessor(ProjectAdapter project, TestCollection testCollection) {
        this.project = project;
        this.testCollection = testCollection;
    }

    public boolean execute(PsiClass element) {
        classes++;
        ProgressIndicatorUtils.displayAsText("Classes in the test source folders: " + classes + " - Found test classes: " + found);
        ProgressIndicatorUtils.displayAsText2("Checking element: " + element);

        if (element.getQualifiedName() == null) {
            return true;
        }
        if (element.isWritable() && getPsiClass(element).isJUnitTestCase(project)) {
            found++;
            addIntoTestCollection(element);
        }
        return true;
    }

    private void addIntoTestCollection(PsiClass element) {
        try {
            testCollection.add(getFile(element).createSunshine(), element.getQualifiedName());
        } catch (FileNotFoundException e) {
        }
    }

    private PsiClassAdapter getPsiClass(PsiClass element) {
        return new PsiClassAdapter(element);
    }

    private VirtualFileAdaptee getFile(PsiClass element) throws FileNotFoundException {
        return VirtualFileAdapter.getFile(element, project);
    }

}
