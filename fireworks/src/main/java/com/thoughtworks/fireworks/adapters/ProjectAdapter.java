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

import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.compiler.CompilerWorkspaceConfiguration;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.AllClassesSearch;
import com.intellij.refactoring.listeners.RefactoringListenerManager;
import com.intellij.util.Query;
import com.thoughtworks.fireworks.adapters.compatibility.CompileStatusNotificationAdapter;
import com.thoughtworks.fireworks.adapters.document.MarkupAdapter;
import com.thoughtworks.fireworks.adapters.psi.PsiClassAdapter;
import com.thoughtworks.fireworks.adapters.psi.PsiPackageAdapter;
import com.thoughtworks.fireworks.controllers.DocumentAdaptee;
import com.thoughtworks.fireworks.core.CompileStatusNotificationAdaptee;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.ant.AntSunshine;
import org.apache.tools.ant.BuildListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter {
    private final List<BuildListener> buildListeners = new ArrayList<BuildListener>();

    private final Project project;
    private final FireworksConfig config;
    private List<ConsoleViewAdapter> consoles = new ArrayList<ConsoleViewAdapter>();

    public ProjectAdapter(Project project, FireworksConfig config) {
        this.config = config;
        this.project = project;
    }

    public void registerToolWindow(String id, String title, JComponent component, Icon icon) {
        ToolWindow toolWindow = getToolWindowManager().registerToolWindow(id, component, ToolWindowAnchor.BOTTOM);
        toolWindow.setTitle(title);
        toolWindow.setIcon(icon);
    }

    public void unregisterToolWindow(String id) {
        getToolWindowManager().unregisterToolWindow(id);
    }

    public ConsoleViewAdaptee createTextConsoleBuilder() {
        ConsoleView view = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        ConsoleViewAdapter consoleViewAdapter = new ConsoleViewAdapter(view);
        consoles.add(consoleViewAdapter);
        return consoleViewAdapter;
    }

    public void setStatusBarInfo(String info) {
        WindowManager.getInstance().getStatusBar(project).setInfo(info);
    }

    public void setToolWindowIcon(final String id, final Icon icon) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ToolWindowManager twm = getToolWindowManager();
                if (twm == null) {
                    return;
                }
                ToolWindow window = twm.getToolWindow(id);
                if (window == null) {
                    return;
                }
                window.setIcon(icon);
            }
        });
    }

    public void jumpToSource(String testClassName, String testMethodName) {
        findClass(testClassName).jumpToMethod(testMethodName);
    }

    public PsiManager getPsiManager() {
        return PsiManager.getInstance(project);
    }

    public DocumentAdaptee createDocumentAdaptee(Document document) {
        return new DocumentAdapter(document, this);
    }

    public PsiDocumentManager getPsiDocumentManager() {
        return PsiDocumentManager.getInstance(project);
    }

    public SearchScope getProjectTestJavaFileScope() {
        return GlobalSearchScope.projectTestScope(project);
    }

    public void addBuildListener(BuildListener listener) {
        this.buildListeners.add(listener);
    }

    public void make(final CompileStatusNotificationAdaptee compileStatusNotification) {
        final boolean autoShowErrorsInEditor = CompilerWorkspaceConfiguration.getInstance(project).AUTO_SHOW_ERRORS_IN_EDITOR;
        final boolean compileInBackground = CompilerWorkspaceConfiguration.getInstance(project).COMPILE_IN_BACKGROUND;
        CompilerWorkspaceConfiguration.getInstance(project).AUTO_SHOW_ERRORS_IN_EDITOR = config.autoShowErrorsInEditorAfterCompile();
        CompilerWorkspaceConfiguration.getInstance(project).COMPILE_IN_BACKGROUND = true;
        CompileStatusNotificationAdapter compileStatusNotificationAdapter = new CompileStatusNotificationAdapter(new CompileStatusNotificationAdaptee() {
            public void finished(boolean aborted, int errors, int warnings) {
                compileStatusNotification.finished(aborted, errors, warnings);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        CompilerWorkspaceConfiguration.getInstance(project).AUTO_SHOW_ERRORS_IN_EDITOR = autoShowErrorsInEditor;
                        CompilerWorkspaceConfiguration.getInstance(project).COMPILE_IN_BACKGROUND = compileInBackground;
                    }
                });
                t.start();
            }
        });
        CompilerManager.getInstance(project).make(compileStatusNotificationAdapter);
    }

    public void dispose() {
        for (int i = 0; i < consoles.size(); i++) {
            ConsoleViewAdapter consoleViewAdapter = consoles.get(i);
            consoleViewAdapter.dispose();
        }
    }

    public Window getSuggesttedParentWindow() {
        return WindowManager.getInstance().suggestParentWindow(project);
    }

    public MarkupAdapter createMarkupAdapter(Document document) {
        return new MarkupAdapter(document, project);
    }

    public ExecutionManager getExecutionManager() {
        return ExecutionManager.getInstance(project);
    }

    public PsiPackageAdapter getPackage(String packageName) {
        PsiPackage aPackage = psiFacade().findPackage(packageName);
        return new PsiPackageAdapter(aPackage);
    }

    public boolean matchesExpectedTestCaseNameRegex(String name) {
        if (name == null) {
            return false;
        }
        return name.matches(config.expectedTestCaseNameRegex());
    }

    RefactoringListenerManager getRefactoringListenerManager() {
        return RefactoringListenerManager.getInstance(project);
    }

    ProjectFileIndex getFileIndex() {
        return ProjectRootManager.getInstance(project).getFileIndex();
    }

    Sunshine getSunshine(VirtualFile file) {
        Module moduleForFile = getFileIndex().getModuleForFile(file);
        AntSunshine sunshine = new ModuleAdapter(moduleForFile, config).antSunshine();
        addBuildListeners(sunshine);
        return sunshine;
    }

    private PsiClassAdapter findClass(String testClassName) {
        return new PsiClassAdapter(psiFacade().findClass(testClassName, projectAllScope()));
    }

    private GlobalSearchScope projectAllScope() {
        return GlobalSearchScope.allScope(project);
    }

    private ToolWindowManager getToolWindowManager() {
        return ToolWindowManager.getInstance(project);
    }

    private void addBuildListeners(AntSunshine sunshine) {
        for (int i = 0; i < buildListeners.size(); i++) {
            sunshine.addBuildListener(buildListeners.get(i));
        }
    }

    public JavaPsiFacade psiFacade() {
        return JavaPsiFacade.getInstance(this.project);
    }

    public Query searchAllCalsses() {
        return AllClassesSearch.search(this.projectAllScope(), project);
    }

    public FileEditorManager getFileEditorManager() {
        return FileEditorManager.getInstance(project);
    }

    public TemplateManager getTemplateManager() {
        return TemplateManager.getInstance(project);
    }
}
