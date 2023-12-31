package com.triforceblitz.triforceblitz.seeds;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.util.UUID;

@Controller
@RequestMapping("/seeds")
public class SeedController {
    private final SeedRepository seedRepository;
    private final SeedService seedService;

    public SeedController(SeedRepository seedRepository, SeedService seedService) {
        this.seedRepository = seedRepository;
        this.seedService = seedService;
    }

    @GetMapping("/{id}")
    public String getSeedDetail(@PathVariable UUID id, Model model) {
        var seed = seedRepository.findSeedById(id).orElseThrow();

        model.addAttribute("seed", seed);
        return "seeds/detail";
    }

    @GetMapping("/{id}/patch" )
    public ResponseEntity<?> getSeedPatchFile(@PathVariable UUID id) throws Exception {
        var seed = seedRepository.findSeedById(id).orElseThrow();
        var patchFile = seedService.getPatchFilename(seed).orElseThrow();
        var resource = new ByteArrayResource(Files.readAllBytes(patchFile));
        var patchFilename = patchFile.getFileName().toString();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(patchFilename))
                .body(resource);
    }

    @GetMapping("/{id}/spoiler")
    public ResponseEntity<?> getSeedSpoilerFile(@PathVariable UUID id) throws Exception {
        var seed  = seedRepository.findSeedById(id).orElseThrow();
        if (!seed.isSpoilerUnlocked()) {
            throw new RuntimeException("Spoiler log is locked.");
        }
        var spoilerFile = seedService.getSpoilerFilename(seed).orElseThrow();
        var resource = new ByteArrayResource(Files.readAllBytes(spoilerFile));
        var spoilerFilename = spoilerFile.getFileName().toString();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(spoilerFilename))
                .body(resource);
    }

    private static String getContentDisposition(String filename) {
        return String.format("attachment; filename=\"%s\"", filename);
    }
}
