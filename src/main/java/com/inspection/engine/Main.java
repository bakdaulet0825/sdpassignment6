package com.inspection.engine;

import com.inspection.engine.elements.*;
import com.inspection.engine.visitors.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Directory root = new Directory("fintech-project");

        Directory src = new Directory("src");
        src.addChild(new SourceCodeFile("PaymentService.java", 120, 8, true));
        src.addChild(new SourceCodeFile("UserService.java", 80, 5, false));
        root.addChild(src);

        Directory tests = new Directory("tests");
        tests.addChild(new TestFile("PaymentServiceTest.java", 45, 6, "JUnit5"));
        root.addChild(tests);

        Directory config = new Directory("config");
        config.addChild(new ConfigurationFile("application.properties", 25, Map.of()));
        root.addChild(config);

        root.addChild(new DependencyFile("pom.xml", 1, List.of("org.springframework:6.0.0")));

        System.out.println("=== 1. SECURITY AUDIT VISITOR ===");
        SecurityAuditVisitor securityVisitor = new SecurityAuditVisitor();
        root.accept(securityVisitor);

        System.out.println("\n=== 2. PROJECT STATISTICS VISITOR ===");
        ProjectStatisticsVisitor statsVisitor = new ProjectStatisticsVisitor();
        root.accept(statsVisitor);
        statsVisitor.printSummary();

        System.out.println("\n=== 3. QUALITY GATE VISITOR ===");
        QualityGateVisitor qualityVisitor = new QualityGateVisitor();
        root.accept(qualityVisitor);

        System.out.println("\n=== QUALITY GATE SUMMARY ===");
        if (qualityVisitor.isPassed()) {
            System.out.println("FINAL RESULT: PASS");
        } else {
            System.out.println("FINAL RESULT: FAILED");
        }
        System.out.println("Complexity: " +
                (qualityVisitor.isComplexityPassed() ? "PASS" : "FAIL"));

        System.out.println("\n=== 4. COMPLIANCE VISITOR ===");
        ComplianceVisitor complianceVisitor = new ComplianceVisitor();
        root.accept(complianceVisitor);

        System.out.println("\n=== 5. LICENSE AUDIT VISITOR ===");
        LicenseAuditVisitor licenseVisitor = new LicenseAuditVisitor();
        root.accept(licenseVisitor);

        System.out.println("\nFintech project inspection completed successfully!");
    }
}