package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.racetime.errors.RaceNotFoundException;
import com.triforceblitz.triforceblitz.seeds.forms.GenerateSeedForm;
import com.triforceblitz.triforceblitz.seeds.generator.GeneratorService;
import com.triforceblitz.triforceblitz.seeds.racetime.InvalidRaceException;
import com.triforceblitz.triforceblitz.seeds.racetime.JpaRacetimeLockManager;
import com.triforceblitz.triforceblitz.seeds.spoilerlog.SpoilerLogManager;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seeds/generate")
public class GeneratorController {
    private final SeedDetailsManager seedManager;
    private final GeneratorService generatorService;
    private final SpoilerLogManager spoilerLogManager;
    private final JpaRacetimeLockManager lockService;

    public GeneratorController(SeedDetailsManager seedManager,
                               GeneratorService generatorService,
                               SpoilerLogManager spoilerLogManager,
                               JpaRacetimeLockManager lockService) {
        this.seedManager = seedManager;
        this.generatorService = generatorService;
        this.spoilerLogManager = spoilerLogManager;
        this.lockService = lockService;
    }

    @GetMapping
    public String getGeneratorForm(@ModelAttribute("form") GenerateSeedForm form) {
        return "seeds/generator_form";
    }

    @PostMapping
    public String generateSeed(@Valid @ModelAttribute("form") GenerateSeedForm form,
                               BindingResult bindingResult) throws Exception {
        if (form.getUnlockMode() == UnlockMode.RACETIME && form.getRacetimeUrl() == null) {
            bindingResult.rejectValue(
                    "racetimeUrl",
                    "seeds.generator.form.racetime-url.required"
            );
        }
        if (bindingResult.hasErrors()) {
            return "seeds/generator_form";
        }

        var seed = seedManager.createSeed(true);
        // Set the spoiler log mode.
        if (form.getUnlockMode() == UnlockMode.LOCKED) {
            spoilerLogManager.lockSpoilerLog(seed.getId());
        } else if (form.getUnlockMode() == UnlockMode.RACETIME) {
            try {
                lockService.lockSpoilerLogWithRace(seed.getId(), "ootr", form.getRaceSlug());
            } catch (RaceNotFoundException e) {
                bindingResult.rejectValue(
                        "racetimeUrl",
                        "seeds.generator.form.racetime-url.not-found"
                );
            } catch (InvalidRaceException e) {
                bindingResult.rejectValue(
                        "racetimeUrl",
                        "seeds.generator.form.racetime-url.not-valid"
                );
            }
        } else {
            spoilerLogManager.unlockSpoilerLog(seed.getId());
        }
        if (bindingResult.hasErrors()) {
            seedManager.deleteSeed(seed.getId());
            return "seeds/generator_form";
        }
        generatorService.generateSeed(seed.getId());
        return "redirect:/seeds/" + seed.getId();
    }
}
