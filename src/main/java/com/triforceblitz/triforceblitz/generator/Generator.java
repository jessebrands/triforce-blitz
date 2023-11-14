package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.python.PythonInterpreter;

import java.nio.file.Path;

public interface Generator {
    Process generateSeed(PythonInterpreter interpreter, Path settingsFile, String preset) throws Exception;
}
