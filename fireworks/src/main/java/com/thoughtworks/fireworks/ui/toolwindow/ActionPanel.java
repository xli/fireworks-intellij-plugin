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

import com.thoughtworks.fireworks.controllers.Icons;
import com.thoughtworks.fireworks.controllers.TestResultSummaryBgColorListener;
import com.thoughtworks.fireworks.core.AutoRunTestConfigurationListener;
import com.thoughtworks.fireworks.core.FireworksConfig;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel implements TestResultSummaryBgColorListener, AutoRunTestConfigurationListener {
    private static final String RUN_TEST_LIST_BUTTON_TEXT = "Run(Alt-Shift-K)";
    private static final String RUN_ALL_TESTS_BUTTON_TEXT = "Run all tests(Alt-Shift-L)";
    private static final String AUTO_RUN_TEST_BUTTON_TEXT = "Enable/disable autorun tests(Alt-Shift-A)";

    private JButton runTestListButton;
    private JButton runAllTestsButton;
    private JCheckBox autorunTestsCheckBox;
    private FireworksConfig config;

    public ActionPanel(TestResultSummary resultSummary, final FireworksConfig config) {
        super(new FlowLayout(FlowLayout.LEADING, 4, 3));
        this.config = config;
        runTestListButton = new FancyButton(Icons.runTestListButton(), Icons.runTestListButtonF());
        runTestListButton.setToolTipText(RUN_TEST_LIST_BUTTON_TEXT);

        runAllTestsButton = new FancyButton(Icons.runAllTestsButton(), Icons.runAllTestsButtonF());
        runAllTestsButton.setToolTipText(RUN_ALL_TESTS_BUTTON_TEXT);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                config.switchAutoRunTestsConfiguration();
            }
        };
        autorunTestsCheckBox = new FancyCheckBox(Icons.disableAutorunTestsButton(), AUTO_RUN_TEST_BUTTON_TEXT,
                Icons.enableAutorunTestsButton(), config.isAutoRunTestsEnabled(), listener);

        config.addAutoRunTestConfigurationListener(this);
        add(runTestListButton);
        add(runAllTestsButton);
        add(resultSummary);
        add(autorunTestsCheckBox);
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    public void change() {
        autorunTestsCheckBox.setSelected(config.isAutoRunTestsEnabled());
    }

    public void removeAllActionListeners() {
        removeAllActionListeners(runTestListButton);
        removeAllActionListeners(runAllTestsButton);
    }

    private void removeAllActionListeners(JButton button) {
        ActionListener[] actionListeners = button.getActionListeners();
        for (int i = 0; i < actionListeners.length; i++) {
            button.removeActionListener(actionListeners[i]);
        }
    }

    public void addRunTestListActionListener(ActionListener listener) {
        runTestListButton.addActionListener(listener);
    }

    public void addRunAllTestsActionListener(ActionListener listener) {
        runAllTestsButton.addActionListener(listener);
    }
}
