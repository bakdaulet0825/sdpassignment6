package com.inspection.engine.elements;

import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;
import java.util.List;

public class DependencyFile implements ProjectElement {
    private final String name;
    private final int size;
    private final List<String> dependencies;
    private final List<String> vulnerableDependencies;
    private final List<String> prohibitedDependencies;

    public DependencyFile(String name, int size, List<String> dependencies) {
        this.name = name;
        this.size = size;
        this.dependencies = dependencies != null ? dependencies : List.of();
        this.vulnerableDependencies = List.of();
        this.prohibitedDependencies = List.of();
    }

    public DependencyFile(String name, int size, List<String> dependencies, List<String> vulnerableOrProhibited) {
        this.name = name;
        this.size = size;
        this.dependencies = dependencies != null ? dependencies : List.of();
        this.vulnerableDependencies = vulnerableOrProhibited != null ? vulnerableOrProhibited : List.of();
        this.prohibitedDependencies = vulnerableOrProhibited != null ? vulnerableOrProhibited : List.of();
    }

    public DependencyFile(String name, int size, List<String> dependencies, List<String> vulnerableDependencies, List<String> prohibitedDependencies) {
        this.name = name;
        this.size = size;
        this.dependencies = dependencies != null ? dependencies : List.of();
        this.vulnerableDependencies = vulnerableDependencies != null ? vulnerableDependencies : List.of();
        this.prohibitedDependencies = prohibitedDependencies != null ? prohibitedDependencies : List.of();
    }

    public String getName() { return name; }
    public String getFileName() { return name; }
    public int getDependencyCount() { return dependencies.size(); }
    public List<String> getDependencies() { return dependencies; }
    public List<String> getVulnerableDependencies() { return vulnerableDependencies; }
    public List<String> getProhibitedDependencies() { return prohibitedDependencies; }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}