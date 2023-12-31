package com.triforceblitz.triforceblitz.generator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.nio.file.Path;

@Data
public class GeneratorSettings {
    @JsonProperty("seed")
    private String seed;

    @JsonProperty("output_file")
    private String outputName;

    @JsonProperty("rom")
    private String romFilename;

    @JsonProperty("output_dir")
    private String outputPath;

    @JsonProperty("create_spoiler")
    private boolean createSpoilerLog = true;

    @JsonProperty("create_patch_file")
    private boolean createPatchFile = true;

    @JsonProperty("create_cosmetics_log")
    private boolean createCosmeticsLog = false;

    @JsonProperty("create_compressed_rom")
    private boolean createCompressedRom = false;

    @JsonProperty("show_seed_info")
    private boolean showSeedInfo = true;

    @JsonProperty("user_message")
    private String userMessage = "Generated by triforceblitz.com";

    // This is for older versions of the randomizer, which used different settings.
    @JsonProperty("compress_rom")
    private String compressionType = "Patch";

    public GeneratorSettings(Path romFilename, Path outputPath, String seed) {
        this.seed = seed;
        this.outputName = seed;
        this.romFilename = romFilename.toAbsolutePath().toString();
        this.outputPath = outputPath.toAbsolutePath().toString();
    }

    @JsonIgnore
    public String getSpoilerLogFilename() {
        return outputName + "_Spoiler.json";
    }

    @JsonIgnore
    public Path getSpoilerLogPath() {
        return Path.of(outputPath).resolve(getSpoilerLogFilename());
    }

    @Deprecated
    public String getCompressionType() {
        return compressionType;
    }

    @Deprecated
    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }
}
