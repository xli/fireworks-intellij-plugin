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
package com.thoughtworks.fireworks.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.project.Project;

public class FireworksRunTestListAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        fireworksProject(e).getCabinetController().fireRunTestListActionEvent();
    }

    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(fireworksProject(e).isEnable());
    }

    private FireworksProject fireworksProject(AnActionEvent e) {
        return getProject(e).getComponent(FireworksProject.class);
    }

    private Project getProject(AnActionEvent e) {
        return (Project) e.getDataContext().getData(DataConstants.PROJECT);
    }
}
