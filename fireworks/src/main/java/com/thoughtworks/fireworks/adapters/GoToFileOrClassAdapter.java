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

import java.awt.*;

public class GoToFileOrClassAdapter implements Thought {
    private static final String GO_TO_FILE_OR_CLASS_UI_CLASS_NAME = "com.intellij.ide.util.gotoByName.ChooseByNameBase";
    private final ProjectAdapter project;

    public GoToFileOrClassAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public boolean isWorking() {
        Window main = project.getSuggesttedParentWindow();
        if (main == null) {
            return false;
        }
        Component focusOwner = main.getFocusOwner();
        if (focusOwner == null) {
            return false;
        }
        String focusOwnerClassName = focusOwner.getClass().getName();
        return focusOwnerClassName.startsWith(GO_TO_FILE_OR_CLASS_UI_CLASS_NAME);
    }
}
