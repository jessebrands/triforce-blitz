package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.generator.forms.GeneratorForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/generator")
public class GeneratorController {
    private final GeneratorService generatorService;

    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping
    public String getGeneratorForm(@ModelAttribute("form") GeneratorForm form) {
        return "generator/generator_form";
    }

    @PostMapping("/generate")
    public String generateSeed(Model model, @ModelAttribute("form") GeneratorForm form) throws Exception {
        generatorService.generateSeed(form.getVersion(), UUID.randomUUID().toString());
        return "generator/generator_form";
    }
}
