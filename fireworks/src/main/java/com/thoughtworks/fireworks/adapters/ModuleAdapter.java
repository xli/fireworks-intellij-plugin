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

import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.ProjectJdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.ant.AntSunshine;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ModuleAdapter {
    private final Module module;
    private final FireworksConfig config;

    public ModuleAdapter(Module module, FireworksConfig config) {
        this.module = module;
        this.config = config;
    }

    AntSunshine antSunshine() {
        AntSunshine antSunshine = new AntSunshine(classpaths(), encoding(), baseDir(), jvm(), jvmVersion(), config.maxMemory());
        antSunshine.addJvmArgs(config.jvmArgs());
        return antSunshine;
    }

    private String jvmVersion() {
        return Utils.getJavaVersion(getJdk().getVersionString());
    }

    private String jvm() {
        return getJdk().getVMExecutablePath();
    }

    private URL[] classpaths() {
        VirtualFile[] files = manager().getFiles(OrderRootType.CLASSES_AND_OUTPUT);
        Set<URL> fileURLs = new HashSet<URL>();
        for (int i = 0; i < files.length; i++) {
            fileURLs.add(Utils.toURL(files[i].getPresentableUrl()));
        }
        VirtualFile test = manager().getCompilerOutputPathForTests();
        if (test != null) {
            fileURLs.add(Utils.toURL(test.getPresentableUrl()));
        }
        return fileURLs.toArray(new URL[fileURLs.size()]);
    }

    private File baseDir() {
        return new File(getModuleDir());
    }

    private String encoding() {
        return CharsetToolkit.getIDEOptionsCharset().name();
    }

    private String getModuleDir() {
        VirtualFile parent = module.getModuleFile().getParent();
        if (parent == null) {
            return ".";
        }
        return parent.getPresentableUrl();
    }

    private ProjectJdk getJdk() {
        ProjectJdk jdk = manager().getJdk();
        if (jdk == null) {
            jdk = ProjectRootManager.getInstance(module.getProject()).getProjectJdk();
        }
        if (jdk == null) {
            throw new IllegalArgumentException("the jdk of project and module does not set");
        }
        return jdk;
    }

    private ModuleRootManager manager() {
        return ModuleRootManager.getInstance(module);
    }

}
