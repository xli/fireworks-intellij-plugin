package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.TestShadowMap;
import com.thoughtworks.fireworks.core.TestUtils;
import com.thoughtworks.fireworks.stubs.Success;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class TestCaseOpenedListenerTest extends TestCase {
    private TestCaseOpenedListener listener;
    private Mock document;
    private Mock testShadowMap;
    private Mock refactoringListenerProvider;
    private Sunshine sunshine;

    protected void setUp() throws Exception {
        sunshine = TestUtils.sunshine();
        testShadowMap = Turtle.mock(TestShadowMap.class);
        refactoringListenerProvider = Turtle.mock(RefactoringListenerProvider.class);

        listener = new TestCaseOpenedListener((TestShadowMap) testShadowMap.mockTarget(), (RefactoringListenerProvider) refactoringListenerProvider.mockTarget());

        document = Turtle.mock(DocumentAdaptee.class);
        document.ifCall("getSunshine").willReturn(sunshine);
        document.ifCall("getJavaFileClassName").willReturn(Success.class.getName());
    }

    public void testShouldRemoveTestShadowIfTheDocumentIsNotTestCase() throws Exception {
        document.ifCall("isWritable").willReturn(Boolean.TRUE);
        document.ifCall("isJavaFile").willReturn(Boolean.TRUE);
        document.ifCall("isExpectedJUnitTestCase").willReturn(Boolean.FALSE);
        listener.checkDocument((DocumentAdaptee) document.mockTarget());
        testShadowMap.assertDid("removeTestShadow").with(sunshine, Success.class.getName());
        refactoringListenerProvider.assertDid("removeListener").with(document.mockTarget());
    }

    public void testShouldAddTestShadowIfTheDocumentIsATestCase() throws Exception {
        document.ifCall("isWritable").willReturn(Boolean.TRUE);
        document.ifCall("isJavaFile").willReturn(Boolean.TRUE);
        document.ifCall("isExpectedJUnitTestCase").willReturn(Boolean.TRUE);
        listener.checkDocument((DocumentAdaptee) document.mockTarget());
        testShadowMap.assertDid("addTestShadow").with(sunshine, Success.class.getName());
        refactoringListenerProvider.assertDid("addListener").with(document.mockTarget(), sunshine, Success.class.getName());
    }

    public void testShouldNotAddOrRemoveTestShadowIfTheDocumentIsNotWritable() throws Exception {
        document.ifCall("isJavaFile").willReturn(Boolean.TRUE);
        document.ifCall("isWritable").willReturn(Boolean.FALSE);
        document.ifCall("isExpectedJUnitTestCase").willReturn(Boolean.TRUE);
        listener.checkDocument((DocumentAdaptee) document.mockTarget());
        testShadowMap.assertNotDid("addTestShadow");
        testShadowMap.assertNotDid("removeTestShadow");
        refactoringListenerProvider.assertNotDid("removeListener");
        refactoringListenerProvider.assertNotDid("addListener");
    }

    public void testShouldNotAddOrRemoveTestShadowIfTheDocumentIsNotJavaFile() throws Exception {
        document.ifCall("isWritable").willReturn(Boolean.TRUE);
        document.ifCall("isJavaFile").willReturn(Boolean.FALSE);
        document.ifCall("isExpectedJUnitTestCase").willReturn(Boolean.TRUE);
        listener.checkDocument((DocumentAdaptee) document.mockTarget());
        testShadowMap.assertNotDid("addTestShadow");
        testShadowMap.assertNotDid("removeTestShadow");
        refactoringListenerProvider.assertNotDid("removeListener");
        refactoringListenerProvider.assertNotDid("addListener");
    }
}
