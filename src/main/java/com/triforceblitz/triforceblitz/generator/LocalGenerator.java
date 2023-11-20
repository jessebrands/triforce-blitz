package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.python.PythonInterpreter;
import lombok.Getter;

import java.nio.file.Path;
import java.util.Objects;

@Getter
public class LocalGenerator implements Generator {
    private final Path path;

    public LocalGenerator(Path path) {
        this.path = Objects.requireNonNull(path);
    }

    private Path getEntrypointPath() {
        return path.resolve("OoTRandomizer.py");
    }

    @Override
    public Process generateSeed(PythonInterpreter interpreter, Path settingsFile, String preset) throws Exception {
        return interpreter.command(
                getEntrypointPath().toAbsolutePath().toString(),
                "--settings", settingsFile.toAbsolutePath().toString(),
                "--settings_preset", preset
        );
    }
}
