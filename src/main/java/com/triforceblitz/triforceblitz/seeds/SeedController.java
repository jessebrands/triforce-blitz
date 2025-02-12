package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.seeds.racetime.RacetimeLockService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Controller
@RequestMapping("/seeds")
public class SeedController {
    private final SeedService seedService;
    private final RacetimeLockService lockService;

    public SeedController(SeedService seedService,
                          RacetimeLockService lockService) {
        this.seedService = seedService;
        this.lockService = lockService;
    }

    @GetMapping("/generate")
    public String getGeneratorForm(@ModelAttribute("form") GenerateSeedForm form,
                                   Model model) {
        return "seeds/generator_form";
    }

    @PostMapping("/generate")
    public String generateSeed(@ModelAttribute("form") GenerateSeedForm form,
                               BindingResult bindingResult,
                               Model model) {
        if (form.getUnlockMode() == UnlockMode.RACETIME && form.getRacetimeUrl() == null) {
            bindingResult.rejectValue("racetimeUrl", "seeds.generator.form.racetime-url.empty");
            return "seeds/generator_form";
        }

        var seed = seedService.createSeed(form.isCooperative());
        // Set the spoiler log mode.
        if (form.getUnlockMode() == UnlockMode.LOCKED) {
            seedService.lockSpoilerLog(seed.getId());
        } else if (form.getUnlockMode() == UnlockMode.RACETIME) {
            lockService.lockSpoilerLog(seed.getId(), "ootr", form.getRaceSlug());
        } else {
            seedService.unlockSpoilerLog(seed.getId());
        }
        return "redirect:/seeds/" + seed.getId();
    }

    @GetMapping("/{id}")
    public String getSeed(@PathVariable UUID id, Model model) {
        model.addAttribute("seed", seedService.getById(id).orElse(null));
        return "seeds/seed";
    }

    @GetMapping("/{id}/patch")
    public ResponseEntity<Resource> getPatchFile(@PathVariable UUID id) throws IOException {
        var patchFilename = seedService.getPatchFilename(id);
        var resource = new ByteArrayResource(Files.readAllBytes(patchFilename));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/{id}/spoiler")
    public ResponseEntity<Resource> getSpoilerLog(@PathVariable UUID id) throws IOException {
        var spoilerFilename = seedService.getSpoilerLogFilename(id);
        var resource = new ByteArrayResource(Files.readAllBytes(spoilerFilename));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }
}
