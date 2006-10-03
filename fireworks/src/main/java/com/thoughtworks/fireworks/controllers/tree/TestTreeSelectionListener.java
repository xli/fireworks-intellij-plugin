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
package com.thoughtworks.fireworks.controllers.tree;

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class TestTreeSelectionListener implements TreeSelectionListener {
    private final Log log;
    private final ConsoleViewAdaptee consoleView;

    public TestTreeSelectionListener(Log log, ConsoleViewAdaptee consoleView) {
        this.log = log;
        this.consoleView = consoleView;
    }

    public void valueChanged(TreeSelectionEvent e) {
        log.output(consoleView);
    }
}
