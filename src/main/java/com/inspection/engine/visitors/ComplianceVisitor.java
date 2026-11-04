package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;
import java.util.ArrayList;
import java.util.List;

public class ComplianceVisitor implements ProjectVisitor {
    private boolean compliant = true;
    private final List<String> violations = new ArrayList<>();

    @Override
    public void visit(SourceCodeFile file) {
        System.out.println("  [COMPLIANCE] Checking coding conventions for: " + file.getName());
    }

    @Override
    public void visit(TestFile file) {
        System.out.println("  [COMPLIANCE] Verifying test naming for: " + file.getName());
    }

    @Override
    public void visit(ConfigurationFile file) {
        System.out.println("  [COMPLIANCE] Verifying configuration structure for: " + file.getName());
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.getProhibitedDependencies() != null && !file.getProhibitedDependencies().isEmpty()) {
            compliant = false;
            violations.addAll(file.getProhibitedDependencies());
            System.out.println("  [COMPLIANCE WARNING] Prohibited dependencies found in " + file.getName());
        } else {
            System.out.println("  [COMPLIANCE PASS] All dependencies in " + file.getName() + " comply with standards.");
        }
    }

    @Override
    public void visit(Directory directory) {
        System.out.println("-> Checking compliance rules in: " + directory.getName());
    }

    public boolean isCompliant() { return compliant; }
    public List<String> getViolations() { return violations; }
}