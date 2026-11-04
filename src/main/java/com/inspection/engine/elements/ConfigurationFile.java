package com.inspection.engine.elements;

import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;
import java.util.Map;

public class ConfigurationFile implements ProjectElement {
    private final String name;
    private final int linesOfCode;
    private final Map<String, String> properties;

    public ConfigurationFile(String name, int linesOfCode, Map<String, String> properties) {
        this.name = name;
        this.linesOfCode = linesOfCode;
        this.properties = properties != null ? properties : Map.of();
    }

    public String getName() { return name; }
    public String getFileName() { return name; }
    public int getLinesOfCode() { return linesOfCode; }
    public Map<String, String> getProperties() { return properties; }

    public boolean isSecure() {
        return !properties.containsKey("db_password") && !properties.containsKey("AWS_KEY");
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}