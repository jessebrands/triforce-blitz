package com.triforceblitz.triforceblitz.python;

import java.util.Optional;

public interface PythonService {
    Optional<PythonInterpreter> findInterpreter() throws Exception;

    Optional<PythonInterpreter> findInterpreter(String... names) throws Exception;
}
