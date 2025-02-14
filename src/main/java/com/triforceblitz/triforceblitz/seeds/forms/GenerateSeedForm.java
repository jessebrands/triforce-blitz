package com.triforceblitz.triforceblitz.seeds.forms;

import com.triforceblitz.triforceblitz.seeds.UnlockMode;
import com.triforceblitz.triforceblitz.seeds.validators.RacetimeRaceUrl;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.net.URL;

public class GenerateSeedForm {
    private boolean cooperative = true;

    @NotNull
    private UnlockMode unlockMode = UnlockMode.UNLOCKED;

    @Nullable
    @RacetimeRaceUrl(categories = {"ootr"})
    private URL racetimeUrl = null;

    public boolean isCooperative() {
        return cooperative;
    }

    public void setCooperative(boolean cooperative) {
        this.cooperative = cooperative;
    }

    public UnlockMode getUnlockMode() {
        return unlockMode;
    }

    public void setUnlockMode(UnlockMode unlockMode) {
        this.unlockMode = unlockMode;
    }

    @Nullable
    public URL getRacetimeUrl() {
        return racetimeUrl;
    }

    public void setRacetimeUrl(@Nullable URL racetimeUrl) {
        this.racetimeUrl = racetimeUrl;
    }

    public String getRaceSlug() {
        if (racetimeUrl != null) {
            return racetimeUrl.toString().replaceFirst("^https://racetime\\.gg/ootr/", "");
        }
        return null;
    }
}
