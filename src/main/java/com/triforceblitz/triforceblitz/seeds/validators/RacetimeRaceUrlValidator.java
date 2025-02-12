package com.triforceblitz.triforceblitz.seeds.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

import static com.triforceblitz.triforceblitz.racetime.race.Race.VALID_PATTERN;

@Component
public class RacetimeRaceUrlValidator implements ConstraintValidator<RacetimeRaceUrl, URL> {
    private List<String> categories;

    @Override
    public void initialize(RacetimeRaceUrl constraint) {
        this.categories = List.of(constraint.categories());
    }

    @Override
    public boolean isValid(@Nullable URL url, ConstraintValidatorContext context) {
        if (url == null) {
            return true;
        }
        var matcher = VALID_PATTERN.matcher(url.toString());
        if (!matcher.matches()) {
            return false;
        }
        if (!categories.isEmpty()) {
            var category = matcher.group(1);
            return categories.contains(category);
        }
        return true;
    }
}
