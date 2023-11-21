package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzVersion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Seed {
    private final UUID id;
    private final TriforceBlitzVersion version;
    private final String seed;
    private final Instant createdOn;

    private boolean spoilerLocked;

    public static Seed create(TriforceBlitzVersion version, String seed) {
        return new Seed(
                UUID.randomUUID(),
                version,
                seed,
                Instant.now(),
                true
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seed seed = (Seed) o;
        return Objects.equals(id, seed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Seed{" + seed + "}";
    }
}
