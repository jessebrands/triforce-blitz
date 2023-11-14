package com.triforceblitz.triforceblitz.generator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
@ConfigurationProperties(prefix = "triforce-blitz.generator")
public class GeneratorConfig {
    private Path rom;
    private Path seedsPath;
    private Path generatorsPath;

    public Path getRom() {
        return rom;
    }

    public void setRom(Path rom) {
        this.rom = rom;
    }

    public Path getSeedsPath() {
        return seedsPath;
    }

    public void setSeedsPath(Path seedsPath) {
        this.seedsPath = seedsPath;
    }

    public Path getGeneratorsPath() {
        return generatorsPath;
    }

    public void setGeneratorsPath(Path generatorsPath) {
        this.generatorsPath = generatorsPath;
    }
}
