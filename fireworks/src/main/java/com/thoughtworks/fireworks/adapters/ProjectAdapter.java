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

import com.intellij.execution.filters.OpenFileHyperlinkInfo;
import com.intellij.execution.filters.TextConsoleBuidlerFactory;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.refactoring.listeners.RefactoringListenerManager;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.ant.AntSunshine;
import org.apache.tools.ant.BuildListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter {
    private final List buildListeners = new ArrayList();

    private final Project project;
    private final TestsRunningProgressIndicatorAdapter progressIndicator;
    private final FireworksConfig config;
    private List<ConsoleViewAdapter> consoles = new ArrayList();

    public ProjectAdapter(Project project, FireworksConfig config, TestsRunningProgressIndicatorAdapter progressIndicator) {
        this.config = config;
        this.project = project;
        this.progressIndicator = progressIndicator;
    }

    public void registerToolWindow(String id, String title, JComponent component, Icon icon) {
        ToolWindow toolWindow = getToolWindowManager().registerToolWindow(id, component, ToolWindowAnchor.BOTTOM);
        toolWindow.setTitle(title);
        toolWindow.setIcon(icon);
    }

    private ToolWindowManager getToolWindowManager() {
        return ToolWindowManager.getInstance(project);
    }

    public void unregisterToolWindow(String id) {
        getToolWindowManager().unregisterToolWindow(id);
    }

    public ConsoleViewAdaptee createTextConsoleBuilder() {
        ConsoleViewAdapter consoleViewAdapter = new ConsoleViewAdapter(TextConsoleBuidlerFactory.getInstance().createBuilder(project).getConsole());
        consoles.add(consoleViewAdapter);
        return consoleViewAdapter;
    }

    public void setStatusBarInfo(String info) {
        WindowManager.getInstance().getStatusBar(project).setInfo(info);
    }

    public void setToolWindowIcon(final String id, final Icon icon) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                getToolWindowManager().getToolWindow(id).setIcon(icon);
            }
        });
    }

    public void jumpToSource(String testClassName, String testMethodName) {
        PsiClass aClass = findClass(testClassName);
        if (aClass == null) {
            return;
        }
        aClass = (PsiClass) aClass.getNavigationElement();
        if (aClass == null) {
            return;
        }
        PsiFile file = aClass.getContainingFile();
        PsiMethod method = findMethod(aClass, testMethodName);

        int lineNum = (method == null) ? getDeclareClassLineNum(file) : getDeclareMethodLineNum(file, method);

        openFileInfo(file, lineNum).navigate(project);
    }

    private int getDeclareMethodLineNum(PsiFile file, PsiMethod method) {
        int startOffset = method.getTextRange().getStartOffset();
        String textBefore = file.getText().substring(0, startOffset);
        return getLineSizeBefore(textBefore);
    }

    private PsiClass findClass(String testClassName) {
        return getPsiManager().findClass(testClassName, GlobalSearchScope.allScope(project));
    }

    PsiManager getPsiManager() {
        return PsiManager.getInstance(project);
    }

    private OpenFileHyperlinkInfo openFileInfo(PsiFile file, int lineNum) {
        return new OpenFileHyperlinkInfo(project, file.getVirtualFile(), lineNum);
    }

    private int getDeclareClassLineNum(PsiFile file) {
        String text = file.getText();
        return getLineSizeBefore(text.substring(0, text.indexOf("class ")));
    }

    private int getLineSizeBefore(String text) {
        return text.split("\n").length;
    }

    private PsiMethod findMethod(PsiClass aClass, String testMethodName) {
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

    public DocumentAdaptee createDocumentAdaptee(Document document) {
        return new DocumentAdapter(document, this, config);
    }

    public DocumentAdaptee createDocumentAdaptee(PsiFile psiFile) {
        return createDocumentAdaptee(getPsiDocumentManager().getDocument(psiFile));
    }

    RefactoringListenerManager getRefactoringListenerManager() {
        return RefactoringListenerManager.getInstance(project);
    }

    ProjectFileIndex getFileIndex() {
        return ProjectRootManager.getInstance(project).getFileIndex();
    }

    PsiDocumentManager getPsiDocumentManager() {
        return PsiDocumentManager.getInstance(project);
    }

    public SearchScope getProjectTestJavaFileScope() {
        return GlobalSearchScope.projectTestScope(project, false);
    }

    public void addBuildListener(BuildListener listener) {
        this.buildListeners.add(listener);
    }

    Sunshine getSunshine(VirtualFile file) {
        Module moduleForFile = getFileIndex().getModuleForFile(file);
        AntSunshine sunshine = new ModuleAdapter(moduleForFile, config).antSunshine();

        addBuildListeners(sunshine);

        return progressIndicator.decorate(sunshine);
    }

    private void addBuildListeners(AntSunshine sunshine) {
        for (int i = 0; i < buildListeners.size(); i++) {
            sunshine.addBuildListener((BuildListener) buildListeners.get(i));
        }
    }

    public void runProcessWithProgressSynchronously(Runnable process, String title, boolean canBeCanceled) {
        ApplicationManager.getApplication().runProcessWithProgressSynchronously(process, title, canBeCanceled, project);
    }

    public void make(CompileStatusNotification compileStatusNotification) {
        CompilerManager.getInstance(project).make(compileStatusNotification);
    }

    public void dispose() {
        for (int i = 0; i < consoles.size(); i++) {
            ConsoleViewAdapter consoleViewAdapter = consoles.get(i);
            consoleViewAdapter.dispose();
        }
    }
}
