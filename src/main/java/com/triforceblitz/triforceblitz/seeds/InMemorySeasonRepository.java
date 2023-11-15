package com.triforceblitz.triforceblitz.seeds;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemorySeasonRepository implements SeasonRepository {
    private final Map<UUID, Season> seasons = new HashMap<>();

    @Override
    public Optional<Season> findByUuid(UUID uuid) {
        return Optional.ofNullable(seasons.get(uuid));
    }

    @Override
    public Season save(Season season) {
        seasons.put(season.getUuid(), season);
        return season;
    }
}
