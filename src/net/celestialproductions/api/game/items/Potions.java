package net.celestialproductions.api.game.items;

import com.runemate.game.api.hybrid.local.Varbits;

/**
 * @author Savior
 */
public final class Potions {

    public static boolean prayerRenewalActive() {
        return Varbits.load(2100).getValue() == 1;
    }

    public static boolean jujuFishingActive() {
        return Varbits.load(29523).getValue() == 1;
    }

}
