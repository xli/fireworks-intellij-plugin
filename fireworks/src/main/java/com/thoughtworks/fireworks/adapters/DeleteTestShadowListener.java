package com.thoughtworks.fireworks.adapters;

import com.intellij.openapi.vfs.VirtualFileAdapter;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.psi.PsiFile;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.TestShadowMap;

public class DeleteTestShadowListener extends VirtualFileAdapter {
    private final TestShadowMap testShadowMap;
    private final ProjectAdapter project;

    public DeleteTestShadowListener(TestShadowMap testShadowMap, ProjectAdapter project) {
        this.testShadowMap = testShadowMap;
        this.project = project;
    }

    public void beforeFileDeletion(final VirtualFileEvent event) {
        PsiFile psiFile = project.getPsiManager().findFile(event.getFile());
        if (psiFile != null) {
            DocumentAdaptee doc = getDocument(psiFile);
            if (doc != null) {
                testShadowMap.removeTestShadow(doc.getSunshine(), doc.getJavaFileClassName());
            }
        }
    }

    private DocumentAdaptee getDocument(PsiFile psiFile) {
        try {
            return project.createDocumentAdaptee(psiFile);
        } catch (DocumentAdapterException e) {
            return null;
        }
    }
}
