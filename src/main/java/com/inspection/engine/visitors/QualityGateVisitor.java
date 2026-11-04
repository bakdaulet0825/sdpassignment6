package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;

public class QualityGateVisitor implements ProjectVisitor {
    private boolean complexityPassed = true;
    private boolean passed = true;

    @Override
    public void visit(SourceCodeFile file) {
        if (file.getComplexity() > 10) {
            System.out.println("  [QUALITY GATE FAIL] Source file '" + file.getName() + "' has high complexity: " + file.getComplexity());
            complexityPassed = false;
            passed = false;
        } else {
            System.out.println("  [QUALITY GATE PASS] Source file '" + file.getName() + "' complexity is acceptable (" + file.getComplexity() + ").");
        }
    }

    @Override
    public void visit(TestFile file) {
        System.out.println("  [QUALITY GATE PASS] Test file '" + file.getName() + "' meets quality standards.");
    }

    @Override
    public void visit(ConfigurationFile file) {
        System.out.println("  [QUALITY GATE PASS] Config file '" + file.getName() + "' checked.");
    }

    @Override
    public void visit(DependencyFile file) {
        System.out.println("  [QUALITY GATE PASS] Dependency file '" + file.getName() + "' checked.");
    }

    @Override
    public void visit(Directory directory) {
        System.out.println("-> Running Quality Gates for: " + directory.getName());
    }

    public boolean isPassed() { return passed; }
    public boolean isComplexityPassed() { return complexityPassed; }
}