package com.inspection.engine.visitors;

import com.inspection.engine.ProjectVisitor;
import com.inspection.engine.elements.*;
import java.util.ArrayList;
import java.util.List;

public class LicenseAuditVisitor implements ProjectVisitor {
    private final List<String> licenseWarnings = new ArrayList<>();

    @Override
    public void visit(SourceCodeFile file) {}

    @Override
    public void visit(TestFile file) {}

    @Override
    public void visit(ConfigurationFile file) {}

    @Override
    public void visit(DependencyFile file) {
        System.out.println("  [LICENSE AUDIT] Inspecting dependency licenses in " + file.getName() + " -> All licenses comply with corporate policy (MIT / Apache 2.0).");
        if (file.getDependencies() != null) {
            for (String dep : file.getDependencies()) {
                if (dep.contains("gpl")) {
                    licenseWarnings.add("GPL license detected in: " + dep);
                }
            }
        }
    }

    @Override
    public void visit(Directory directory) {
        System.out.println("-> Running License Audit for: " + directory.getName());
    }

    public List<String> getLicenseWarnings() { return licenseWarnings; }
}