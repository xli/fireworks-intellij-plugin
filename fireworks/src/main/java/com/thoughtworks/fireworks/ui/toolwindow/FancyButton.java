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
package com.thoughtworks.fireworks.ui.toolwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FancyButton extends JButton implements MouseListener {

    private final Icon icon;
    private final Icon enterIcon;

    public FancyButton(Icon icon, Icon enterIcon) {
        super(icon);
        this.icon = icon;
        this.enterIcon = enterIcon;
        this.addMouseListener(this);
        setBorder(BorderFactory.createRaisedBevelBorder());
        setMargin(new Insets(1, 1, 1, 1));
        setFocusable(false);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void mouseReleased(MouseEvent e) {
        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void mouseEntered(MouseEvent e) {
        setIcon(enterIcon);
    }

    public void mouseExited(MouseEvent e) {
        setIcon(icon);
    }
}
