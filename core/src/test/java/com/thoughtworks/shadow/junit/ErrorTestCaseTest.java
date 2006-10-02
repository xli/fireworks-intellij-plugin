package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class ErrorTestCaseTest extends TestCase {
    public void testShouldDoNothingWhenAcceptAVisitor() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);
        TestShadow shadow = new ErrorTestCase("name", null);
        shadow.accept((ShadowVisitor) visitor.mockTarget());
        visitor.assertNotDid("visitTestClassName");
        visitor.assertNotDid("visitTestMethodName");
        visitor.assertNotDid("end");
    }
}
