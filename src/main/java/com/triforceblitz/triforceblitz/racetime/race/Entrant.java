package com.triforceblitz.triforceblitz.racetime.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;

/**
 * An entrant is a contest in a race.
 */
public class Entrant {
    /**
     * Summarized user data of an entrant.
     */
    public static class User {
        /// Unique identifier for the user.
        @JsonProperty("id")
        private String id;

        /// The user's name.
        @JsonProperty("name")
        private String name;

        /// Short value to discriminate users with the same name.
        @Nullable
        @JsonProperty("discriminator")
        private String discriminator;

        /// Whether this entrant can moderate the race.
        @JsonProperty("can_moderate")
        private boolean moderator;

        /**
         * Returns the display name.
         * @return The user's full display name.
         */
        public String getDisplayName() {
            if (discriminator != null) {
                return String.format("%s#%s", name, discriminator);
            }
            return name;
        }
    }

    /// Entrant user information.
    @JsonProperty("user")
    private User user;

    /// Entrant race status.
    @JsonProperty("status")
    private EntrantStatus status;

    /// Time it took for the entrant to finish the race.
    @Nullable
    @JsonProperty("finish_time")
    private Duration finishTime;

    /// Time at which the entrant finished.
    @Nullable
    @JsonProperty("finished_at")
    private Instant finishedAt;

    /// Entrant position on the leaderboard of the race.
    @Nullable
    @JsonProperty("place")
    private Integer place;

    /// Comment left by the entrant.
    @Nullable
    @JsonProperty("comment")
    private String comment;
}
