package com.inspection.engine.elements;

import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;

public class SourceCodeFile implements ProjectElement {
    private final String fileName;
    private final int linesOfCode;
    private final int cyclomaticComplexity;
    private final boolean highRiskFlag;

    public SourceCodeFile(String fileName, int linesOfCode, int cyclomaticComplexity, boolean highRiskFlag) {
        this.fileName = fileName;
        this.linesOfCode = linesOfCode;
        this.cyclomaticComplexity = cyclomaticComplexity;
        this.highRiskFlag = highRiskFlag;
    }

    public String getFileName() { return fileName; }
    public int getLinesOfCode() { return linesOfCode; }
    public int getCyclomaticComplexity() { return cyclomaticComplexity; }
    public boolean isHighRiskFlag() { return highRiskFlag; }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}
