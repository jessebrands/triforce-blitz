package com.triforceblitz.triforceblitz.generator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "triforce-blitz.generator")
public class GeneratorConfig {
    private Path rom;
    private Path seedsPath;
    private Path generatorsPath;
    private final List<String> blacklistedBranches = new ArrayList<>();
}
