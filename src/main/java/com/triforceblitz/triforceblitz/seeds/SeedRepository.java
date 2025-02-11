package com.triforceblitz.triforceblitz.seeds;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SeedRepository extends CrudRepository<Seed, UUID> {
}
