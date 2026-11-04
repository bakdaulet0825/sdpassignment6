package com.inspection.engine.elements;

import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;

public class TestFile implements ProjectElement {
    private final String name;
    private final int linesOfCode;
    private final int testCount;
    private final String framework;

    public TestFile(String name, int linesOfCode, int testCount, String framework) {
        this.name = name;
        this.linesOfCode = linesOfCode;
        this.testCount = testCount;
        this.framework = framework;
    }

    public String getFileName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public int getTestCount() {
        return testCount;
    }

    public String getFramework() {
        return framework;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}