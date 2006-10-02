package com.thoughtworks.fireworks.ui.table;

import com.thoughtworks.fireworks.controllers.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class TraceLogFrameFactory {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 400;

    private final WindowAdapter listener;

    public TraceLogFrameFactory(WindowAdapter listener) {
        this.listener = listener;
    }

    public JFrame createTraceLogFrame(Component console) {
        JFrame traceLogFrame = new JFrame("Trace Log");
        traceLogFrame.setIconImage(Icons.logo().getImage());
        traceLogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        traceLogFrame.addWindowListener(listener);
        traceLogFrame.addWindowFocusListener(listener);

        traceLogFrame.getContentPane().setLayout(new BorderLayout());
        traceLogFrame.getContentPane().add(console, BorderLayout.CENTER);
        traceLogFrame.setSize(WIDTH, HEIGHT);

        return traceLogFrame;
    }
}
