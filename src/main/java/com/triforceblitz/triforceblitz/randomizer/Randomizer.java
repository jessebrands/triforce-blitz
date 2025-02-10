package com.triforceblitz.triforceblitz.randomizer;

import java.nio.file.Path;

public class Randomizer {
    public static final String ENTRYPOINT_FILENAME = "OoTRandomizer.py";

    private final Path path;

    public Randomizer(Path path) {
        this.path = path;
    }

    public Path getEntrypoint() {
        return path.resolve(ENTRYPOINT_FILENAME).toAbsolutePath();
    }

    public ProcessBuilder generate(String interpreter, String seed, String preset, Path settingsFile) {
        return new ProcessBuilder(
                interpreter,
                getEntrypoint().toString(),
                "--seed", seed,
                "--settings", settingsFile.toAbsolutePath().toString(),
                "--settings_preset", preset,
                "--no_log"
        );
    }
}
