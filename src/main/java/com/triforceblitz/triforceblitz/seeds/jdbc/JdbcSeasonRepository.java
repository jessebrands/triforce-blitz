package com.triforceblitz.triforceblitz.seeds.jdbc;

import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.SeasonRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcSeasonRepository implements SeasonRepository {
    private final JdbcTemplate template;

    public JdbcSeasonRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Season> findById(long id) {
        var season = template.queryForObject("SELECT * FROM seasons WHERE id = ?", new SeasonRowMapper(), id);
        return Optional.ofNullable(season);
    }
}
