package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.seeds.SeedNotFoundException;
import com.triforceblitz.triforceblitz.seeds.SeedRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LocalGeneratorService implements GeneratorService {
    private final SeedRepository seedRepository;
    private final GenerateSeedTaskFactory taskFactory;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LocalGeneratorService(SeedRepository seedRepository,
                                 GenerateSeedTaskFactory taskFactory) {
        this.seedRepository = seedRepository;
        this.taskFactory = taskFactory;
    }

    @Override
    public void generateSeed(UUID seedId) throws SeedNotFoundException {
        var seed = seedRepository.findById(seedId)
                .orElseThrow(() -> new SeedNotFoundException("not found"));
        executor.submit(taskFactory.create(seed));
    }
}
