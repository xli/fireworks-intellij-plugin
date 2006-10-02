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
