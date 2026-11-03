package com.inspection.engine;

import com.inspection.engine.elements.*;
import com.inspection.engine.visitors.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Construct target fintech structure
        Directory root = new Directory("fintech-project");

        Directory srcDir = new Directory("src");
        srcDir.addElement(new SourceCodeFile("PaymentService.java", 250, 14, true));
        srcDir.addElement(new SourceCodeFile("UserService.java", 120, 4, false));

        Directory testsDir = new Directory("tests");
        testsDir.addElement(new TestFile("PaymentServiceTest.java", 180, 12, "JUnit 5"));

        Directory configDir = new Directory("config");
        configDir.addElement(new ConfigurationFile("application.properties", 45, Map.of("AWS_SECRET_KEY", "xxxx")));

        root.addElement(srcDir);
        root.addElement(testsDir);
        root.addElement(configDir);
        root.addElement(new DependencyFile("pom.xml", 8, List.of("log4j-1.2.17"), List.of()));

        // Run Visitors
        SecurityAuditVisitor securityVisitor = new SecurityAuditVisitor();
        ProjectStatisticsVisitor statsVisitor = new ProjectStatisticsVisitor();
        QualityGateVisitor qualityGateVisitor = new QualityGateVisitor();
        ComplianceVisitor complianceVisitor = new ComplianceVisitor();
        LicenseAuditVisitor licenseVisitor = new LicenseAuditVisitor();

        root.accept(securityVisitor);
        root.accept(statsVisitor);
        root.accept(qualityGateVisitor);
        root.accept(complianceVisitor);
        root.accept(licenseVisitor);

        // Display Reports
        System.out.println("=== SECURITY AUDIT ===");
        System.out.println("Security Score: " + securityVisitor.getSecurityScore());
        System.out.println("Critical Issues: " + securityVisitor.getCriticalIssuesCount());

        System.out.println("\n=== PROJECT STATISTICS ===");
        System.out.println("Total Files: " + statsVisitor.getTotalFiles());
        System.out.println("Total Lines of Code: " + statsVisitor.getTotalLinesOfCode());
        System.out.println("Avg Complexity: " + statsVisitor.getAverageComplexity());
        System.out.println("Test/Source Ratio: " + statsVisitor.getTestToSourceRatio());

        System.out.println("\n=== QUALITY GATE ===");
        System.out.println(qualityGateVisitor.generateReport());

        System.out.println("\n=== COMPLIANCE VIOLATIONS ===");
        complianceVisitor.getViolations().forEach(v -> System.out.println("- " + v));
    }
}