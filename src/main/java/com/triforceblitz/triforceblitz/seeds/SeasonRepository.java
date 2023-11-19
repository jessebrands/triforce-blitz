package com.triforceblitz.triforceblitz.seeds;

import java.util.Optional;
import java.util.UUID;

public interface SeasonRepository {
    Optional<Season> findById(long id);
}
