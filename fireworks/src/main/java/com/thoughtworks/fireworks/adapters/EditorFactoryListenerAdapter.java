package com.thoughtworks.fireworks.adapters;

import com.intellij.openapi.editor.event.EditorFactoryAdapter;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.controllers.TestCaseOpenedListener;

public class EditorFactoryListenerAdapter extends EditorFactoryAdapter {
    private final TestCaseOpenedListener listener;
    private final ProjectAdapter project;

    public EditorFactoryListenerAdapter(TestCaseOpenedListener listener, ProjectAdapter project) {
        this.listener = listener;
        this.project = project;
    }

    public void editorCreated(EditorFactoryEvent event) {
        listener.checkDocument(document(event));
    }

    private DocumentAdaptee document(EditorFactoryEvent event) {
        return project.createDocumentAdaptee(event.getEditor().getDocument());
    }
}
