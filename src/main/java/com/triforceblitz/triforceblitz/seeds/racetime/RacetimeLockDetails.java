package com.triforceblitz.triforceblitz.seeds.racetime;

import java.util.UUID;

public interface RacetimeLockDetails {
    UUID getSeedId();
    boolean isLocked();
    String getRaceCategory();
    String getRaceName();
    boolean isOpen();
    boolean isInProgress();
    boolean isFinished();
}
