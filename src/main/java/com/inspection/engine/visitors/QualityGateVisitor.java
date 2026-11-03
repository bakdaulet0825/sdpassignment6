package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;

public class QualityGateVisitor implements ProjectVisitor {
    private final SecurityAuditVisitor securityVisitor = new SecurityAuditVisitor();
    private final ProjectStatisticsVisitor statsVisitor = new ProjectStatisticsVisitor();

    @Override
    public void visit(SourceCodeFile file) {
        securityVisitor.visit(file);
        statsVisitor.visit(file);
    }

    @Override
    public void visit(TestFile file) {
        securityVisitor.visit(file);
        statsVisitor.visit(file);
    }

    @Override
    public void visit(ConfigurationFile file) {
        securityVisitor.visit(file);
        statsVisitor.visit(file);
    }

    @Override
    public void visit(DependencyFile file) {
        securityVisitor.visit(file);
        statsVisitor.visit(file);
    }

    @Override
    public void visit(Directory directory) {
        securityVisitor.visit(directory);
        statsVisitor.visit(directory);
    }

    public boolean isComplexityPassed() {
        return statsVisitor.getAverageComplexity() <= 10.0;
    }

    public boolean isTestRatioPassed() {
        return statsVisitor.getTestToSourceRatio() >= 0.40;
    }

    public boolean isSecurityPassed() {
        return securityVisitor.getCriticalIssuesCount() == 0;
    }

    public boolean isPassed() {
        return isComplexityPassed() && isTestRatioPassed() && isSecurityPassed();
    }

    public String generateReport() {
        return String.format(
                "Quality Gate Result:\n" +
                        "Security: %s (Critical Issues: %d)\n" +
                        "Complexity: %s (Avg: %.2f)\n" +
                        "Tests: %s (Ratio: %.2f)\n" +
                        "FINAL RESULT: %s",
                isSecurityPassed() ? "PASS" : "FAIL", securityVisitor.getCriticalIssuesCount(),
                isComplexityPassed() ? "PASS" : "FAIL", statsVisitor.getAverageComplexity(),
                isTestRatioPassed() ? "PASS" : "FAIL", statsVisitor.getTestToSourceRatio(),
                isPassed() ? "PASSED" : "FAILED"
        );
    }
}
