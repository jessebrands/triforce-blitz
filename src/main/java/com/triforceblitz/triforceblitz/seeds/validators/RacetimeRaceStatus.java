package com.triforceblitz.triforceblitz.seeds.validators;

import com.triforceblitz.triforceblitz.racetime.race.RaceStatus;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validates that a race room exists and has a specific status, if specified.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RacetimeRaceStatusValidator.class)
public @interface RacetimeRaceStatus {
    String message() default "{seeds.validation.constraints.race-status.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

    /// List of valid statuses; ignored if empty.
    RaceStatus[] statuses() default {};
}
