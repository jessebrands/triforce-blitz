package com.triforceblitz.triforceblitz.generator.jdbc;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.generator.SeasonRequirement;
import com.triforceblitz.triforceblitz.seeds.Season;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonRequirementRowMapper implements RowMapper<SeasonRequirement> {
    @Override
    public SeasonRequirement mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Parse the season first
        var season = new Season(
                rs.getLong("season_id"),
                rs.getInt("ordinal"),
                rs.getString("preset"),
                rs.getString("message_key")
        );
        var minimumVersion = rs.getString("min_version");
        var maximumVersion = rs.getString("max_version");
        var branches = rs.getArray("branches");

        return new SeasonRequirement(
                rs.getLong("id"),
                season,
                minimumVersion != null ? Version.from(minimumVersion) : null,
                maximumVersion != null ? Version.from(maximumVersion) : null,
                (String[])branches.getArray()
        );
    }
}
