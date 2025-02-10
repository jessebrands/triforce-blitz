package com.triforceblitz.triforceblitz.python;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {
    private final Path path;

    public Interpreter(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public ProcessBuilder processBuilder(String entrypoint, String... args) {
        var command = new ArrayList<String>();
        command.add(path.toString());
        command.add(entrypoint);
        command.addAll(List.of(args));
        return new ProcessBuilder(command);
    }
}
