package com.triforceblitz.triforceblitz.seeds;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seeds")
public class SeedController {
    private final SeedService seedService;

    public SeedController(SeedService seedService) {
        this.seedService = seedService;
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
        var seed = seedService.generateSeed();
        return "redirect:/seeds/" + seed.getId();
    }

    @GetMapping("/{id}")
    public String getSeed(@PathVariable String id, Model model) {
        model.addAttribute("seed", seedService.getById(id));
        return "seeds/seed";
    }
}
