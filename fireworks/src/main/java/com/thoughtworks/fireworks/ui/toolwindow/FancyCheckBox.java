package com.thoughtworks.fireworks.ui.toolwindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class FancyCheckBox extends JCheckBox {

    public FancyCheckBox(Icon defaultIcon, String tipText, Icon selectedIcon, boolean selected, ActionListener listener) {
        super(defaultIcon);
        setToolTipText(tipText);
        setSelectedIcon(selectedIcon);
        setMargin(new Insets(1, 1, 1, 1));
        setBorderPainted(true);
        setSelected(selected);
        addActionListener(listener);
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                resetBorder(isSelected());
            }
        });
        resetBorder(selected);
    }

    private void resetBorder(boolean selected) {
        if (selected) {
            setBorder(BorderFactory.createLoweredBevelBorder());
        } else {
            setBorder(BorderFactory.createRaisedBevelBorder());
        }
    }
}
