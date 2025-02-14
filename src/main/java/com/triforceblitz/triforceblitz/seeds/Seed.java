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
    @Id
    @Column(name = "id", nullable = false)
    private final UUID id;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "seed", length = 64, nullable = false)
    private final String seed;

    @Column(name = "preset", length = 32, nullable = false)
    private final String preset;

    @Column(name = "randomizer_version", length = 32, nullable = false)
    private final String randomizerVersion;

    @Column(name = "cooperative", nullable = false)
    private final boolean cooperative;

    @Column(name = "spoiler_locked", nullable = false)
    private boolean spoilerLocked = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private final Instant createdAt;

    @Nullable
    @OneToOne(mappedBy = "seed", cascade = CascadeType.ALL, orphanRemoval = true)
    private RacetimeLock racetimeLock;

    @Nullable
    @OneToOne(mappedBy = "seed", cascade = CascadeType.ALL)
    private FeaturedSeed featuredSeed;

    protected Seed() {
        this.id = null;
        this.seed = null;
        this.preset = null;
        this.randomizerVersion = null;
        this.cooperative = false;
        this.createdAt = null;
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

    public void setSpoilerLocked(boolean spoilerLocked) {
        this.spoilerLocked = spoilerLocked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Nullable
    public RacetimeLock getRacetimeLock() {
        return racetimeLock;
    }

    public void addRacetimeLock(String category, String slug) {
        this.racetimeLock = new RacetimeLock(slug, this);
    }

    public void removeRacetimeLock() {
        this.racetimeLock = null;
    }

    @Nullable
    public FeaturedSeed getFeaturedSeed() {
        return featuredSeed;
    }

    public void setFeaturedSeed(@Nullable FeaturedSeed featuredSeed) {
        this.featuredSeed = featuredSeed;
    }

    public boolean isFeatured() {
        return featuredSeed != null;
    }
}
