package com.thoughtworks.fireworks.plugin;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.thoughtworks.fireworks.controllers.Icons;
import com.thoughtworks.fireworks.plugin.configuration.ConfigurationUI;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class FireworksConfiguration implements Configurable, JDOMExternalizable {
    private ConfigurationUI configurationUI;

    public Icon getIcon() {
        return Icons.logo();
    }

    @Nullable
    @NonNls
    public String getHelpTopic() {
        return null;
    }

    public JComponent createComponent() {
        return getConfigurationUI().getRootComponent();
    }

    public boolean isModified() {
        return true;
    }

    public void disposeUIResources() {
        configurationUI = null;
    }

    public void readExternal(Element element) throws InvalidDataException {
        DefaultJDOMExternalizer.readExternal(this, element);
    }

    public void writeExternal(Element element) throws WriteExternalException {
        DefaultJDOMExternalizer.writeExternal(this, element);
    }

    protected ConfigurationUI getConfigurationUI() {
        if (configurationUI == null) {
            configurationUI = new ConfigurationUI();
        }
        return configurationUI;
    }
}
