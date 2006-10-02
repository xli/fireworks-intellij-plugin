package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import javax.swing.table.TableModel;

public class ShadowTableModelTest extends TestCase {
    private TableModel model;
    private Mock logViewer;

    protected void setUp() throws Exception {
        logViewer = Turtle.mock(TraceLogViewer.class);
        model = new ShadowTableModel((TraceLogViewer) logViewer.mockTarget());
    }

    public void testDefaultRowCount() throws Exception {
        assertEquals(0, model.getRowCount());
    }

    public void testColumnCount() throws Exception {
        assertEquals(4, model.getColumnCount());
    }

    public void testColumnName() throws Exception {
        assertEquals("Test", model.getColumnName(ShadowTableModel.TEST_METHOD));
        assertEquals("Message", model.getColumnName(ShadowTableModel.MESSAGE));
        assertEquals("Trace Log", model.getColumnName(ShadowTableModel.TRACE_LOG));
        assertEquals("Test Class", model.getColumnName(ShadowTableModel.TEST_CLASS));
    }

    public void testColumnClass() throws Exception {
        for (int i = 0; i < model.getColumnCount(); i++) {
            assertEquals(Shadow.class, model.getColumnClass(i));
        }
    }

    public void testCellShouldNotBeEditable() throws Exception {
        assertFalse(model.isCellEditable(0, 0));
        assertFalse(model.isCellEditable(0, 1));
        assertFalse(model.isCellEditable(1000, 1000));
        assertFalse(model.isCellEditable(-1, -1));
    }

    public void testShouldThrowIndexOutOfBoundsExceptionWhenGetValueAtAnInvalidRow() throws Exception {
        try {
            model.getValueAt(0, 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e.getMessage());
        }
    }

    public void testShouldThrowUnsupportedOperationExceptionWhenSetValue() throws Exception {
        try {
            model.setValueAt(new Object(), 0, 0);
            fail();
        } catch (UnsupportedOperationException e) {
            assertNotNull(e.getMessage());
        }
    }

}
