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
package com.thoughtworks.fireworks.controllers;

import com.intellij.psi.PsiClass;
import com.thoughtworks.shadow.Sunshine;

import java.io.FileNotFoundException;

public interface DocumentAdaptee {
    boolean isWritable();

    boolean isJavaFile();

    boolean isExpectedJUnitTestCase();

    boolean isInSourceOrTestContent();

    Sunshine getSunshine();

    String getJavaFileClassName();

    PsiClass getPsiClass() throws FileNotFoundException;

    boolean hasErrors();

    boolean isNotXml();

    boolean isNotDtd();
}
