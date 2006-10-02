package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.shadow.Utils;
import junit.framework.Protectable;
import junit.framework.TestResult;

public class BaseTestCase implements TestShadow {
    private final Protectable protectable;
    private final String testClassName;
    private final String testMethodName;

    public BaseTestCase(String testClassName, String testMethodName, Protectable protectable) {
        this.testClassName = testClassName;
        this.testMethodName = testMethodName;
        this.protectable = protectable;
    }

    public void accept(ShadowVisitor visitor) {
        visitor.visitTestMethodName(testMethodName);
        visitor.visitTestClassName(testClassName);
        visitor.end();
    }

    public void run(TestResult result) {
        result.startTest(this);
        result.runProtected(this, protectable);
        result.endTest(this);
    }

    public int countTestCases() {
        return 1;
    }

    public String toString() {
        if (testMethodName == null) {
            return testClassName;
        }
        return testMethodName + "(" + testClassName + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (!(o instanceof BaseTestCase))) return false;

        final BaseTestCase that = (BaseTestCase) o;

        return Utils.isEqual(testMethodName, that.testMethodName) && Utils.isEqual(testClassName, that.testClassName);
    }

    public int hashCode() {
        int result;
        result = testClassName.hashCode();
        result = 29 * result + (testMethodName != null ? testMethodName.hashCode() : 0);
        return result;
    }
}
