package com.triforceblitz.triforceblitz.seeds.forms;

import com.triforceblitz.triforceblitz.TriforceBlitzVersion;
import com.triforceblitz.triforceblitz.seeds.SpoilerUnlockMode;
import lombok.Data;

@Data
public class GenerateSeedForm {
    private TriforceBlitzVersion version;
    private SpoilerUnlockMode unlockMode;
}
