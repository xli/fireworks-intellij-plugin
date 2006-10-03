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
package com.thoughtworks.fireworks.ui.toolwindow;

import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.core.tree.TestStatusSummaryListener;

import javax.swing.*;
import java.awt.*;

public class ToolWindow implements TestStatusSummaryListener {
    private static final String TOOL_WINDOW_ID = "Fireworks";
    private static final String TOOL_WINDOW_TITLE = "Don't make me test.";
    private final ProjectAdapter project;
    private final JPanel panel;
    private Icon status;
    private boolean registered;

    public ToolWindow(ProjectAdapter project, ActionPanel actionPanel, DetailPanel detailPanel) {
        this.project = project;
        panel = new JPanel(new BorderLayout());
        panel.add(detailPanel.getComponent(), BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.NORTH);
    }

    public void summaryStatusChanged(Icon status) {
        this.status = status;
        if (registered) {
            project.setToolWindowIcon(TOOL_WINDOW_ID, status);
        }
    }

    public void statusChanged(Object key, Icon status) {
    }

    public void register() {
        project.registerToolWindow(TOOL_WINDOW_ID, TOOL_WINDOW_TITLE, panel, status);
        registered = true;
    }

    public void unregister() {
        registered = false;
        project.unregisterToolWindow(TOOL_WINDOW_ID);
    }
}
