package com.inspection.engine;

public interface ProjectElement {
    void accept(ProjectVisitor visitor);
}
