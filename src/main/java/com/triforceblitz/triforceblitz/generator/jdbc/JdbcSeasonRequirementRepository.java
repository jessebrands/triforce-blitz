package com.triforceblitz.triforceblitz.generator.jdbc;

import com.triforceblitz.triforceblitz.generator.SeasonRequirement;
import com.triforceblitz.triforceblitz.generator.SeasonRequirementRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcSeasonRequirementRepository implements SeasonRequirementRepository {
    private final JdbcTemplate template;

    public JdbcSeasonRequirementRepository(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    @Cacheable("season_requirements")
    public List<SeasonRequirement> findAll() {
        return template.query("""
                        SELECT season_requirements.id as id,
                               min_version, max_version, branches,
                               season_id, ordinal, preset, message_key
                               FROM season_requirements
                        LEFT JOIN seasons s on s.id = season_requirements.season_id
                        """,
                new SeasonRequirementRowMapper());
    }
}
