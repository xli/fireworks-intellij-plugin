package com.thoughtworks.fireworks.ui.toolwindow;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BevelBorderMouseListener implements MouseListener {
    private AbstractButton comp;
    private Icon enterIcon;
    private Icon exitedIcon;

    public BevelBorderMouseListener(AbstractButton comp, Icon enterIcon, Icon exitedIcon) {
        this.comp = comp;
        this.enterIcon = enterIcon;
        this.exitedIcon = exitedIcon;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        comp.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void mouseReleased(MouseEvent e) {
        comp.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void mouseEntered(MouseEvent e) {
        comp.setIcon(enterIcon);
    }

    public void mouseExited(MouseEvent e) {
        comp.setIcon(exitedIcon);
    }
}
