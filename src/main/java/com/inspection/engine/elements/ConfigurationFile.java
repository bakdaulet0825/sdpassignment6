import com.inspection.engine.ProjectElement;
import com.inspection.engine.ProjectVisitor;
import java.util.Map;

public class ConfigurationFile implements ProjectElement {
    private final String fileName;
    private final int linesOfCode;
    private final Map<String, String> properties;

    public ConfigurationFile(String fileName, int linesOfCode, Map<String, String> properties) {
        this.fileName = fileName;
        this.linesOfCode = linesOfCode;
        this.properties = properties;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public boolean hasExposedSecret() {
        return properties.values().stream().anyMatch(val ->
                val.toLowerCase().contains("secret") ||
                        val.toLowerCase().contains("password") ||
                        val.toLowerCase().contains("aws_key")
        );
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}