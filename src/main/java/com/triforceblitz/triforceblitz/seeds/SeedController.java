package com.triforceblitz.triforceblitz.seeds;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seeds")
public class SeedController {
    @GetMapping("/generate")
    public String getGeneratorForm(@ModelAttribute("form") GenerateSeedForm form,
                                   Model model) {
        return "seeds/generator_form";
    }
}
