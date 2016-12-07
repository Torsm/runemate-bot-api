package net.celestialproductions.api.util;

/**
 * @author Savior
 */
public interface Chance {

    default boolean poll() {
        return chance() > Math.random();
    }

    double chance();

}
