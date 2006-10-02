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
