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
package com.thoughtworks.fireworks.ui.table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClearTableSelectionListener extends WindowAdapter {
    private final TestShadowResultTable table;

    public ClearTableSelectionListener(TestShadowResultTable table) {
        this.table = table;
    }

    public void windowClosed(WindowEvent e) {
        table.clearSelection();
    }

    public void windowLostFocus(WindowEvent e) {
        e.getWindow().dispose();
    }

}
