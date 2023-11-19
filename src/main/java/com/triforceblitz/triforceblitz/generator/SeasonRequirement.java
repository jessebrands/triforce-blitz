package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.seeds.Season;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SeasonRequirement {
    private final long id;
    private final Season season;

    @Nullable
    private final Version minimumVersion;

    @Nullable
    private final Version maximumVersion;

    private final List<String> branches = new ArrayList<>();

    // All args constructor for JDBC
    public SeasonRequirement(long id, Season season,
                             @Nullable Version minimumVersion,
                             @Nullable Version maximumVersion,
                             String[] branches) {
        this.id = id;
        this.season = season;
        this.minimumVersion = minimumVersion;
        this.maximumVersion = maximumVersion;
        this.branches.addAll( Arrays.stream(branches).toList());
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
