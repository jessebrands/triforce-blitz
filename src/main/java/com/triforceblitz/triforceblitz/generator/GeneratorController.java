package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.generator.forms.GeneratorForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.UUID;

@Controller
@RequestMapping("/generator")
public class GeneratorController {
    private final GeneratorService generatorService;

    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping
    public String getGeneratorForm(Model model, @ModelAttribute("form") GeneratorForm form) {
        var availableVersions = generatorService.getAvailableVersions();
        var defaultVersion = availableVersions.stream().findFirst().orElseThrow();
        var seasons = generatorService.getCompatibleSeasons(defaultVersion);
        var defaultSeason = seasons.stream()
                .max(Comparator.naturalOrder())
                .orElse(null);

        // Set the form values.
        form.setVersion(defaultVersion);
        form.setSeason(defaultSeason);

        // Update the model
        model.addAttribute("versions", availableVersions);
        model.addAttribute("seasons", seasons);
        return "generator/generate";
    }

    @PostMapping("/generate")
    public String generateSeed(@ModelAttribute("form") GeneratorForm form) throws Exception {
        var generatorSeed = UUID.randomUUID().toString();
        var seed = generatorService.generateSeed(form.getVersion(), form.getSeason(), generatorSeed);
        return "redirect:/seeds/" + seed.getId();
    }

    @GetMapping("/options")
    public String getGeneratorOptions(@RequestParam Version version, Model model) {
        var seasons = generatorService.getCompatibleSeasons(version);
        var defaultSeason = seasons.stream()
                .max(Comparator.naturalOrder())
                .orElse(null);

        // Update the model
        model.addAttribute("seasons", !seasons.isEmpty() ? seasons : null);
        model.addAttribute("activeSeason", defaultSeason);
        return "generator/fragments/options";
    }
}
