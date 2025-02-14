package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.seeds.generator.GeneratedSeed;
import com.triforceblitz.triforceblitz.seeds.generator.GeneratedSeedService;
import com.triforceblitz.triforceblitz.seeds.racetime.RacetimeLockDetails;
import com.triforceblitz.triforceblitz.seeds.racetime.RacetimeLockDetailsService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Controller
@RequestMapping("/seeds/{id}")
public class SeedController {
    private final SeedDetailsManager seedManager;
    private final GeneratedSeedService generatedSeedService;
    private final RacetimeLockDetailsService racetimeLockService;

    public SeedController(SeedDetailsManager seedManager,
                          GeneratedSeedService generatedSeedService,
                          RacetimeLockDetailsService racetimeLockService) {
        this.seedManager = seedManager;
        this.generatedSeedService = generatedSeedService;
        this.racetimeLockService = racetimeLockService;
    }

    @ModelAttribute("seed")
    private SeedDetails getSeed(@PathVariable UUID id) throws SeedNotFoundException {
        return seedManager.loadSeedById(id);
    }

    @ModelAttribute("generatedSeed")
    private GeneratedSeed getGeneratedSeed(@PathVariable UUID id) {
        try {
            return generatedSeedService.loadGeneratedSeedBySeedId(id);
        } catch (SeedNotGeneratedException e) {
            return null;
        }
    }

    @ModelAttribute("racetimeLock")
    private RacetimeLockDetails getRacetimeLock(@PathVariable UUID id) {
        try {
            return racetimeLockService.findRacetimeLockBySeedId(id);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping
    public String getSeed() {
        return "seeds/seed";
    }

    @GetMapping("/patch")
    public ResponseEntity<Resource> getPatchFile(@ModelAttribute("generatedSeed") GeneratedSeed generatedSeed)
            throws SeedNotGeneratedException, IOException {
        if (generatedSeed == null) {
            throw new SeedNotGeneratedException("seed is not generated");
        }

        var patchFilename = generatedSeed.patchFilename();
        var resource = new ByteArrayResource(Files.readAllBytes(patchFilename));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/spoiler")
    public ResponseEntity<Resource> getSpoilerLog(@ModelAttribute("generatedSeed") GeneratedSeed generatedSeed)
            throws SeedNotGeneratedException, IOException {
        if (generatedSeed == null) {
            throw new SeedNotGeneratedException("seed is not generated");
        }

        var spoilerFilename = generatedSeed.spoilerLogFilename();
        var resource = new ByteArrayResource(Files.readAllBytes(spoilerFilename));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }
}
