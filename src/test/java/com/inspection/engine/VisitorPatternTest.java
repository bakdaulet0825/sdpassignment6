package com.inspection.engine;

import com.inspection.engine.elements.*;
import com.inspection.engine.visitors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VisitorPatternTest {

    private Directory rootDirectory;

    @BeforeEach
    void setUp() {
        rootDirectory = new Directory("root");
    }

    @Test
    @DisplayName("Test 1: Empty directory traversal")
    void testEmptyDirectory() {
        ProjectStatisticsVisitor visitor = new ProjectStatisticsVisitor();
        rootDirectory.accept(visitor);

        assertEquals(0, visitor.getTotalFiles());
        assertEquals(0, visitor.getTotalLinesOfCode());
    }

    @Test
    @DisplayName("Test 2: Source code metrics evaluation")
    void testSourceCodeFileVisitor() {
        SourceCodeFile sourceFile = new SourceCodeFile("OrderService.java", 100, 5, false);
        ProjectStatisticsVisitor visitor = new ProjectStatisticsVisitor();
        sourceFile.accept(visitor);

        assertEquals(1, visitor.getTotalFiles());
        assertEquals(100, visitor.getTotalLinesOfCode());
        assertEquals(5.0, visitor.getAverageComplexity());
    }

    @Test
    @DisplayName("Test 3: Security audit detects exposed secrets")
    void testSecurityVisitorExposedSecrets() {
        ConfigurationFile config = new ConfigurationFile("config.yml", 20, Map.of("db_password", "secret123"));
        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        config.accept(visitor);

        assertEquals(70, visitor.getSecurityScore());
        assertEquals(1, visitor.getCriticalIssuesCount());
    }

    @Test
    @DisplayName("Test 4: Security audit detects vulnerable dependencies")
    void testSecurityVisitorVulnerableDependencies() {
        DependencyFile pom = new DependencyFile("pom.xml", 5, List.of("vulnerable-lib-1.0"), List.of());
        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        pom.accept(visitor);

        assertEquals(80, visitor.getSecurityScore());
        assertEquals(1, visitor.getCriticalIssuesCount());
    }

    @Test
    @DisplayName("Test 5: Quality Gate PASS status")
    void testQualityGatePass() {
        Directory src = new Directory("src");
        src.addElement(new SourceCodeFile("App.java", 50, 4, false));
        src.addElement(new TestFile("AppTest.java", 40, 2, "JUnit5"));

        QualityGateVisitor qualityGate = new QualityGateVisitor();
        src.accept(qualityGate);

        assertTrue(qualityGate.isPassed());
    }

    @Test
    @DisplayName("Test 6: Quality Gate FAIL due to high complexity")
    void testQualityGateFailComplexity() {
        SourceCodeFile complexFile = new SourceCodeFile("ComplexService.java", 500, 25, false);
        QualityGateVisitor qualityGate = new QualityGateVisitor();
        complexFile.accept(qualityGate);

        assertFalse(qualityGate.isComplexityPassed());
        assertFalse(qualityGate.isPassed());
    }

    @Test
    @DisplayName("Test 7: Compliance Visitor detects unversioned dependency")
    void testComplianceUnversionedDependency() {
        DependencyFile buildGradle = new DependencyFile("build.gradle", 3, List.of(), List.of("org.springframework:spring-core"));
        ComplianceVisitor visitor = new ComplianceVisitor();
        buildGradle.accept(visitor);

        assertFalse(visitor.isCompliant());
        assertEquals(1, visitor.getViolations().size());
    }

    @Test
    @DisplayName("Test 8: Deeply nested directory hierarchy traversal")
    void testDeeplyNestedDirectories() {
        Directory dirLevel1 = new Directory("level1");
        Directory dirLevel2 = new Directory("level2");
        dirLevel1.addElement(dirLevel2);
        dirLevel2.addElement(new SourceCodeFile("Deep.java", 80, 2, false));

        ProjectStatisticsVisitor visitor = new ProjectStatisticsVisitor();
        dirLevel1.accept(visitor);

        assertEquals(1, visitor.getTotalFiles());
        assertEquals(80, visitor.getTotalLinesOfCode());
    }

    @Test
    @DisplayName("Test 9: License Audit custom visitor detection")
    void testLicenseAuditVisitor() {
        DependencyFile depFile = new DependencyFile("pom.xml", 2, List.of("gpl-library-v2"), List.of());
        LicenseAuditVisitor visitor = new LicenseAuditVisitor();
        depFile.accept(visitor);

        assertEquals(1, visitor.getLicenseWarnings().size());
    }

    @Test
    @DisplayName("Test 10: Test-to-Source ratio calculation")
    void testTestToSourceRatio() {
        Directory project = new Directory("project");
        project.addElement(new SourceCodeFile("Main.java", 100, 2, false));
        project.addElement(new TestFile("MainTest.java", 50, 1, "JUnit"));

        ProjectStatisticsVisitor visitor = new ProjectStatisticsVisitor();
        project.accept(visitor);

        assertEquals(1.0, visitor.getTestToSourceRatio());
    }

    @Test
    @DisplayName("Test 11: Edge Case - Multiple issues across mixed elements")
    void testMixedElementsAccumulation() {
        rootDirectory.addElement(new SourceCodeFile("Bad.java", 300, 20, true));
        rootDirectory.addElement(new ConfigurationFile("app.env", 10, Map.of("AWS_KEY", "secret")));

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        rootDirectory.accept(visitor);

        assertEquals(55, visitor.getSecurityScore());
        assertEquals(2, visitor.getCriticalIssuesCount());
    }

    @Test
    @DisplayName("Test 12: Edge Case - Dependency file without vulnerabilities")
    void testCleanDependencyFile() {
        DependencyFile cleanDep = new DependencyFile("pom.xml", 10, List.of(), List.of());
        SecurityAuditVisitor securityVisitor = new SecurityAuditVisitor();
        ComplianceVisitor complianceVisitor = new ComplianceVisitor();

        cleanDep.accept(securityVisitor);
        cleanDep.accept(complianceVisitor);

        assertEquals(100, securityVisitor.getSecurityScore());
        assertTrue(complianceVisitor.isCompliant());
    }
}
