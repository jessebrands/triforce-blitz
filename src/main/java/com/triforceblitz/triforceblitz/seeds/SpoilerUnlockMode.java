package com.triforceblitz.triforceblitz.seeds;

/**
 * This enum lists the options for how a generated seed can have its spoiler log unlocked.
 *
 * @author Jesse
 * @see com.triforceblitz.triforceblitz.seeds.forms.GenerateSeedForm
 */
public enum SpoilerUnlockMode {
    /**
     * The spoiler log should always be unlocked and accessible.
     */
    ALWAYS_UNLOCKED,

    /**
     * The spoiler log should never be unlocked or accessible.
     */
    ALWAYS_LOCKED,
}
