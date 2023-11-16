package com.triforceblitz.triforceblitz.seeds;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/seeds")
public class SeedController {
    private final SeedRepository seedRepository;

    public SeedController(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    @GetMapping("/{id}")
    public String getSeedDetail(@PathVariable UUID id, Model model) {
        var seed = seedRepository.findSeedById(id).orElseThrow();

        model.addAttribute("seed", seed);
        return "seeds/detail";
    }
}
