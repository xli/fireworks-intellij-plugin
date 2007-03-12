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

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.thoughtworks.fireworks.controllers.CabinetController;
import com.thoughtworks.fireworks.core.TestShadowMap;

public class FireworksProject extends FireworksConfigurationImpl implements ProjectComponent {

    private Project project;
    private FireworksContainer container;

    private boolean projectIsOpened = false;

    public FireworksProject(Project project) {
        this.project = project;
    }

    public void initComponent() {
        container = new FireworksContainer(this, project);
    }

    public void disposeComponent() {
        container.disposeComponent();
        container = null;
    }

    public String getComponentName() {
        return "FireworksProject";
    }

    public void projectOpened() {
        projectIsOpened = true;
        setMaxSize(maxSize);
        resetEnableFireworks();
        fireAutoRunTestConfigurationListener();
    }

    public void projectClosed() {
        container.stop();
        projectIsOpened = false;
        clearConfigurationListeners();
    }

    public void setMaxSize(int maxSize) {
        super.setMaxSize(maxSize);
        getTestShadowMap().setMaxSize(maxSize);
    }

    protected void resetEnableFireworks() {
        if (!projectIsOpened) {
            return;
        }
        if (isEnabled()) {
            container.start();
        } else {
            container.stop();
        }
    }

    public CabinetController getCabinetController() {
        return container.getInstance(CabinetController.class);
    }

    public TestShadowMap getTestShadowMap() {
        return container.getInstance(TestShadowMap.class);
    }

}
