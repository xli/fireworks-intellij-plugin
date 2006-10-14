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

import com.intellij.psi.search.PsiSearchHelper;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.core.TestCollection;

public class TestCaseSearcher {
    private final ProjectAdapter project;

    public TestCaseSearcher(ProjectAdapter project) {
        this.project = project;
    }

    public void action(final TestCollection collection) {
        project.runProcessWithProgressSynchronously(new Runnable() {
            public void run() {
                PsiSearchHelper searchHelper = project.getSearchHelper();
                SearchTestClassProcessor processor = new SearchTestClassProcessor(project, collection);
                searchHelper.processAllClasses(processor, project.getProjectTestJavaFileScope());
            }
        }, "Searching test class...", true);
    }
}
