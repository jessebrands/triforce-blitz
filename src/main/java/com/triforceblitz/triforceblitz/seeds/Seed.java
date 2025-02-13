package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.seeds.racetime.RacetimeLock;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "seeds")
public class Seed {
    public static final String PATCH_FILENAME = "TriforceBlitz.zpf";
    public static final String COOP_PATCH_FILENAME = "TriforceBlitz.zpfz";
    public static final String SPOILER_LOG_FILENAME = "TriforceBlitz_Spoiler.json";

    @Id
    @Column(name = "id", nullable = false)
    private final UUID id;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "seed", length = 64, nullable = false)
    private String seed;

    @Column(name = "preset", length = 32, nullable = false)
    private String preset;

    @Column(name = "randomizer_version", length = 32, nullable = false)
    private String randomizerVersion;

    @Column(name = "cooperative", nullable = false)
    private boolean cooperative = false;

    @Column(name = "spoiler_locked", nullable = false)
    private boolean spoilerLocked = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Nullable
    @OneToOne(mappedBy = "seed", cascade = CascadeType.ALL)
    private RacetimeLock racetimeLock;

    @Nullable
    @OneToOne(mappedBy = "seed", cascade = CascadeType.ALL)
    private FeaturedSeed featuredSeed;

    protected Seed() {
        this.id = UUID.randomUUID();
    }

    public Seed(String seed, String preset, String randomizerVersion, boolean cooperative) {
        this.id = UUID.randomUUID();
        this.seed = seed;
        this.preset = preset;
        this.randomizerVersion = randomizerVersion;
        this.cooperative = cooperative;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getSeed() {
        return seed;
    }

    public String getPreset() {
        return preset;
    }

    public String getRandomizerVersion() {
        return randomizerVersion;
    }

    public boolean isCooperative() {
        return cooperative;
    }

    public boolean isSpoilerLocked() {
        return spoilerLocked;
    }

    public void lockSpoiler() {
        spoilerLocked = true;
    }

    public void unlockSpoiler() {
        spoilerLocked = false;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Nullable
    public RacetimeLock getRacetimeLock() {
        return racetimeLock;
    }

    public void setRacetimeLock(@Nullable RacetimeLock racetimeLock) {
        this.racetimeLock = racetimeLock;
    }

    @Nullable
    public FeaturedSeed getFeaturedSeed() {
        return featuredSeed;
    }

    public boolean isFeatured() {
        return featuredSeed != null;
    }
}
