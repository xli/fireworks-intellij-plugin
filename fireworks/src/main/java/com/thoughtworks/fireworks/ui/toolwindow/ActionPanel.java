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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel implements TestResultSummaryBgColorListener {
    private static final String RUN_TEST_LIST_BUTTON_TEXT = "Run(Alt-Shift-K)";
    private JButton runTestListButton;
    private JButton runAllTestsButton;
    private static final String RUN_ALL_TESTS_BUTTON_TEXT = "Run all tests(Alt-Shift-L)";

    public ActionPanel(TestResultSummary resultSummary) {
        super(new FlowLayout(FlowLayout.LEADING, 4, 3));
        runTestListButton = new FancyButton(Icons.runTestListButton(), Icons.runTestListButtonF());
        runTestListButton.setToolTipText(RUN_TEST_LIST_BUTTON_TEXT);

        runAllTestsButton = new FancyButton(Icons.runAllTestsButton(), Icons.runAllTestsButtonF());
        runAllTestsButton.setToolTipText(RUN_ALL_TESTS_BUTTON_TEXT);

        add(runTestListButton);
        add(runAllTestsButton);
        add(resultSummary);
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
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
