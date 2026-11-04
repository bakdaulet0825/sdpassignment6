package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;

public class SecurityAuditVisitor implements ProjectVisitor {
    private int securityScore = 100;
    private int criticalIssuesCount = 0;

    @Override
    public void visit(SourceCodeFile file) {
        if (file.hasHardcodedCredentials()) {
            System.out.println("  [SECURITY WARNING] Source file '" + file.getName() + "' contains hardcoded credentials!");
            criticalIssuesCount++;
            securityScore -= 15;
        } else {
            System.out.println("  [OK] Source file '" + file.getName() + "' passed security check.");
        }
    }

    @Override
    public void visit(TestFile file) {
        System.out.println("  [OK] Test file '" + file.getName() + "' is secure.");
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (!file.isSecure()) {
            System.out.println("  [SECURITY WARNING] Configuration file '" + file.getName() + "' is unsecure!");
            criticalIssuesCount++;
            securityScore -= 30;
        } else {
            System.out.println("  [OK] Configuration file '" + file.getName() + "' is secure.");
        }
    }

    @Override
    public void visit(DependencyFile file) {
        boolean hasIssues = false;
        if (file.getVulnerableDependencies() != null && !file.getVulnerableDependencies().isEmpty()) {
            hasIssues = true;
        }
        if (file.getDependencies() != null) {
            for (String dep : file.getDependencies()) {
                if (dep.contains("vulnerable") || dep.contains("gpl")) {
                    hasIssues = true;
                }
            }
        }

        if (hasIssues) {
            System.out.println("  [SECURITY WARNING] Dependency file '" + file.getName() + "' has vulnerabilities or issues.");
            criticalIssuesCount++;
            securityScore -= 20;
        } else {
            System.out.println("  [OK] Dependency file '" + file.getName() + "' has no known vulnerabilities.");
        }
    }

    @Override
    public void visit(Directory directory) {
        System.out.println("-> Scanning directory for security: " + directory.getName());
    }

    public int getSecurityScore() {
        return Math.max(0, securityScore);
    }

    public int getCriticalIssuesCount() {
        return criticalIssuesCount;
    }
}