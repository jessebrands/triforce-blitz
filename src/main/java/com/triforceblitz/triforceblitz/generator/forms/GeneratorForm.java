package com.triforceblitz.triforceblitz.generator.forms;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.seeds.Season;

public class GeneratorForm {
    private Version version;
    private Season season;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
