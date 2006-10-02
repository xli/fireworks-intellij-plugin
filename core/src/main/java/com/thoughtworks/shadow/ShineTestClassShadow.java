package com.thoughtworks.shadow;

import junit.framework.Test;
import junit.framework.TestResult;

public class ShineTestClassShadow implements Test {
    private final String testClassName;
    private final Sunshine sunshine;

    public ShineTestClassShadow(String testClassName, Sunshine sunshine) {
        this.testClassName = testClassName;
        this.sunshine = sunshine;
    }

    public int countTestCases() {
        return sunshine.shine(testClassName).countTestCases();
    }

    public void run(TestResult result) {
        sunshine.shine(testClassName).run(result);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ShineTestClassShadow that = (ShineTestClassShadow) o;

        return testClassName.equals(that.testClassName);
    }

    public int hashCode() {
        return testClassName.hashCode();
    }

    public String toString() {
        int index = testClassName.lastIndexOf(".");
        if (index < 0) {
            return testClassName;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(testClassName.substring(index + 1));
        buffer.append("(");
        buffer.append(testClassName.substring(0, index));
        buffer.append(")");
        return buffer.toString();
    }

}
