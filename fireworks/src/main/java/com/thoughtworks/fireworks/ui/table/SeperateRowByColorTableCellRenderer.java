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

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class SeperateRowByColorTableCellRenderer implements TableCellRenderer {

    private static final Logger LOG = Logger.getLogger(SeperateRowByColorTableCellRenderer.class);

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        if (value == null) {
            LOG.error("table value is null: row=" + row + ", column=" + column);
            value = "";
        }
        JLabel component = defaultRendererComp(table, value, isSelected, hasFocus, row, column);
        component.setToolTipText(value.toString());
        if (!isSelected) {
            component.setBackground(new Color(255, green(row), 0));
        }
        return component;
    }

    private int green(int row) {
        int green = 255 - (row + 1) * 25;
        return green < 0 ? 0 : green;
    }

    private JLabel defaultRendererComp(JTable table,
                                       Object value,
                                       boolean isSelected,
                                       boolean hasFocus,
                                       int row,
                                       int column) {
        return (JLabel) getRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    private DefaultTableCellRenderer getRenderer() {
        return new DefaultTableCellRenderer();
    }

}
