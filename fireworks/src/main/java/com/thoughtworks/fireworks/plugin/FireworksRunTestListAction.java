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
