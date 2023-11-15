package com.triforceblitz.triforceblitz.seeds.converters;

import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.SeasonRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToSeasonConverter implements Converter<String, Season> {
    private final SeasonRepository repository;

    public StringToSeasonConverter(SeasonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Season convert(String source) {
        var uuid = UUID.fromString(source);
        return repository.findByUuid(uuid).orElseThrow();
    }
}
