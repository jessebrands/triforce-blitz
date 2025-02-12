package com.triforceblitz.triforceblitz.seeds.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validates that a Racetime race URL is valid and belongs to one of the
 * categories, if specified. This does not check if the race room actually
 * exists.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RacetimeRaceUrlValidator.class)
public @interface RacetimeRaceUrl {
    String message() default "{seeds.validation.constraints.race-url.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

    /// List of Racetime.gg categories. The URL must match one of them.
    /// Will be ignored if empty.
    String[] categories() default {};
}
