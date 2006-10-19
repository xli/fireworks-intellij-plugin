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

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;

public class PsiPackageAdapter {
    private final PsiPackage psiPackage;

    public PsiPackageAdapter(PsiPackage psiPackage) {
        this.psiPackage = psiPackage;
    }

    public PsiClass getPsiClass(Class<?> aClass) {
        if (psiPackage != null) {
            for (final PsiClass clazz : psiPackage.getClasses()) {
                if (aClass.getName().equals(clazz.getQualifiedName())) {
                    return clazz;
                }
            }
        }
        throw new IllegalStateException("Can't find class \"" + aClass + "\".");
    }
}
