package com.thoughtworks.fireworks.core.tree;

import javax.swing.*;

public interface TestStatusSummaryListener {
    void summaryStatusChanged(Icon status);

    void statusChanged(Object key, Icon status);
}
