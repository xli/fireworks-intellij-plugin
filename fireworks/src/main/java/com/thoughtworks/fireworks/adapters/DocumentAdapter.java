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

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.Utils;
import junit.framework.Test;
import junit.framework.TestCase;

import java.io.FileNotFoundException;


public class DocumentAdapter implements DocumentAdaptee {
    private final ProjectAdapter project;
    private final Document document;
    private final FireworksConfig config;

    public DocumentAdapter(Document document, ProjectAdapter project, FireworksConfig config) {
        if (document == null) {
            throw new DocumentAdapterException("Document(" + document + ") should not be null");
        }
        this.document = document;
        this.project = project;
        this.config = config;
    }

    public boolean isWritable() {
        return document.isWritable();
    }

    public boolean isJavaFile() {
        try {
            return getFileIndex().isJavaSourceFile(getFile());
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean isExpectedJUnitTestCase() {
        VirtualFile file;
        try {
            file = getFile();
        } catch (FileNotFoundException e) {
            return false;
        }
        if (!file.getNameWithoutExtension().matches(config.expectedTestCaseNameRegex())) {
            return false;
        }
        PsiClass psiClass = getPsiClass(file);
        return isExpectedJUnitTestCase(psiClass);
    }

    public boolean isExpectedJUnitTestCase(PsiClass psiClass) {
        if (!psiClass.getQualifiedName().matches(config.expectedTestCaseNameRegex())) {
            return false;
        }

        return isTest(psiClass);
    }

    private boolean isTest(PsiClass psiClass) {
        PsiMethod[] methods = psiClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (hasAnnotationOfOrgJunitTest(methods[i])) {
                return true;
            }
        }

        return psiClass.isInheritor(getTestCasePsiClass(), true);
    }

    private boolean hasAnnotationOfOrgJunitTest(PsiMethod method) {
        return AnnotationUtil.isAnnotated(method, org.junit.Test.class.getName(), true);
    }

    private PsiClass getTestCasePsiClass() {
        String packageName = TestCase.class.getPackage().getName();
        return getTestCasePsiClassFromPackage(getPsiManager().findPackage(packageName));
    }

    public Sunshine getSunshine() {
        final VirtualFile file;
        try {
            file = getFile();
        } catch (FileNotFoundException e) {
            return null;
        }
        return new Sunshine() {
            public Test shine(String testClassName) {
                return project.getSunshine(file).shine(testClassName);
            }
        };
    }

    public String getJavaFileClassName() {
        VirtualFile file;
        try {
            file = getFile();
        } catch (FileNotFoundException e) {
            return null;
        }
        String packageName = getFileIndex().getPackageNameByDirectory(file.getParent());
        String className = file.getNameWithoutExtension();
        if (Utils.isEmpty(packageName)) {
            return className;
        }
        return packageName + "." + className;
    }

    public PsiElement getPsiClass() throws FileNotFoundException {
        return getPsiClass(getFile());
    }

    public boolean isInSourceOrTestContent() {
        VirtualFile file;
        try {
            file = getFile();
        } catch (FileNotFoundException e) {
            return false;
        }
        return getFileIndex().isInSourceContent(file) || getFileIndex().isInTestSourceContent(file);
    }

    private PsiManager getPsiManager() {
        return project.getPsiManager();
    }

    private PsiClass getTestCasePsiClassFromPackage(PsiPackage testCasePackage) {
        if (testCasePackage != null) {
            for (final PsiClass clazz : testCasePackage.getClasses()) {
                if (TestCase.class.getName().equals(clazz.getQualifiedName())) {
                    return clazz;
                }
            }
        }
        String errorMsg = "Can't find junit class \"" + TestCase.class + "\".";
        throw new IllegalStateException(errorMsg);
    }

    private PsiClass getPsiClass(VirtualFile file) {
        final PsiClass[] classes = getPsiJavaFile(file).getClasses();
        final String fileName = file.getNameWithoutExtension();
        for (final PsiClass aClass : classes) {
            if (fileName.equals(aClass.getName())) {
                return aClass;
            }
        }
        String errorMsg = "VirtualFile is not a Java file or has no public class: " + file.getPresentableUrl();
        throw new IllegalStateException(errorMsg);
    }

    private PsiJavaFile getPsiJavaFile(VirtualFile file) {
        return (PsiJavaFile) getPsiManager().findFile(file);
    }

    private ProjectFileIndex getFileIndex() {
        return project.getFileIndex();
    }

    private VirtualFile getFile() throws FileNotFoundException {
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file == null) {
            throw new FileNotFoundException();
        }
        return file;
    }
}
