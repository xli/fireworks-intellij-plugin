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
