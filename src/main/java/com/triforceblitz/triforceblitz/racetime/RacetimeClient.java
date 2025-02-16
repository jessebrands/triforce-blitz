package com.triforceblitz.triforceblitz.racetime;

import com.triforceblitz.triforceblitz.racetime.race.Race;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface RacetimeClient {
    @GetExchange("/{category}/{slug}/data")
    Race getRace(@PathVariable String category, @PathVariable String slug);
}
