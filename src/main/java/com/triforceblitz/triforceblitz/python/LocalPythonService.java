package com.triforceblitz.triforceblitz.python;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPythonService implements PythonService {
    private static final String[] executableNames = {
            "python3",
            "python",
            "python.exe",
    };

    @Override
    public Interpreter findInterpreter() {
        for (var path : System.getenv("PATH").split(File.pathSeparator)) {
            for (var executable : executableNames) {
                var filename = Path.of(path, executable);
                if (Files.isExecutable(filename)) {
                    return new Interpreter(filename);
                }
            }
        }
        return null;
    }
}
