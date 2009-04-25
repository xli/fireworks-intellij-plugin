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
import com.thoughtworks.fireworks.core.ApplicationAdaptee;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TestLogPanel extends JPanel implements BuildListener, ShadowCabinetListener {
    private ConsoleViewAdaptee console;
    private long time;
    private FireworksConfig config;
    private ApplicationAdaptee app;

    public TestLogPanel(ProjectAdapter projectAdapter, FireworksConfig config, ApplicationAdaptee app) {
        super(new BorderLayout());
        this.config = config;
        this.app = app;
        console = projectAdapter.createTextConsoleBuilder();
        add(console.getComponent(), BorderLayout.CENTER);
        projectAdapter.addBuildListener(this);
    }

    public void buildStarted(BuildEvent event) {
    }

    public void buildFinished(BuildEvent event) {
    }

    public void targetStarted(BuildEvent event) {
    }

    public void targetFinished(BuildEvent event) {
    }

    public void taskStarted(BuildEvent event) {
    }

    public void taskFinished(BuildEvent event) {
    }

    public void messageLogged(BuildEvent event) {
        if (event.getPriority() == Project.MSG_DEBUG) {
            return;
        }
        if (event.getPriority() > Project.MSG_INFO) {
            console.printlnSystemOutput(message(event));
        } else {
            console.printlnErrorOutput(message(event));
        }
        if (event.getException() != null) {
            console.printlnErrorOutput("==================unexpected exception========================");
            console.printlnErrorOutput(Utils.toString(event.getException()));
            console.printlnErrorOutput(null);
        }
    }

    private String message(BuildEvent event) {
        return getMessagePrefix(event.getPriority()) + " " + event.getMessage();
    }

    private String getMessagePrefix(int priority) {
        switch (priority) {
            case Project.MSG_DEBUG:
                return "[debug]";
            case Project.MSG_VERBOSE:
                return "[verbose]";
            case Project.MSG_INFO:
                return "[info]";
            case Project.MSG_WARN:
                return "[warn]";
            case Project.MSG_ERR:
                return "[err]";
        }
        return "[unexpected]";
    }

    public void afterAddTest(ComparableTestShadow test) {
    }

    public void afterRemoveTest(ComparableTestShadow test) {
    }

    public void endAction() {
        console.printlnSystemOutput("***************************************************");
        console.printlnSystemOutput("* end action at: " + new Date(System.currentTimeMillis()));
        console.printlnSystemOutput("* time: " + (System.currentTimeMillis() - time) + " millisecond");
        console.printlnSystemOutput("***************************************************");
        console.printlnSystemOutput(null);
        console.printlnSystemOutput(null);
    }

    public void startAction() {
        time = System.currentTimeMillis();
        if (config.clearLogConsole()) {
            clearConsoleAndOutput();
        } else {
            outputStartActionLog();
        }
    }

    private void clearConsoleAndOutput() {
        app.invokeLater(new Runnable() {
            public void run() {
                console.clear();
                outputStartActionLog();
            }
        });
    }

    private void outputStartActionLog() {
        console.printlnSystemOutput("***************************************************");
        console.printlnSystemOutput("* start action at: " + new Date(time));
        console.printlnSystemOutput("***************************************************");
    }

}
