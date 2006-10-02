package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.shadow.Utils;
import com.thoughtworks.shadow.junit.BaseTestCase;
import com.thoughtworks.shadow.junit.FailureTestTraceLog;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.constraint.CheckResult;
import com.thoughtworks.turtlemock.constraint.Constraint;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ShadowTableModelTableChangedEventTest extends TestCase {
    private ShadowTableModel model;
    private TestShadow failureShadow;
    private TestResult result;
    private Mock listener;
    private Mock logViewer;

    protected void setUp() throws Exception {
        logViewer = Turtle.mock(TraceLogViewer.class);
        model = new ShadowTableModel((TraceLogViewer) logViewer.mockTarget());
        failureShadow = new BaseTestCase("test class name", "test method name", new Protectable() {
            public void protect() throws Throwable {
                fail("must fail");
            }
        });
        result = new TestResult();
        result.addListener(model);
        listener = Turtle.mock(TableModelListener.class);
    }


    public void testRunTestCaseCreatedByFailureTestTraceLog() throws Exception {
        final String exceptionMessage = "testShouldFailed(TestClass)AssertionFailedError";
        final String exceptionTrace = exceptionMessage + Utils.LINE_SEP + "\tat package.TestClass.testShouldFailed(TestClass.java:7)";
        FailureTestTraceLog log = new FailureTestTraceLog(exceptionTrace);
        Test test = log.testCase();

        model.addTableModelListener((TableModelListener) listener.mockTarget());
        test.run(result);

        assertEquals("testShouldFailed", model.getValueAt(0, ShadowTableModel.TEST_METHOD).toString());
        assertEquals("AssertionFailedError", model.getValueAt(0, ShadowTableModel.MESSAGE).toString());
        assertEquals("(TestClass.java:7)package.TestClass.testShouldFailed", model.getValueAt(0, ShadowTableModel.TRACE_LOG).toString());
        assertEquals("TestClass", model.getValueAt(0, ShadowTableModel.TEST_CLASS).toString());
    }

    public void testShouldFireTableChangedEventWhenTestShadowFailed() throws Exception {
        model.addTableModelListener((TableModelListener) listener.mockTarget());
        failureShadow.run(result);
        listener.assertDid("tableChanged").withFirstArgConstraint(new Constraint() {
            public CheckResult check(Object param) {
                TableModelEvent event = (TableModelEvent) param;
                assertEquals(TableModelEvent.ALL_COLUMNS, event.getColumn());
                assertEquals(0, event.getFirstRow());
                assertEquals(0, event.getLastRow());
                assertEquals(TableModelEvent.INSERT, event.getType());
                return CheckResult.PASS;
            }
        });
    }

    public void testShouldNotFireTableChangeEventWhenStartActionAndThereIsNotTestFailureBefore() throws Exception {
        model.startAction();
        listener.assertNotDid("tableChanged");
    }

    public void testShouldFireAllTableRowsDeletedEventWhenStartActionByCabinet() throws Exception {
        model.addError(failureShadow, new Throwable());
        model.addError(failureShadow, new Throwable());
        model.addError(failureShadow, new Throwable());

        model.addTableModelListener((TableModelListener) listener.mockTarget());
        model.startAction();
        listener.assertDid("tableChanged").withFirstArgConstraint(new Constraint() {
            public CheckResult check(Object param) {
                TableModelEvent event = (TableModelEvent) param;
                assertEquals(TableModelEvent.ALL_COLUMNS, event.getColumn());
                assertEquals(0, event.getFirstRow());
                assertEquals(2, event.getLastRow());
                assertEquals(TableModelEvent.DELETE, event.getType());
                return CheckResult.PASS;
            }
        });
    }

    public void testRemoveTableModelListener() throws Exception {
        model.addTableModelListener((TableModelListener) listener.mockTarget());
        model.removeTableModelListener((TableModelListener) listener.mockTarget());
        model.addError(failureShadow, new Throwable());
        listener.assertNotDid("tableChanged");
    }
}
