package net.celestialproductions.api.game.items;

import com.runemate.game.api.hybrid.local.Varps;

/**
 * @author Savior
 */
public class Potions {

    public static boolean prayerRenewalActive() {
        return Varps.getAt(902).getValue() == 4096;
    }

}
