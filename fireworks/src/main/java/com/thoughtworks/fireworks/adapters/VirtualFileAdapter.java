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

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.thoughtworks.fireworks.adapters.psi.PsiUtils;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.Utils;
import junit.framework.Test;

import java.io.FileNotFoundException;

public class VirtualFileAdapter implements VirtualFileAdaptee {

    public static VirtualFileAdaptee getFile(Document document, ProjectAdapter project) throws FileNotFoundException {
        return getFile(FileDocumentManager.getInstance().getFile(document), project);
    }

    public static VirtualFileAdaptee getFile(PsiElement element, ProjectAdapter project) throws FileNotFoundException {
        return getFile(element.getContainingFile().getVirtualFile(), project);
    }

    public static VirtualFileAdaptee getFile(VirtualFile file, ProjectAdapter project) throws FileNotFoundException {
        if (file == null) {
            throw new FileNotFoundException();
        }
        return new VirtualFileAdapter(file, project);
    }

    private final VirtualFile file;
    private final ProjectAdapter project;

    private VirtualFileAdapter(VirtualFile file, ProjectAdapter project) {
        this.file = file;
        this.project = project;
    }

    public boolean isInSourceOrTestContent() {
        return isJavaSourceFile() || getFileIndex().isInTestSourceContent(file);
    }

    public PsiClass getPsiClass() {
        return PsiUtils.getPsiClass(project, file);
    }

    public String getJavaFileClassName() {
        String packageName = getFileIndex().getPackageNameByDirectory(file.getParent());
        String className = file.getNameWithoutExtension();
        if (Utils.isEmpty(packageName)) {
            return className;
        }
        return packageName + "." + className;
    }

    private ProjectFileIndex getFileIndex() {
        return project.getFileIndex();
    }

    public Sunshine createSunshine() {
        return new Sunshine() {
            public Test shine(String testClassName) {
                return project.getSunshine(file).shine(testClassName);
            }
        };
    }

    public boolean isJavaSourceFile() {
        return getFileIndex().isInSourceContent(file);
    }

    public String getNameWithoutExtension() {
        return file.getNameWithoutExtension();
    }
}
