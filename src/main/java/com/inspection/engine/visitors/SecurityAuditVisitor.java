package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;

public class SecurityAuditVisitor implements ProjectVisitor {
    private int securityScore = 100;
    private int criticalIssuesCount = 0;

    public int getSecurityScore() { return Math.max(0, securityScore); }
    public int getCriticalIssuesCount() { return criticalIssuesCount; }

    @Override
    public void visit(SourceCodeFile file) {
        if (file.isHighRiskFlag()) {
            securityScore -= 15;
            criticalIssuesCount++;
        }
    }

    @Override
    public void visit(TestFile file) {
        // Test files present negligible security risk
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.hasExposedSecret()) {
            securityScore -= 30;
            criticalIssuesCount++;
        }
    }

    @Override
    public void visit(DependencyFile file) {
        int vulns = file.getVulnerableDependencies().size();
        securityScore -= (vulns * 20);
        criticalIssuesCount += vulns;
    }

    @Override
    public void visit(Directory directory) {
        // No security penalty for directory containers
    }
}
