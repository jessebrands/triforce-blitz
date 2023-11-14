package com.triforceblitz.triforceblitz.python;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class LocalPythonInterpreter implements PythonInterpreter {
    private final Path path;

    public LocalPythonInterpreter(Path path) {
        this.path = Objects.requireNonNull(path);
    }

    private ProcessBuilder processBuilder() {
        return new ProcessBuilder(path.toAbsolutePath().toString());
    }

    @Override
    public String getVersion() throws Exception {
        var process = command("--version");
        var result = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        return result.replaceFirst("^Python ", "");
    }

    @Override
    public ProcessBuilder processBuilder(String... args) {
        var pb = processBuilder();
        pb.command().addAll(Arrays.asList(args));
        return pb;
    }

    @Override
    public Process command(String... args) throws Exception {
        return processBuilder(args).start();
    }
}
