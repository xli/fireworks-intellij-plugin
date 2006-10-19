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

import com.intellij.execution.filters.OpenFileHyperlinkInfo;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;

public class NavigationElementAdapter {
    private final PsiElement navigationElement;

    public NavigationElementAdapter(PsiElement navigationElement) {
        this.navigationElement = navigationElement;
    }

    public void navigate(PsiMethod method) {
        if (navigationElement == null) {
            return;
        }
        PsiFile file = navigationElement.getContainingFile();
        openFileInfo(file, lineNum(method, file)).navigate(getProject());
    }

    private int lineNum(PsiMethod method, PsiFile file) {
        return (method == null) ? getDeclareClassLineNum(file) : getDeclareMethodLineNum(file, method);
    }

    private Project getProject() {
        return navigationElement.getProject();
    }

    private int getDeclareMethodLineNum(PsiFile file, PsiMethod method) {
        int startOffset = method.getTextRange().getStartOffset();
        String textBefore = file.getText().substring(0, startOffset);
        return getLineSizeBefore(textBefore);
    }

    private OpenFileHyperlinkInfo openFileInfo(PsiFile file, int lineNum) {
        return new OpenFileHyperlinkInfo(getProject(), file.getVirtualFile(), lineNum);
    }

    private int getDeclareClassLineNum(PsiFile file) {
        String text = file.getText();
        return getLineSizeBefore(text.substring(0, text.indexOf("class ")));
    }

    private int getLineSizeBefore(String text) {
        return text.split("\n").length;
    }
}
