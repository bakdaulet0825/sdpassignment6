package com.inspection.engine.elements;

import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;

public class TestFile implements ProjectElement {
    private final String fileName;
    private final int linesOfCode;
    private final int testCount;
    private final String framework;

    public TestFile(String fileName, int linesOfCode, int testCount, String framework) {
        this.fileName = fileName;
        this.linesOfCode = linesOfCode;
        this.testCount = testCount;
        this.framework = framework;
    }

    public String getFileName() { return fileName; }
    public int getLinesOfCode() { return linesOfCode; }
    public int getTestCount() { return testCount; }
    public String getFramework() { return framework; }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}
