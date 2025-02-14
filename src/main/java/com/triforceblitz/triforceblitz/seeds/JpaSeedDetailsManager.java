package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JpaSeedDetailsManager implements SeedDetailsManager {
    private final TriforceBlitzConfig config;
    private final SeedRepository seedRepository;

    public JpaSeedDetailsManager(TriforceBlitzConfig config,
                                 SeedRepository seedRepository) {
        this.config = config;
        this.seedRepository = seedRepository;
    }

    @Override
    public SeedDetails loadSeedById(UUID id) throws SeedNotFoundException {
        return seedRepository.findById(id)
                .map(JpaSeedDetails::new)
                .orElseThrow(() -> new SeedNotFoundException("not found"));
    }

    @Override
    public SeedDetails createSeed(boolean cooperative) {
        var preset = config.getRandomizerPreset();
        var seed = new Seed(
                UUID.randomUUID().toString(),
                preset,
                config.getRandomizerVersion(),
                cooperative
        );
        seedRepository.save(seed);
        return new JpaSeedDetails(seed);
    }

    @Override
    public void deleteSeed(UUID id) {
        seedRepository.deleteById(id);
    }

    @Override
    public boolean seedExists(UUID id) {
        return seedRepository.existsById(id);
    }
}
