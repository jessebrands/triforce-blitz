package com.triforceblitz.triforceblitz.seeds;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seeds/featured")
public class FeaturedSeedsController {
    private final FeaturedSeedService featuredSeedService;

    public FeaturedSeedsController(FeaturedSeedService featuredSeedService) {
        this.featuredSeedService = featuredSeedService;
    }

    @GetMapping("/featured")
    public String featuredSeeds(Model model) {
        model.addAttribute("dailySeed", featuredSeedService.getDailySeed());
        model.addAttribute("weeklySeed", featuredSeedService.getWeeklySeed());
        return "seeds/featured_seeds";
    }
}
