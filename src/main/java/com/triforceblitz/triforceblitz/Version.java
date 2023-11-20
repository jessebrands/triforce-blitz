package com.triforceblitz.triforceblitz;

import java.util.regex.Pattern;

public record Version(int major, int minor, int patch, String branch, int branchMajor, int branchMinor)
        implements Comparable<Version> {
    private static final Pattern VALID_PATTERN = Pattern.compile(
            "^v?(\\d+)\\.(\\d+)\\.(\\d+)-([a-z0-9]+)-(\\d+).(\\d+)$"
    );

    public static Version from(String s) {
        var matcher = VALID_PATTERN.matcher(s);
        if (!matcher.matches()) {
            throw new RuntimeException("invalid Triforce Blitz version");
        }

        // First group (0) is the whole match, so we skip that one. We know this is valid so move confidently.
        return new Version(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                matcher.group(4),
                Integer.parseInt(matcher.group(5)),
                Integer.parseInt(matcher.group(6))
        );
    }

    public int compareToRandomizerVersion(Version o) {
        if (major != o.major) return Integer.compare(major, o.major);
        if (minor != o.minor) return Integer.compare(minor, o.minor);
        return Integer.compare(patch, o.patch);
    }

    public int compareToBranchVersion(Version o) {
        if (branchMajor != o.branchMajor) return Integer.compare(branchMajor, o.branchMajor);
        return Integer.compare(branchMinor, o.branchMinor);
    }

    @Override
    public int compareTo(Version o) {
        var r = compareToBranchVersion(o);
        if (r != 0) {
            return r;
        }
        if (!branch.equals(o.branch)) {
            return branch.compareTo(o.branch);
        }
        return compareToRandomizerVersion(o);
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d-%s-%d.%d", major, minor, patch, branch, branchMajor, branchMinor);
    }
}
