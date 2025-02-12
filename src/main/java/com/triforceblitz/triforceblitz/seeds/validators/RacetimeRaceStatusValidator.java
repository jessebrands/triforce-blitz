package com.triforceblitz.triforceblitz.seeds.validators;

import com.triforceblitz.triforceblitz.racetime.RacetimeService;
import com.triforceblitz.triforceblitz.racetime.race.RaceStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Set;

import static com.triforceblitz.triforceblitz.racetime.race.Race.VALID_PATTERN;

@Component
public class RacetimeRaceStatusValidator implements ConstraintValidator<RacetimeRaceStatus, URL> {
    private final RacetimeService racetimeService;

    private Set<RaceStatus> statuses;

    public RacetimeRaceStatusValidator(RacetimeService racetimeService) {
        this.racetimeService = racetimeService;
    }

    @Override
    public void initialize(RacetimeRaceStatus constraintAnnotation) {
        statuses = Set.of(constraintAnnotation.statuses());
    }

    @Override
    public boolean isValid(URL url, ConstraintValidatorContext context) {
        if (url == null) {
            return true;
        }
        var matcher = VALID_PATTERN.matcher(url.toString());
        if (!matcher.matches()) {
            return false;
        }
        var category = matcher.group(1);
        var slug = matcher.group(2);
        var response = racetimeService.getRace(category, slug);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return false;
        }
        var race = response.getBody();
        if (race == null) {
            return false;
        }
        return statuses.contains(race.getStatus());
    }
}
