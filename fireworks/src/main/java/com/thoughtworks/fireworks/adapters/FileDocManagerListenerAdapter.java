package com.thoughtworks.fireworks.adapters;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerAdapter;
import com.intellij.openapi.fileEditor.VetoDocumentSavingException;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.controllers.TestCaseOpenedListener;

public class FileDocManagerListenerAdapter extends FileDocumentManagerAdapter {
    private final TestCaseOpenedListener listener;
    private final ProjectAdapter project;

    public FileDocManagerListenerAdapter(TestCaseOpenedListener listener, ProjectAdapter project) {
        this.listener = listener;
        this.project = project;
    }

    public void beforeDocumentSaving(Document document) throws VetoDocumentSavingException {
        listener.checkDocument(document(document));
    }

    private DocumentAdaptee document(Document document) {
        return project.createDocumentAdaptee(document);
    }
}
