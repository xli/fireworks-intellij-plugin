package com.thoughtworks.shadow.junit;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.textui.TestRunner;
import org.junit.Ignore;
import org.junit.internal.runners.TestClass;
import org.junit.runner.JUnitCore;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JUnitAdapter {
    private final Class runnerClass;
    private final Method[] testMethods;
    private final Class testClass;

    public JUnitAdapter(Class aClass) {
        testClass = aClass;
        boolean isJUnit4 = !isSubClassOfJUnit3TestCase();
        if (isJUnit4) {
            runnerClass = JUnitCore.class;
            testMethods = getJUnit4TestMethods();
        } else {
            runnerClass = TestRunner.class;
            testMethods = getMethodsStartWithTest();
        }
    }

    /**
     * for different class loader, we can't use "equals" or "isAssignableFrom" to
     * confirm the class is sub class of junit3 TestCase directly
     *
     * @return true if the param "aClass" is a sub class of JUnit3 TestCase
     */
    private boolean isSubClassOfJUnit3TestCase() {
        return getClassFromClassLoaderOfTestClass(TestCase.class).isAssignableFrom(testClass);
    }

    private Method[] getMethodsStartWithTest() {
        Method[] methods = testClass.getMethods();
        List<Method> testMethods = new ArrayList();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().startsWith("test")) {
                testMethods.add(methods[i]);
            }
        }
        return toArray(testMethods);
    }

    private Method[] getJUnit4TestMethods() {
        return toArray(new TestClass(testClass).getAnnotatedMethods(getClassFromClassLoaderOfTestClass(org.junit.Test.class)));
    }

    private Class getClassFromClassLoaderOfTestClass(Class target) {
        try {
            return testClass.getClassLoader().loadClass(target.getName());
        } catch (ClassNotFoundException e) {
            return target;
        }
    }

    private Method[] toArray(List<Method> testMethods) {
        return testMethods.toArray(new Method[testMethods.size()]);
    }

    public Class runnerClass() {
        return runnerClass;
    }

    public junit.framework.Test toTestCase(LogThrowable throwable) {
        Protectable protectable = ProtectableFactory.protectable(throwable);
        return new ErrorTestCase(testClass.getName(), protectable);
    }

    public Test[] createTests() {
        Test[] tests = new junit.framework.Test[testMethods.length];
        for (int i = 0; i < testMethods.length; i++) {
            tests[i] = createTestCase(testMethods[i]);
        }
        return tests;
    }

    private Test createTestCase(Method method) {
        if (isIgnored(method)) {
            return new IgnoredTestCase(testClass.getName(), method.getName());
        }
        return new SuccessfulTestCase(testClass.getName(), method.getName());
    }

    private boolean isIgnored(Method method) {
        return method.getAnnotation(getClassFromClassLoaderOfTestClass(Ignore.class)) != null;
    }
}
