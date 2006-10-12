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
package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.*;
import junit.framework.TestResult;

public class IntellijShadowCabinet implements TestShadowMap, Cabinet {
    private final ShadowCabinet shadowCabinet = new ShadowCabinet();
    private final CompilerManagerAdaptee compilerManager;
    private final TestStateListener stateListener;
    private boolean isEmptyCabinetPreActionTime;

    public IntellijShadowCabinet(ShadowCabinetListener[] listeners,
                                 CompilerManagerAdaptee compilerManager,
                                 TestStateListener stateListener) {
        this.compilerManager = compilerManager;
        this.stateListener = stateListener;
        for (int i = 0; i < listeners.length; i++) {
            shadowCabinet.addListener(listeners[i]);
        }
    }

    public void action(final TestResult result) {
        if(isEmptyCabinetPreActionTime && isEmpty())  {
            return;
        }
        compilerManager.compile(new CabinetActionNotification(shadowCabinet, result));
        isEmptyCabinetPreActionTime = result.runCount() == 0;
    }

    public void addTestShadow(Sunshine sunshine, String testClassName) {
        shadowCabinet.add(newComparableTestShadow(sunshine, testClassName));
    }

    public void setMaxSize(int maxSize) {
        shadowCabinet.setMaxSize(maxSize);
    }

    public void removeTestShadow(Sunshine sunshine, String testClassName) {
        shadowCabinet.remove(newComparableTestShadow(sunshine, testClassName));
    }

    private ComparableTestShadow newComparableTestShadow(Sunshine sunshine, String testClassName) {
        ComparableTestShadow shadow = new ComparableTestShadow(new ShineTestClassShadow(testClassName, sunshine), testClassName);
        shadow.addListener(stateListener);
        return shadow;
    }

    public int size() {
        return shadowCabinet.size();
    }

    public void addListener(ShadowCabinetListener listener) {
        shadowCabinet.addListener(listener);
    }

    public void removeListener(ShadowCabinetListener listener) {
        shadowCabinet.removeListener(listener);
    }
    private boolean isEmpty() {
        return size() == 0;
    }

}
