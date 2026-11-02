package com.inspection.engine.elements;

import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;
import java.util.List;

public class DependencyFile implements ProjectElement {
    private final String fileName;
    private final int dependencyCount;
    private final List<String> vulnerableDependencies;
    private final List<String> unversionedDependencies;

    public DependencyFile(String fileName, int dependencyCount, List<String> vulnerableDependencies, List<String> unversionedDependencies) {
        this.fileName = fileName;
        this.dependencyCount = dependencyCount;
        this.vulnerableDependencies = vulnerableDependencies;
        this.unversionedDependencies = unversionedDependencies;
    }

    public String getFileName() { return fileName; }
    public int getDependencyCount() { return dependencyCount; }
    public List<String> getVulnerableDependencies() { return vulnerableDependencies; }
    public List<String> getUnversionedDependencies() { return unversionedDependencies; }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}
