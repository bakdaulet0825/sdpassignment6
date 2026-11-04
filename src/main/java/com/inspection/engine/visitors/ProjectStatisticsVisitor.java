package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;

public class ProjectStatisticsVisitor implements ProjectVisitor {
    private int totalFiles = 0;
    private int totalLinesOfCode = 0;
    private int totalComplexity = 0;
    private int sourceFilesCount = 0;
    private int testFilesCount = 0;

    @Override
    public void visit(SourceCodeFile file) {
        totalFiles++;
        sourceFilesCount++;
        totalLinesOfCode += file.getLinesOfCode();
        totalComplexity += file.getComplexity();
        System.out.println("  - Source file: " + file.getName() + " (" + file.getLinesOfCode() + " lines)");
    }

    @Override
    public void visit(TestFile file) {
        totalFiles++;
        testFilesCount++;
        totalLinesOfCode += file.getLinesOfCode();
        System.out.println("  - Test file: " + file.getName() + " (" + file.getLinesOfCode() + " lines, " + file.getTestCount() + " tests)");
    }

    @Override
    public void visit(ConfigurationFile file) {
        totalFiles++;
        totalLinesOfCode += file.getLinesOfCode();
        System.out.println("  - Config file: " + file.getName() + " (" + file.getLinesOfCode() + " lines)");
    }

    @Override
    public void visit(DependencyFile file) {
        totalFiles++;
        System.out.println("  - Dependency file: " + file.getName() + " (" + file.getDependencyCount() + " dependencies)");
    }

    @Override
    public void visit(Directory directory) {
        System.out.println("-> Analyzing directory stats: " + directory.getName());
    }

    public int getTotalFiles() { return totalFiles; }
    public int getTotalLinesOfCode() { return totalLinesOfCode; }

    public double getAverageComplexity() {
        return sourceFilesCount == 0 ? 0.0 : (double) totalComplexity / sourceFilesCount;
    }

    public double getTestToSourceRatio() {
        return sourceFilesCount == 0 ? 0.0 : (double) testFilesCount / sourceFilesCount;
    }

    public void printSummary() {
        System.out.println("   [SUMMARY] Total Files: " + totalFiles + ", Total Lines of Code: " + totalLinesOfCode);
    }
}