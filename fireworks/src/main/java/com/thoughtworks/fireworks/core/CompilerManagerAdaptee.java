package com.thoughtworks.fireworks.core;

import com.intellij.openapi.compiler.CompileStatusNotification;

public interface CompilerManagerAdaptee {
    void compile(CompileStatusNotification compileStatusNotification);
}
