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
package com.thoughtworks.fireworks.controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Icons {
    private static final int SMALL_ICON_LENGTH = 16;
    private static final int BIG_ICON_LENGTH = 32;

    private static ImageIcon createSmallImageIcon(String path) {
        return createImageIcon(path, SMALL_ICON_LENGTH, SMALL_ICON_LENGTH);
    }

    private static ImageIcon createBigImageIcon(String path) {
        return createImageIcon(path, BIG_ICON_LENGTH, BIG_ICON_LENGTH);
    }

    private static ImageIcon createImageIcon(String path, int height, int width) {
        URL icon = Icons.class.getResource(path);
        if (icon == null) {
            String msg = "couldn't find file: " + path;
            JOptionPane.showMessageDialog(null, "fireworks: " + msg);
            throw new IllegalArgumentException(msg);
        }
        ImageIcon imageIcon = new ImageIcon(icon);
        if (imageIcon.getIconHeight() == height && imageIcon.getIconWidth() == width) {
            return imageIcon;
        }
        return greenImage(imageIcon, height, width);
    }

    public static ImageIcon greenImage(ImageIcon original, int height, int width) {
        Image img = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(original.getImage(), 0, 0, height, width, new Color(0, 255, 0, 0), null);
        return new ImageIcon(img);
    }

    private static ImageIcon logo = createBigImageIcon("/logo.png");

    private static ImageIcon successIcon = createSmallImageIcon("/green.png");
    private static ImageIcon failedIcon = createSmallImageIcon("/red.png");
    private static ImageIcon ignoredIcon = createSmallImageIcon("/ignored.png");
    private static ImageIcon compileFailedIcon = createSmallImageIcon("/compile_fialed.png");
    private static ImageIcon pending = createSmallImageIcon("/pending.png");
    private static ImageIcon actionPending = createSmallImageIcon("/action_pending.png");
    private static ImageIcon runTestListButton = createSmallImageIcon("/run_test_list_button.png");
    private static ImageIcon runTestListButtonF = createSmallImageIcon("/run_test_list_button_f.png");
    private static ImageIcon runAllTestsButton = createSmallImageIcon("/run_all_tests_button.png");
    private static ImageIcon runAllTestsButtonF = createSmallImageIcon("/run_all_tests_button_f.png");

    public static ImageIcon logo() {
        return logo;
    }

    public static Icon actionPending() {
        return actionPending;
    }

    public static Icon successIcon() {
        return successIcon;
    }

    public static Icon ignoredIcon() {
        return ignoredIcon;
    }

    public static Icon failureIcon() {
        return failedIcon;
    }

    public static Icon compileFailedIcon() {
        return compileFailedIcon;
    }

    public static Icon pending() {
        return pending;
    }

    public static Icon runTestListButton() {
        return runTestListButton;
    }

    public static Icon runTestListButtonF() {
        return runTestListButtonF;
    }

    public static Icon runAllTestsButton() {
        return runAllTestsButton;
    }

    public static Icon runAllTestsButtonF() {
        return runAllTestsButtonF;
    }
}
