package com.triforceblitz.triforceblitz.seeds.racetime;

import com.triforceblitz.triforceblitz.seeds.Seed;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "racetime_locks")
public class RacetimeLock {
    @Id
    @Column(name = "id", nullable = false)
    private final UUID id;

    @Column(name = "race_slug", length = 32, unique = true, nullable = false)
    private String raceSlug;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seed_id", referencedColumnName = "id", unique = true, nullable = false)
    private Seed seed;

    protected RacetimeLock() {
        this.id = null;
    }

    public RacetimeLock(String raceSlug, Seed seed) {
        this.id = UUID.randomUUID();
        this.raceSlug = raceSlug;
        this.seed = seed;
    }

    public UUID getId() {
        return id;
    }

    public String getRaceCategory() {
        return "ootr";
    }

    public String getRaceSlug() {
        return raceSlug;
    }

    public void setRaceSlug(String raceSlug) {
        this.raceSlug = raceSlug;
    }

    public Seed getSeed() {
        return seed;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }
}
