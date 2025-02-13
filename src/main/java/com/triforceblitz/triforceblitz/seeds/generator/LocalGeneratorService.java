package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LocalGeneratorService implements GeneratorService {
    private final GenerateSeedTaskFactory taskFactory;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LocalGeneratorService(GenerateSeedTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    @Override
    public void generateSeed(Seed seed) {
        executor.submit(taskFactory.create(seed));
    }
}
