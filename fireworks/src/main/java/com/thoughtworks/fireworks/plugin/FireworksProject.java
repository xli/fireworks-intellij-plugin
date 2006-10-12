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
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.thoughtworks.fireworks.controllers.CabinetController;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.fireworks.core.TestShadowMap;
import com.thoughtworks.shadow.Utils;

public class FireworksProject extends FireworksConfiguration implements ProjectComponent, FireworksConfig {

    private static final String DEFAULT_TEST_CLASS_NAME_REGEX = ".*Test";

    public String expectedTestCaseNameRegex = null;
    public String maxMemory = null;
    public int maxSize = 5;
    public boolean enable = true;
    public int autoRunTestsDelayTime = 4000;

    private Project project;
    private FireworksContainer container;
    private String jvmArgs;

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
        if (enable) {
            setMaxSize(maxSize);
            container.start();
        }
    }

    public void projectClosed() {
        container.stop();
    }

    public CabinetController getCabinetController() {
        return container.getCabinetController();
    }

    public String maxMemory() {
        if (maxMemory == null || !maxMemory.matches("\\d+")) {
            return null;
        }
        return maxMemory;
    }

    public String expectedTestCaseNameRegex() {
        if (Utils.isEmpty(expectedTestCaseNameRegex)) {
            return DEFAULT_TEST_CLASS_NAME_REGEX;
        }
        return expectedTestCaseNameRegex;
    }

    public int maxSize() {
        return maxSize;
    }

    public int autoRunTestsDelayTime() {
        return autoRunTestsDelayTime;
    }

    public String jvmArgs() {
        return jvmArgs;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        getTestShadowMap().setMaxSize(maxSize);
    }

    public String getDisplayName() {
        return "Fireworks";
    }

    public void apply() throws ConfigurationException {
        enable = getConfigurationUI().isEnable();
        if (enable) {
            container.start();
        } else {
            container.stop();
        }
        maxMemory = getConfigurationUI().maxMemory();
        expectedTestCaseNameRegex = getConfigurationUI().expectedTestCaseNameRegex();
        setMaxSize(getConfigurationUI().maxSize());
        autoRunTestsDelayTime = getConfigurationUI().autoRunTestsDelayTime();
        jvmArgs = getConfigurationUI().jvmArgs();
    }

    public void reset() {
        getConfigurationUI().setEnable(enable);
        getConfigurationUI().setMaxMemory(maxMemory);
        getConfigurationUI().setExpectedTestCaseNameRegex(expectedTestCaseNameRegex);
        getConfigurationUI().setMaxSize(maxSize);
        getConfigurationUI().setAutoRunTestsDelayTime(autoRunTestsDelayTime);
        getConfigurationUI().setJvmArgs(jvmArgs);
    }

    public TestShadowMap getTestShadowMap() {
        return container.getTestShadowMap();
    }

    public boolean isEnable() {
        return enable;
    }
}
