package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;
import java.util.ArrayList;
import java.util.List;

public class LicenseAuditVisitor implements ProjectVisitor {
    private final List<String> licenseWarnings = new ArrayList<>();

    public List<String> getLicenseWarnings() { return licenseWarnings; }

    @Override
    public void visit(SourceCodeFile file) { }

    @Override
    public void visit(TestFile file) { }

    @Override
    public void visit(ConfigurationFile file) { }

    @Override
    public void visit(DependencyFile file) {
        for (String dep : file.getVulnerableDependencies()) {
            if (dep.toLowerCase().contains("gpl")) {
                licenseWarnings.add("Restricted GPL license dependency detected: " + dep);
            }
        }
    }

    @Override
    public void visit(Directory directory) { }
}
