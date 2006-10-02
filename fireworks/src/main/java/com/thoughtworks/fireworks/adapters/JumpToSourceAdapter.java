package com.thoughtworks.fireworks.adapters;

import com.thoughtworks.fireworks.controllers.tree.JumpToSourceAdaptee;

public class JumpToSourceAdapter implements JumpToSourceAdaptee {
    private final ProjectAdapter project;
    private String testClassName;
    private String testMethodName;

    public JumpToSourceAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public void visitTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public void visitTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public void end() {
        project.jumpToSource(testClassName, testMethodName);
        testClassName = null;
        testMethodName = null;
    }
}
