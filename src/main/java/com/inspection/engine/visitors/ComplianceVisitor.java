package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;
import java.util.ArrayList;
import java.util.List;

public class ComplianceVisitor implements ProjectVisitor {
    private final List<String> violations = new ArrayList<>();

    public List<String> getViolations() { return violations; }
    public boolean isCompliant() { return violations.isEmpty(); }

    @Override
    public void visit(SourceCodeFile file) {
        if (file.getCyclomaticComplexity() > 15) {
            violations.add("Complexity violation in file: " + file.getFileName());
        }
    }

    @Override
    public void visit(TestFile file) {
        if (file.getTestCount() == 0) {
            violations.add("Empty test file detected: " + file.getFileName());
        }
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.hasExposedSecret()) {
            violations.add("Exposed credentials detected in config: " + file.getFileName());
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (!file.getUnversionedDependencies().isEmpty()) {
            violations.add("Unversioned dependencies found in: " + file.getFileName());
        }
    }

    @Override
    public void visit(Directory directory) {
        // Compliance checks for directory names if needed
    }
}
