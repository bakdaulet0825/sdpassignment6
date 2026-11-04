package com.inspection.engine.elements;

import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;
import java.util.ArrayList;
import java.util.List;

public class Directory implements ProjectElement {
    private final String name;
    private final List<ProjectElement> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void addElement(ProjectElement element) {
        children.add(element);
    }

    public void addChild(ProjectElement element) {
        children.add(element);
    }

    public List<ProjectElement> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
        for (ProjectElement child : children) {
            child.accept(visitor);
        }
    }
}