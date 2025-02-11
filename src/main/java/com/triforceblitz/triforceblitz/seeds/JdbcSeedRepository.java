package com.triforceblitz.triforceblitz.seeds;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcSeedRepository implements SeedRepository {
    private final JdbcClient jdbcClient;

    public JdbcSeedRepository(DataSource dataSource) {
        this.jdbcClient = JdbcClient.create(dataSource);
    }

    @Override
    public Optional<Seed> findById(UUID id) {
        return jdbcClient.sql("""
                        select id, seed, preset, version, cooperative, spoiler_locked, created_at
                        from seeds
                        where id = :id
                        """)
                .param("id", id)
                .query(Seed.class)
                .optional();
    }

    @Override
    public void save(Seed seed) {
        jdbcClient.sql("""
                        insert into seeds(id, seed, preset, version, cooperative, spoiler_locked)
                        values (:id, :seed, :preset, :version, :cooperative, :spoiler_locked)
                        """)
                .param("id", seed.getId())
                .param("seed", seed.getSeed())
                .param("preset", seed.getPreset())
                .param("version", seed.getVersion())
                .param("cooperative", seed.isCooperative())
                .param("spoiler_locked", seed.isSpoilerLocked())
                .update();
    }
}
