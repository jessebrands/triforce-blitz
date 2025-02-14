package com.triforceblitz.triforceblitz.seeds;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "featured_seeds")
public class Feature {
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

    @CreationTimestamp
    @Column(name = "added_at", nullable = false)
    private Instant addedAt = Instant.now();

    protected Feature() {
        this.id = null;
    }

    public Feature(Seed seed) {
        this.id = UUID.randomUUID();
        this.seed = seed;
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

    public Instant getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }
}
