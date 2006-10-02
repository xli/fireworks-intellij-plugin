package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.shadow.Sunshine;

public interface RefactoringListenerProvider {
    void addListener(DocumentAdaptee documentAdaptee, Sunshine sunshine, String className);

    void removeListener(DocumentAdaptee documentAdaptee);
}
