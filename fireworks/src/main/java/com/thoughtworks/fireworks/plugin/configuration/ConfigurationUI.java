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
package com.thoughtworks.fireworks.plugin.configuration;

import com.thoughtworks.fireworks.mail.FeedbacksActionListener;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Label;
import java.text.MessageFormat;

public class ConfigurationUI {
    private JCheckBox enable;
    private JCheckBox enableAutoTask;
    private JTextField maxMemory;
    private JTextField expectedTestCaseNameRegex;
    private JTextField maxSize;
    private JTextField autoRunTestsDelayTime;
    private JTextField jvmArgs;
    private JCheckBox clearLogConsole;

    private JPanel pane;

    public ConfigurationUI() {
        enable = new JCheckBox();
        enableAutoTask = new JCheckBox();
        maxMemory = new JTextField();
        expectedTestCaseNameRegex = new JTextField();
        maxSize = new JTextField();
        autoRunTestsDelayTime = new JTextField();
        jvmArgs = new JTextField();
        clearLogConsole = new JCheckBox();

        pane = new JPanel(new GridLayout(9, 2));
        pane.add(new Label("Enable fireworks: "));
        pane.add(enable);
        pane.add(new Label("Enable auto run task: "));
        pane.add(enableAutoTask);
        pane.add(new Label("Run tests max memory: "));
        pane.add(maxMemory);
        pane.add(new Label("Test class name regex(jdk1.4): "));
        pane.add(expectedTestCaseNameRegex);
        pane.add(new Label("Max size of recent test list: "));
        pane.add(maxSize);
        pane.add(new Label("Delay time(millisecond): "));
        pane.add(autoRunTestsDelayTime);
        pane.add(new Label("JVM arguments for the tests: "));
        pane.add(jvmArgs);
        pane.add(new Label("Clear log console: "));
        pane.add(clearLogConsole);

        pane.add(new Label("Just ten seconds, please help me, "));
        JButton feedbacks = new JButton("click me to response feedbacks.");
        feedbacks.addActionListener(new FeedbacksActionListener());
        pane.add(feedbacks);
    }

    public JComponent getRootComponent() {
        return pane;
    }

    public boolean isEnable() {
        return enable.isSelected();
    }

    public String maxMemory() {
        return maxMemory.getText();
    }

    public String expectedTestCaseNameRegex() {
        return expectedTestCaseNameRegex.getText();
    }

    public void setEnable(boolean enable) {
        this.enable.setSelected(enable);
    }

    public void setExpectedTestCaseNameRegex(String expectedTestCaseNameRegex) {
        this.expectedTestCaseNameRegex.setText(expectedTestCaseNameRegex);
    }

    public void setMaxMemory(String maxMemory) {
        this.maxMemory.setText(maxMemory);
    }

    public int maxSize() {
        return toInt(this.maxSize.getText());
    }

    public void setMaxSize(int maxSize) {
        this.maxSize.setText(String.valueOf(maxSize));
    }

    public int autoRunTestsDelayTime() {
        return toInt(this.autoRunTestsDelayTime.getText());
    }

    public void setAutoRunTestsDelayTime(int autoRunTestsDelayTime) {
        this.autoRunTestsDelayTime.setText(String.valueOf(autoRunTestsDelayTime));
    }

    private int toInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            String message = MessageFormat.format("Should type in number as max size, but you type in \"{0}\".", new Object[]{text});
            JOptionPane.showMessageDialog(null, message);
            return 0;
        }
    }

    public String jvmArgs() {
        return jvmArgs.getText();
    }

    public void setJvmArgs(String jvmArgs) {
        this.jvmArgs.setText(jvmArgs);
    }

    public boolean isAutoTaskEnabled() {
        return enableAutoTask.isSelected();
    }

    public void setAutoTaskEnabled(boolean enableAutoTask) {
        this.enableAutoTask.setSelected(enableAutoTask);
    }

    public boolean getClearLogConsole() {
        return clearLogConsole.isSelected();
    }

    public void setClearLogConsole(boolean clearLogConsole) {
        this.clearLogConsole.setSelected(clearLogConsole);
    }
}
