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

import com.thoughtworks.fireworks.core.developer.Thought;

import javax.swing.*;
import java.awt.*;

public class CodeCompletionAdapter implements Thought {
    private static final String CODE_COMPLETION_LIST_CELL_RENDERER = "com.intellij.codeInsight.lookup.impl.LookupCellRenderer";

    private final ProjectAdapter project;

    public CodeCompletionAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public boolean isWorking() {
        return checkChildren(project.getSuggesttedParentWindow());
    }

    private boolean checkChildren(Container container) {
        if (container == null) {
            return false;
        }
        if (container instanceof JList) {
            return checkJList((JList) container);
        }
        Component[] components = container.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (!(component instanceof Container)) {
                continue;
            }
            if (checkChildren((Container) component)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkJList(JList list) {
        return CODE_COMPLETION_LIST_CELL_RENDERER.equals(list.getCellRenderer().getClass().getName());
    }
}
