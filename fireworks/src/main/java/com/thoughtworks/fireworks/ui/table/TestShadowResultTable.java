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

import com.thoughtworks.fireworks.controllers.tree.JumpToSourceAdaptee;
import com.thoughtworks.fireworks.core.table.ShadowTableModel;
import com.thoughtworks.shadow.Shadow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class TestShadowResultTable extends JTable {
    private static final int FONT_SIZE = 12;
    private static final int ROW_HEIGHT = 20;
    private final JumpToSourceAdaptee jumpToSourceAdaptee;

    public TestShadowResultTable(ShadowTableModel model,
                                 JumpToSourceAdaptee jumpToSourceAdaptee,
                                 SeperateRowByColorTableCellRenderer defaultCellRenderer,
                                 DialogTraceLogViewer logViewer) {
        super(model);
        this.jumpToSourceAdaptee = jumpToSourceAdaptee;

        setUpFont();
        setUpSelectionModel();
        setUpColumnPreferredWidth();

        this.setDefaultRenderer(Shadow.class, defaultCellRenderer);
        this.setRowHeight(ROW_HEIGHT);

        logViewer.setTraceLogFrameFactory(new TraceLogFrameFactory(new ClearTableSelectionListener(this)));
    }

    private void setUpColumnPreferredWidth() {
        this.getColumnModel().getColumn(ShadowTableModel.TEST_METHOD).setPreferredWidth(300);
        this.getColumnModel().getColumn(ShadowTableModel.MESSAGE).setPreferredWidth(400);
        this.getColumnModel().getColumn(ShadowTableModel.TRACE_LOG).setPreferredWidth(500);
        this.getColumnModel().getColumn(ShadowTableModel.TEST_CLASS).setPreferredWidth(100);
    }

    private void setUpSelectionModel() {
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setColumnSelectionAllowed(false);
        this.setRowSelectionAllowed(false);
        this.setCellSelectionEnabled(true);

        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectCell();
                }
            }
        });
    }

    private void selectCell() {
        int rowIndex = getSelectedRow();
        int columnIndex = getSelectedColumn();
        if (rowIndex == -1 || columnIndex == -1) {
            return;
        }
        getShadowAt(rowIndex, columnIndex).accept(jumpToSourceAdaptee);
    }

    private Shadow getShadowAt(int rowIndex, int columnIndex) {
        return ((Shadow) getValueAt(rowIndex, columnIndex));
    }

    private void setUpFont() {
        Font font = getFont();
        if (font.getSize() < FONT_SIZE) {
            setFont(new Font(font.getFontName(), font.getStyle(), FONT_SIZE));
        }
    }

}
