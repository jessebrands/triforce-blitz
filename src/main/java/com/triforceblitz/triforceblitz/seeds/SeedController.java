package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.generator.GeneratorService;
import com.triforceblitz.triforceblitz.seeds.forms.GenerateSeedForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Web controller for the <code>/seeds</code> endpoint. This controller is the main entrypoint related for anything
 * related to seeds such as viewing them, downloading patch files, and of course generating them.
 *
 * @author Jesse
 */
@Controller
@RequestMapping(value = "/seeds", name = "seeds")
public class SeedController {
    private final SeedService seedService;
    private final GeneratorService generatorService;

    public SeedController(SeedService seedService, GeneratorService generatorService) {
        this.seedService = seedService;
        this.generatorService = generatorService;
    }

    @GetMapping("/generate")
    public String getGeneratorForm(@ModelAttribute("form") GenerateSeedForm form,
                                   Model model) {
        model.addAttribute("versions", generatorService.getAvailableVersions());
        return "seeds/generator_form";
    }

    @PostMapping("/generate")
    public String postGeneratorForm(@ModelAttribute("form") GenerateSeedForm form,
                                    BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "seeds/generator_form";
        }

        var seed = seedService.generateSeed(form.getVersion());
        switch (form.getUnlockMode()) {
            case ALWAYS_UNLOCKED -> seedService.unlockSpoilerLog(seed);
            case ALWAYS_LOCKED -> seedService.lockSpoilerLog(seed);
        }

        return "redirect:/seeds/" + seed.getId();
    }

    @GetMapping("/{id}")
    public String getSeed(@PathVariable UUID id, Model model) {
        model.addAttribute("seed", seedService.getSeed(id));
        return "seeds/detail";
    }
}
