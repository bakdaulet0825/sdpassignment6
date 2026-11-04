package com.inspection.engine;

import com.inspection.engine.elements.ConfigurationFile;
import com.inspection.engine.elements.DependencyFile;
import com.inspection.engine.elements.Directory;
import com.inspection.engine.elements.SourceCodeFile;
import com.inspection.engine.elements.TestFile;

public interface ProjectVisitor {
    void visit(SourceCodeFile file);
    void visit(TestFile file);
    void visit(ConfigurationFile file);
    void visit(DependencyFile file);
    void visit(Directory directory);
}