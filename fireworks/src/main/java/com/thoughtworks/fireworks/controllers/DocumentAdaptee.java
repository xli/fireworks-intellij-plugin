package com.thoughtworks.fireworks.controllers;

import com.intellij.psi.PsiElement;
import com.thoughtworks.shadow.Sunshine;

import java.io.FileNotFoundException;

public interface DocumentAdaptee {
    boolean isWritable();

    boolean isJavaFile();

    boolean isExpectedJUnitTestCase();

    Sunshine getSunshine();

    String getJavaFileClassName();

    PsiElement getPsiClass() throws FileNotFoundException;
}
