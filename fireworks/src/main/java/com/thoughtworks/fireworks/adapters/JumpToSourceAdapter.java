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
