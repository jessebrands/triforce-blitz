package com.triforceblitz.triforceblitz.randomizer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Path;

public class RandomizerSettings {
    public static final String FILENAME = "TriforceBlitz_Settings.json";

    @JsonProperty("rom")
    private Path romFilename;

    @JsonProperty("output_dir")
    private Path outputDirectory;

    @JsonProperty("output_file")
    private String outputPrefix = "TriforceBlitz";

    @JsonProperty("create_patch_file")
    private boolean createPatch = true;

    @JsonProperty("create_compressed_rom")
    private boolean createCompressedRom = false;

    @JsonProperty("create_cosmetics_log")
    private boolean createCosmeticsLog = false;

    @JsonProperty("compress_rom")
    private String outputType;

    public RandomizerSettings(Path romFilename, Path outputDirectory) {
        this.romFilename = romFilename;
        this.outputDirectory = outputDirectory;
    }

    public String getRomFilename() {
        return romFilename.toAbsolutePath().toString();
    }

    public void setRomFilename(Path romFilename) {
        this.romFilename = romFilename;
    }

    public String getOutputDirectory() {
        return outputDirectory.toAbsolutePath().toString();
    }

    public void setOutputDirectory(Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getOutputPrefix() {
        return outputPrefix;
    }

    public void setOutputPrefix(String outputPrefix) {
        this.outputPrefix = outputPrefix;
    }

    public boolean shouldCreatePatch() {
        return createPatch;
    }

    public void setCreatePatch(boolean createPatch) {
        this.createPatch = createPatch;
    }

    public boolean shouldCreateCompressedRom() {
        return createCompressedRom;
    }

    public void setCreateCompressedRom(boolean createCompressedRom) {
        this.createCompressedRom = createCompressedRom;
    }

    public boolean shouldCreateCosmeticsLog() {
        return createCosmeticsLog;
    }

    public void setCreateCosmeticsLog(boolean createCosmeticsLog) {
        this.createCosmeticsLog = createCosmeticsLog;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }
}
