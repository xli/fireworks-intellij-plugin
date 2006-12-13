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

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.shadow.Utils;

import javax.swing.JComponent;

public class ConsoleViewAdapter implements ConsoleViewAdaptee {
    private final ConsoleView console;
    private JComponent component;

    public ConsoleViewAdapter(ConsoleView console) {
        this.console = console;
        this.component = console.getComponent();
    }

    public JComponent getComponent() {
        return component;
    }

    public void clearAndPrint(String text) {
        clear();
        printButFilterEmptyAndNull(text, ConsoleViewContentType.NORMAL_OUTPUT);
    }

    private void printButFilterEmptyAndNull(String text, ConsoleViewContentType type) {
        if (!Utils.isEmpty(text)) {
            console.print(text, type);
        }
    }

    public void dispose() {
        console.dispose();
    }

    public void clear() {
        console.clear();
    }

    public void printlnSystemOutput(String message) {
        printButFilterEmptyAndNull(message, ConsoleViewContentType.SYSTEM_OUTPUT);
        println(ConsoleViewContentType.SYSTEM_OUTPUT);
    }

    public void printlnErrorOutput(String message) {
        printButFilterEmptyAndNull(message, ConsoleViewContentType.ERROR_OUTPUT);
        println(ConsoleViewContentType.ERROR_OUTPUT);
    }

    private void println(ConsoleViewContentType type) {
        printButFilterEmptyAndNull(Utils.LINE_SEP, type);
    }

}
