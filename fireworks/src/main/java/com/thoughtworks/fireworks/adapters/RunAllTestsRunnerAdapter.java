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

import com.thoughtworks.fireworks.adapters.search.TestCaseSearcher;
import com.thoughtworks.fireworks.controllers.AllTestsRunner;
import com.thoughtworks.fireworks.core.AllTestShadowCabinet;
import com.thoughtworks.fireworks.core.TestResultFactory;

public class RunAllTestsRunnerAdapter implements AllTestsRunner {
    private final AllTestShadowCabinet cabinet;
    private final TestResultFactory factory;
    private final TestCaseSearcher searcher;

    public RunAllTestsRunnerAdapter(AllTestShadowCabinet cabinet, TestCaseSearcher searcher, TestResultFactory factory) {
        this.cabinet = cabinet;
        this.searcher = searcher;
        this.factory = factory;
    }

    public void run() {
        cabinet.init();
        searcher.action(cabinet);
        cabinet.action(factory.createTestResult());
    }
}
