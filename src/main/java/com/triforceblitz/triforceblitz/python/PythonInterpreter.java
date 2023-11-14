package com.triforceblitz.triforceblitz.python;

public interface PythonInterpreter {
    String getVersion() throws Exception;
    ProcessBuilder processBuilder(String... args);
    Process command(String... args) throws Exception;
}
