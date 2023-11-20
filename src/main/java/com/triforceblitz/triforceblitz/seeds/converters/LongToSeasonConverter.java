package com.triforceblitz.triforceblitz.seeds.converters;

import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.SeasonRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LongToSeasonConverter implements Converter<String, Season> {
    private final SeasonRepository repository;

    public LongToSeasonConverter(SeasonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Season convert(String source) {
        var id = Long.parseLong(source);
        return repository.findById(id).orElse(null);
    }
}
