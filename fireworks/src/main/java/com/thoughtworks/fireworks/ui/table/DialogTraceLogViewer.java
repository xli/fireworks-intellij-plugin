package com.thoughtworks.fireworks.ui.table;

import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.fireworks.core.table.TraceLogViewer;

import javax.swing.*;

public class DialogTraceLogViewer implements TraceLogViewer {
    private final ProjectAdapter project;
    private TraceLogFrameFactory traceLogFrameFactory;

    public DialogTraceLogViewer(ProjectAdapter project) {
        this.project = project;
    }

    public void display(Throwable t) {
        traceLogFrameFactory.createTraceLogFrame(getConsoleComp(t)).setVisible(true);
    }

    private JComponent getConsoleComp(Throwable t) {
        ConsoleViewAdaptee console = project.createTextConsoleBuilder();
        console.cleanAndPrint(Utils.toString(t));
        return console.getComponent();
    }

    public void setTraceLogFrameFactory(TraceLogFrameFactory traceLogFrameFactory) {
        this.traceLogFrameFactory = traceLogFrameFactory;
    }
}
