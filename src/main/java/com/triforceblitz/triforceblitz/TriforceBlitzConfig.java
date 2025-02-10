package com.triforceblitz.triforceblitz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
@ConfigurationProperties(prefix = "triforce-blitz")
public class TriforceBlitzConfig {
    private String pythonInterpreter;
    private Path generatorPath;
    private Path seedStoragePath;
    private Path romFile;

    public String getPythonInterpreter() {
        return pythonInterpreter;
    }

    public void setPythonInterpreter(String pythonInterpreter) {
        this.pythonInterpreter = pythonInterpreter;
    }

    public Path getGeneratorPath() {
        return generatorPath;
    }

    public void setGeneratorPath(Path generatorPath) {
        this.generatorPath = generatorPath;
    }

    public Path getSeedStoragePath() {
        return seedStoragePath;
    }

    public void setSeedStoragePath(Path seedStoragePath) {
        this.seedStoragePath = seedStoragePath;
    }

    public Path getRomFile() {
        return romFile;
    }

    public void setRomFile(Path romFile) {
        this.romFile = romFile;
    }
}
