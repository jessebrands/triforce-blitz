package com.triforceblitz.triforceblitz.seeds;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "featured_seeds")
public class FeaturedSeed {
    @Id
    @Column(name = "id", nullable = false)
    private final UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seed_id", referencedColumnName = "id", unique = true, nullable = false)
    private Seed seed;

    @Column(name = "daily", nullable = false)
    private boolean daily = false;

    @Column(name = "weekly", nullable = false)
    private boolean weekly = false;

    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();

    protected FeaturedSeed() {
        this.id = null;
    }

    public FeaturedSeed(Seed seed) {
        this.id = UUID.randomUUID();
        this.seed = seed;
    }

    public FeaturedSeed(Seed seed, LocalDate date) {
        this.id = UUID.randomUUID();
        this.seed = seed;
        this.date = date;
    }


    public UUID getId() {
        return id;
    }

    public Seed getSeed() {
        return seed;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public boolean isWeekly() {
        return weekly;
    }

    public void setWeekly(boolean weekly) {
        this.weekly = weekly;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
