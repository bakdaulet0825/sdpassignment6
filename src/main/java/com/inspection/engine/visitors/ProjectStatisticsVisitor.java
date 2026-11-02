package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;

public class ProjectStatisticsVisitor implements ProjectVisitor {
    private int totalFiles = 0;
    private int totalLinesOfCode = 0;
    private int totalTests = 0;
    private int totalDependencies = 0;
    private int sourceFileCount = 0;
    private int totalComplexity = 0;

    public int getTotalFiles() { return totalFiles; }
    public int getTotalLinesOfCode() { return totalLinesOfCode; }
    public int getTotalTests() { return totalTests; }
    public int getTotalDependencies() { return totalDependencies; }

    public double getAverageComplexity() {
        return sourceFileCount == 0 ? 0.0 : (double) totalComplexity / sourceFileCount;
    }

    public double getTestToSourceRatio() {
        return sourceFileCount == 0 ? 0.0 : (double) totalTests / sourceFileCount;
    }

    @Override
    public void visit(SourceCodeFile file) {
        totalFiles++;
        sourceFileCount++;
        totalLinesOfCode += file.getLinesOfCode();
        totalComplexity += file.getCyclomaticComplexity();
    }

    @Override
    public void visit(TestFile file) {
        totalFiles++;
        totalLinesOfCode += file.getLinesOfCode();
        totalTests += file.getTestCount();
    }

    @Override
    public void visit(ConfigurationFile file) {
        totalFiles++;
        totalLinesOfCode += file.getLinesOfCode();
    }

    @Override
    public void visit(DependencyFile file) {
        totalFiles++;
        totalDependencies += file.getDependencyCount();
    }

    @Override
    public void visit(Directory directory) {
        // Structural element
    }
}
