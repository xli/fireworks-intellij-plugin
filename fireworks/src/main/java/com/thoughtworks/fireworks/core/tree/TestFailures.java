package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.Shadow;

import java.util.*;

public class TestFailures {

    private final Map<ShadowTreeNode, List<Throwable>> testFailures = new HashMap();

    private void put(ShadowTreeNode node, Throwable t) {
        getFailures(node).add(t);
        putIntoParent(node, t);
    }

    private void putIntoParent(ShadowTreeNode node, Throwable t) {
        if (node.parent() != null) {
            getFailures(node.parent()).add(t);
            putIntoParent(node.parent(), t);
        }
    }

    private List<Throwable> getFailures(ShadowTreeNode node) {
        List<Throwable> failures = testFailures.get(node);
        if (failures == null) {
            failures = new ArrayList();
            testFailures.put(node, failures);
        }
        return failures;
    }

    public void output(ShadowTreeNode node, ConsoleViewAdaptee consoleView) {
        List<Throwable> failures = getFailures(node);
        StringBuffer buffer = new StringBuffer();
        for (Throwable t : failures) {
            buffer.append(Utils.toString(t));
        }
        consoleView.cleanAndPrint(buffer.toString());
    }

    private final Map<Shadow, Throwable> buffer = new HashMap();

    public void addIntoBuffer(Shadow shadow, Throwable t) {
        buffer.put(shadow, t);
    }

    public void commitBuffer(ShadowTreeNode parent) {
        for (Iterator<Shadow> iter = buffer.keySet().iterator(); iter.hasNext();) {
            Shadow shadow = iter.next();
            put(new ShadowMethodTreeNode(shadow, parent), buffer.get(shadow));
        }
        buffer.clear();
    }
}
