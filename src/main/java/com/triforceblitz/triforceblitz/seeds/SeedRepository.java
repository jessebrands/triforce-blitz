package com.triforceblitz.triforceblitz.seeds;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeedRepository extends JpaRepository<Seed, UUID> {
}
