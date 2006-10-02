package com.thoughtworks.fireworks.plugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.thoughtworks.fireworks.controllers.CabinetController;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.fireworks.core.IntellijShadowCabinet;
import com.thoughtworks.shadow.Utils;

public class FireworksProject extends FireworksConfiguration implements ProjectComponent, FireworksConfig {

    private static final String DEFAULT_TEST_CLASS_NAME_REGEX = ".*Test";

    public String expectedTestCaseNameRegex = null;
    public String maxMemory = null;
    public int maxSize = 5;
    public boolean enable = true;
    public int autoRunTestsDelayTime = -1;

    private Project project;
    private FireworksContainer container;

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

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        getCabinet().setMaxSize(maxSize);
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
    }

    public void reset() {
        getConfigurationUI().setEnable(enable);
        getConfigurationUI().setMaxMemory(maxMemory);
        getConfigurationUI().setExpectedTestCaseNameRegex(expectedTestCaseNameRegex);
        getConfigurationUI().setMaxSize(maxSize);
        getConfigurationUI().setAutoRunTestsDelayTime(autoRunTestsDelayTime);
    }

    public IntellijShadowCabinet getCabinet() {
        return container.getCabinet();
    }

    public boolean isEnable() {
        return enable;
    }
}
