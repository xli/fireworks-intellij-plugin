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
        if (doc.isWritable() && doc.isExpectedJUnitTestCase()) {
            found++;
            cabinet.addTestShadow(doc.getSunshine(), element.getQualifiedName());
        }
        return true;
    }

}
