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

import com.intellij.psi.search.PsiSearchHelper;
import com.thoughtworks.fireworks.controllers.RunAllTestsActionListener;
import com.thoughtworks.fireworks.core.AllTestShadowCabinet;
import junit.framework.TestResult;

public class RunAllTestsTask implements RunAllTestsActionListener {
    private final ProjectAdapter project;
    private final AllTestShadowCabinet cabinet;

    public RunAllTestsTask(ProjectAdapter project, AllTestShadowCabinet cabinet) {
        this.project = project;
        this.cabinet = cabinet;
    }

    public void actionPerformed(final TestResult result) {
        cabinet.init();
        searchTestCases();
        cabinet.action(result);
    }

    private void searchTestCases() {
        project.runProcessWithProgressSynchronously(new Runnable() {
            public void run() {
                PsiSearchHelper searchHelper = project.getPsiManager().getSearchHelper();
                SearchTestClassProcessor processor = new SearchTestClassProcessor(project, cabinet);

                searchHelper.processAllClasses(processor, project.getProjectTestJavaFileScope());
            }
        }, "Searching test class...", true);
    }
}
