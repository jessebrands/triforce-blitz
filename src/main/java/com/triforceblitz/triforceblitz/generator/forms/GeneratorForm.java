package com.triforceblitz.triforceblitz.generator.forms;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.SpoilerUnlockMode;
import lombok.Data;

@Data
public class GeneratorForm {
    private Version version;
    private Season season;
    private SpoilerUnlockMode unlockMode = SpoilerUnlockMode.UNLOCKED;
}
