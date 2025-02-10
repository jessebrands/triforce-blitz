package com.triforceblitz.triforceblitz.python;

import java.nio.file.Path;

public class Interpreter {
    private final Path path;

    public Interpreter(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
