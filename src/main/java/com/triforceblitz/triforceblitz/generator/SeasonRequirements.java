package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.seeds.Season;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeasonRequirements {
    private final Season season;

    @Nullable
    private final Version minimumVersion;

    @Nullable
    private final Version maximumVersion;

    private final List<String> branches = new ArrayList<>();

    public SeasonRequirements(Season season,
                              @Nullable Version minimumVersion) {
        this(season, null, minimumVersion, null);
    }

    public SeasonRequirements(Season season,
                              @Nullable Version minimumVersion,
                              @Nullable Version maximumVersion) {
        this(season, null, minimumVersion, maximumVersion);
    }

    public SeasonRequirements(Season season,
                              @Nullable List<String> branches) {
        this(season, branches, null, null);
    }

    public SeasonRequirements(Season season,
                              @Nullable List<String> branches,
                              @Nullable Version minimumVersion) {
        this(season, branches, minimumVersion, null);
    }

    public SeasonRequirements(Season season,
                              @Nullable List<String> branches,
                              @Nullable Version minimumVersion,
                              @Nullable Version maximumVersion) {
        this.season = Objects.requireNonNull(season);
        if (branches != null) {
            this.branches.addAll(branches);
        }
        this.minimumVersion = minimumVersion;
        this.maximumVersion = maximumVersion;
    }

    public boolean satisfiesRequirements(Version version) {
        // If this version is bounded by a branch, check that first.
        if (!branches.isEmpty() && !branches.contains(version.branch())) {
            return false;
        }

        // We satisfy the branch requirement, what about the version?
        if (minimumVersion != null && version.compareTo(minimumVersion) < 0) {
            return false;
        }
        return maximumVersion == null || version.compareTo(maximumVersion) <= 0;
    }

    public Season getSeason() {
        return season;
    }

    @Nullable
    public Version getMinimumVersion() {
        return minimumVersion;
    }

    @Nullable
    public Version getMaximumVersion() {
        return maximumVersion;
    }

    public List<String> getBranches() {
        return branches;
    }
}
