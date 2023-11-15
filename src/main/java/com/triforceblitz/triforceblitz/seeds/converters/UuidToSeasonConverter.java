package com.triforceblitz.triforceblitz.seeds.converters;

import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.SeasonRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidToSeasonConverter implements Converter<UUID, Season> {
    private final SeasonRepository repository;

    public UuidToSeasonConverter(SeasonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Season convert(UUID source) {
        return repository.findByUuid(source).orElseThrow();
    }
}
