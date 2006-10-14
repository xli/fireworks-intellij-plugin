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
package com.thoughtworks.fireworks.plugin;

import com.intellij.openapi.project.Project;
import com.thoughtworks.fireworks.adapters.*;
import com.thoughtworks.fireworks.adapters.document.AllEditorsOpenedAdapter;
import com.thoughtworks.fireworks.adapters.search.TestCaseSearcher;
import com.thoughtworks.fireworks.controllers.*;
import com.thoughtworks.fireworks.core.AllTestShadowCabinet;
import com.thoughtworks.fireworks.core.IntellijShadowCabinet;
import com.thoughtworks.fireworks.core.TestResultFactory;
import com.thoughtworks.fireworks.core.TestShadowMap;
import com.thoughtworks.fireworks.core.table.ShadowTableModel;
import com.thoughtworks.fireworks.core.timer.*;
import com.thoughtworks.fireworks.core.tree.ShadowSummaryTreeNode;
import com.thoughtworks.fireworks.core.tree.ShadowTreeModel;
import com.thoughtworks.fireworks.ui.table.DialogTraceLogViewer;
import com.thoughtworks.fireworks.ui.table.SeperateRowByColorTableCellRenderer;
import com.thoughtworks.fireworks.ui.table.TestShadowResultTable;
import com.thoughtworks.fireworks.ui.toolwindow.*;
import com.thoughtworks.fireworks.ui.tree.TestShadowListTree;
import com.thoughtworks.fireworks.ui.tree.TestShadowTreeRenderer;
import com.thoughtworks.fireworks.ui.tree.TreeDetailPanel;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.alternatives.CachingPicoContainer;

public class FireworksContainer {

    private boolean started;
    private MutablePicoContainer container;
    private ProjectAdapter projectAdapter;

    public FireworksContainer(FireworksProject fireworksProject, Project project) {
        container = new CachingPicoContainer();
        TestsRunningProgressIndicatorAdapter progressIndicator = new TestsRunningProgressIndicatorAdapter();
        projectAdapter = new ProjectAdapter(project, fireworksProject, progressIndicator);
        container.registerComponentInstance(progressIndicator);
        container.registerComponentInstance(fireworksProject);
        container.registerComponentInstance(projectAdapter);

        container.registerComponentImplementation(ApplicationAdapter.class);

        container.registerComponentImplementation(CompilerManagerAdapter.class);
        container.registerComponentImplementation(RefactoringTestShadowListenerProvider.class);

        container.registerComponentImplementation(CodeCompletionAdapter.class);
        container.registerComponentImplementation(AllEditorsOpenedAdapter.class);
        container.registerComponentImplementation(TaskRunnerFactory.class);
        container.registerComponentImplementation(TimerTaskManager.class);
        container.registerComponentImplementation(CabinetControllerActionTimer.class);
        container.registerComponentImplementation(ReschedulableTaskAdapter.class);
        container.registerComponentImplementation(TimerScheduler.class);
        container.registerComponentImplementation(ConfiguredTimer.class);

        container.registerComponentImplementation(AllTestShadowCabinet.class);
        container.registerComponentImplementation(TestCaseSearcher.class);
        container.registerComponentImplementation(RunAllTestsRunnerAdapter.class);

        container.registerComponentImplementation(TestCaseOpenedListener.class);
        container.registerComponentImplementation(EditorFactoryListenerAdapter.class);
        container.registerComponentImplementation(FileDocManagerListenerAdapter.class);
        container.registerComponentImplementation(DocumentListenerAdapter.class);
        container.registerComponentImplementation(DeleteTestShadowListener.class);
        container.registerComponentImplementation(Listeners.class);
        container.registerComponentImplementation(AutoRunTaskListeners.class);

        container.registerComponentImplementation(JumpToSourceAdapter.class);
        container.registerComponentImplementation(StatusBarAdapter.class);

        container.registerComponentImplementation(ShadowSummaryTreeNode.class);
        container.registerComponentImplementation(TestShadowsStatus.class);
        container.registerComponentImplementation(TestShadowTreeRenderer.class);
        container.registerComponentImplementation(ShadowTreeModel.class);
        container.registerComponentImplementation(TestShadowListTree.class);
        container.registerComponentImplementation(ShadowTableModel.class);
        container.registerComponentImplementation(TestShadowResultTable.class);
        container.registerComponentImplementation(DialogTraceLogViewer.class);
        container.registerComponentImplementation(SeperateRowByColorTableCellRenderer.class);

        container.registerComponentImplementation(TestLogPanel.class);
        container.registerComponentImplementation(TreeDetailPanel.class);
        container.registerComponentImplementation(TestResultSummary.class);
        container.registerComponentImplementation(ActionPanel.class);
        container.registerComponentImplementation(DetailPanel.class);
        container.registerComponentImplementation(ToolWindow.class);
        container.registerComponentImplementation(ShadowCabinetViewToolWindowImpl.class);
        container.registerComponentImplementation(TestResultSummaryBgColor.class);

        container.registerComponentImplementation(TestResultFactory.class);

        container.registerComponentImplementation(IntellijShadowCabinet.class);
        container.registerComponentImplementation(RecentTestListRunnerAdapter.class);
        container.registerComponentImplementation(ShadowCabinetController.class);
    }

    public void start() {
        if (!started) {
            started = true;
            container.start();
        }
    }

    public void stop() {
        if (started) {
            started = false;
            container.stop();
        }
    }

    public CabinetController getCabinetController() {
        return ((CabinetController) getInstance(CabinetController.class));
    }

    public TestShadowMap getTestShadowMap() {
        return (TestShadowMap) getInstance(TestShadowMap.class);
    }

    private Object getInstance(Class type) {
        return container.getComponentInstanceOfType(type);
    }

    public void disposeComponent() {
        projectAdapter.dispose();
    }

    public AutoRunTaskListeners getListeners() {
        return (AutoRunTaskListeners) getInstance(AutoRunTaskListeners.class);
    }
}
