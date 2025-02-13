package com.triforceblitz.triforceblitz.seeds;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface FeaturedSeedRepository
        extends PagingAndSortingRepository<FeaturedSeed, UUID>, CrudRepository<FeaturedSeed, UUID> {
}
