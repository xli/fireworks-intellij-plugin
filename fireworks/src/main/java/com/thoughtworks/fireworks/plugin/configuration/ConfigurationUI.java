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

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class ConfigurationUI {
    private Checkbox enable;
    private JTextField maxMemory;
    private JTextField expectedTestCaseNameRegex;
    private JTextField maxSize;
    private JTextField autoRunTestsDelayTime;
    private JTextField jvmArgs;

    private JPanel pane;

    public ConfigurationUI() {
        enable = new Checkbox();
        maxMemory = new JTextField();
        expectedTestCaseNameRegex = new JTextField();
        maxSize = new JTextField();
        autoRunTestsDelayTime = new JTextField();
        jvmArgs = new JTextField();

        pane = new JPanel(new GridLayout(6, 2));
        pane.add(new Label("Enable fireworks: "));
        pane.add(enable);
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
    }

    public JComponent getRootComponent() {
        return pane;
    }

    public boolean isEnable() {
        return enable.getState();
    }

    public String maxMemory() {
        return maxMemory.getText();
    }

    public String expectedTestCaseNameRegex() {
        return expectedTestCaseNameRegex.getText();
    }

    public void setEnable(boolean enable) {
        this.enable.setState(enable);
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
}
