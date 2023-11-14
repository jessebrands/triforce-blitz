package com.triforceblitz.triforceblitz.python;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "triforce-blitz.python")
public class PythonConfiguration {
    private final List<String> names = new ArrayList<>();
    private final List<Path> paths = new ArrayList<>();
    private boolean includePath = true;

    public List<String> getNames() {
        return names;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public boolean getIncludeSystemPath() {
        return includePath;
    }

    public void setIncludeSystemPath(boolean includeSystemPath) {
        this.includePath = includeSystemPath;
    }
}
