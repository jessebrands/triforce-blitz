package com.triforceblitz.triforceblitz.seeds.jdbc;

import com.triforceblitz.triforceblitz.seeds.Season;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonRowMapper implements RowMapper<Season> {
    @Override
    public Season mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Season(
                rs.getLong("id"),
                rs.getInt("ordinal"),
                rs.getString("preset"),
                rs.getString("message_key")
        );
    }
}
