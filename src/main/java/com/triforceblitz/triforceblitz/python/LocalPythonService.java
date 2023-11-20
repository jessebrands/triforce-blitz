package com.triforceblitz.triforceblitz.python;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LocalPythonService implements PythonService {
    private final static Logger logger = LoggerFactory.getLogger(LocalPythonService.class);

    private final PythonConfiguration config;

    public LocalPythonService(PythonConfiguration config) {
        this.config = config;
    }

    private List<Path> getSystemPath() {
        return Arrays.stream(System.getenv("PATH").split(File.pathSeparator))
                .map(Path::of)
                .toList();
    }

    @Override
    @Cacheable("interpreters")
    public Optional<PythonInterpreter> findInterpreter() {
        return findInterpreter(config.getNames().toArray(new String[0]));
    }

    @Override
    @Cacheable(value = "interpreters", key = "#names")
    public Optional<PythonInterpreter> findInterpreter(String... names) {
        var searchPaths = new ArrayList<>(config.getPaths());
        if (config.isIncludePath()) {
            searchPaths.addAll(getSystemPath());
        }

        for (var path : searchPaths) {
            for (var name : names) {
                var p = path.resolve(name);
                if (Files.exists(p) && Files.isExecutable(p)) {
                    logger.info("Found Python interpreter at {}", p);
                    return Optional.of(new LocalPythonInterpreter(p));
                }
            }
        }

        return Optional.empty();
    }
}
