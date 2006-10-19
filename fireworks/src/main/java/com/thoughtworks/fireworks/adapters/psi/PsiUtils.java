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
package com.thoughtworks.fireworks.adapters.psi;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import junit.framework.TestCase;

public class PsiUtils {
    public static PsiMethod findMethod(PsiClass aClass, String testMethodName) {
        if (testMethodName != null) {
            PsiMethod[] methods = aClass.getMethods();
            for (PsiMethod method : methods) {
                if (method.getName().equals(testMethodName)) {
                    return method;
                }
            }
        }
        return null;
    }

    public static PsiClass getTestCasePsiClass(ProjectAdapter project) {
        String packageName = TestCase.class.getPackage().getName();
        return project.getPackage(packageName).getPsiClass(TestCase.class);
    }

    public static PsiClass getPsiClass(ProjectAdapter project, VirtualFile file) {
        final PsiClass[] classes = getPsiJavaFile(project, file).getClasses();
        final String fileName = file.getNameWithoutExtension();
        for (final PsiClass aClass : classes) {
            if (fileName.equals(aClass.getName())) {
                return aClass;
            }
        }
        return null;
    }

    private static PsiJavaFile getPsiJavaFile(ProjectAdapter project, VirtualFile file) {
        return (PsiJavaFile) project.getPsiManager().findFile(file);
    }

}
