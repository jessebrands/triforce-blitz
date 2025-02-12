package com.triforceblitz.triforceblitz.racetime.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * A Racetime.gg race.
 */
public class Race {
    /// Version of the data, incremented with each change.
    @JsonProperty("version")
    private int version;

    /// Slug of the race in the format abc-def-1234.
    @JsonProperty("slug")
    private String slug;

    /// Status of the race.
    @JsonProperty("status")
    private RaceStatus status;

    /// Category of the race.
    @JsonProperty("category")
    private RaceCategory category;

    /// Goal of the race.
    @JsonProperty("goal")
    private RaceGoal goal;

    /// Summarized information about the race.
    @JsonProperty("info")
    private String info;

    /// Whether this race will affect the entrants rank.
    @JsonProperty("ranked")
    private boolean ranked;

    /// Unlisted races will not appear in the category's listed races.
    @JsonProperty("unlisted")
    private boolean unlisted;

    /// Whether the race can be recorded.
    @JsonProperty("recordable")
    private boolean recordable;

    /// Whether the race has been recorded.
    @JsonProperty("recorded")
    private boolean recorded;

    /// Whether the race is a team race.
    @JsonProperty("team_race")
    private boolean teamRace;

    /// Teams must be the same size.
    @JsonProperty("require_even_teams")
    private boolean evenTeamsRequired;

    /// Whether entrants are required to stream.
    @JsonProperty("streaming_required")
    private boolean streamingRequired;

    /// List of entrants in the race.
    @JsonProperty("entrants")
    private List<Entrant> entrants;

    /// Time and date the race room was opened at.
    @JsonProperty("opened_at")
    private Instant openedAt;

    /// Time and date the race started at.
    @Nullable
    @JsonProperty("started_at")
    private Instant startedAt;

    /// Time and date all entrants finished or dropped out at.
    @Nullable
    @JsonProperty("ended_at")
    private Instant endedAt;

    /// Time and date the race was cancelled at.
    @Nullable
    @JsonProperty("cancelled_at")
    private Instant cancelledAt;

    /// Maximum duration the race can go for.
    @Nullable
    @JsonProperty("time_limit")
    private Duration timeLimit;

    public int getVersion() {
        return version;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return String.format("%s/%s", category.getSlug(), slug);
    }

    public RaceStatus getStatus() {
        return status;
    }

    /**
     * Checks if the race has completed.
     * @return True if the race status is FINISHED or CANCELLED.
     */
    public boolean completed() {
        return status == RaceStatus.FINISHED || status == RaceStatus.CANCELLED;
    }

    public RaceCategory getCategory() {
        return category;
    }

    public RaceGoal getGoal() {
        return goal;
    }

    public String getInfo() {
        return info;
    }

    public boolean isRanked() {
        return ranked;
    }

    public boolean isUnlisted() {
        return unlisted;
    }

    public boolean isRecordable() {
        return recordable;
    }

    public boolean isRecorded() {
        return recorded;
    }

    public boolean isTeamRace() {
        return teamRace;
    }

    public boolean isEvenTeamsRequired() {
        return evenTeamsRequired;
    }

    public boolean isStreamingRequired() {
        return streamingRequired;
    }

    public List<Entrant> getEntrants() {
        return entrants;
    }

    public Instant getOpenedAt() {
        return openedAt;
    }

    @Nullable
    public Instant getStartedAt() {
        return startedAt;
    }

    @Nullable
    public Instant getEndedAt() {
        return endedAt;
    }

    @Nullable
    public Instant getCancelledAt() {
        return cancelledAt;
    }

    @Nullable
    public Duration getTimeLimit() {
        return timeLimit;
    }
}
