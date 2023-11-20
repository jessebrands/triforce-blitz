package com.triforceblitz.triforceblitz.python;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "triforce-blitz.python")
public class PythonConfiguration {
    private final List<String> names = new ArrayList<>();
    private final List<Path> paths = new ArrayList<>();
    private boolean includePath = true;
}
