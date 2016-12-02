package net.celestialproductions.api.game.entities;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.queries.LocatableEntityQueryBuilder;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.Validatable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Abstract class to create timeable entities with, like trees, fishing spots, rocks
 *
 * @author Defeat3d
 *
 * @param <L> type of the underlying entity, for example Npc for a fishing spot, GameObject for a tree
 * @param <B> type of query builder required to attach the entity of type L
 */
public abstract class TimeableEntity<L extends LocatableEntity, B extends LocatableEntityQueryBuilder<L, B>> implements Locatable, Interactable, Validatable {
    public static final long DEFAULT_EXPIRY = TimeUnit.MINUTES.toMillis(15);
    private final long expiry; // expiry time used to set the status of entities out of detection range
    private final Coordinate position; // position this entity spawns at
    private final Callable<B> builder; // query builder to attach instances of this entity with
    private final StopWatch availabilityAge; // stopwatch that tracks how long this entity has had it's current status for
    private boolean available;
    private boolean allowMovement;
    private boolean fixedSpawnRate;
    private long lastQuery;
    private L instance; // instance of our in-game entity, used for interaction etc

    /**
     * Constructs a timeable entity on the given position, loaded using the given builder, with a default expiry time of 15 minutes
     * @param position position of this entity
     * @param builder builder to attach this entity with
     */
    public TimeableEntity(final Coordinate position, final Callable<B> builder) {
        this(position, builder, DEFAULT_EXPIRY);
    }

    /**
     * Constructs a timeable entity on the given position, loaded using the given builder
     * @param position position of this entity
     * @param builder builder to attach this entity with
     * @param expiry expiry time of this entity's status (think of a tree's respawn time)
     */
    public TimeableEntity(final Coordinate position, final Callable<B> builder, final long expiry) {
        this.position = position;
        this.builder = builder;
        this.expiry = expiry;
        this.allowMovement = true;
        this.fixedSpawnRate = true;
        this.availabilityAge = new StopWatch();

        setAvailable(true);
        availabilityAge.start();
    }

    /**
     * @return the query builder to attach instances with
     */
    public final B builder() {
        try {
            return builder.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * If the last call was less than one game tick ago, the existing instance can be reused
     * @return an instance of the in-game entity
     */
    public final L instance() {
        if (System.currentTimeMillis() - lastQuery < 600L)
            return instance;
        lastQuery = System.currentTimeMillis();
        return (instance != null && instance.isValid() && (allowMovement || instance.getPosition().equals(position))) ? instance : (instance = builder().results().first());
    }

    /**
     * @return the amount of time this entity has had it's current status for
     */
    public final long getAvailabilityAge() {
        return availabilityAge.getRuntime();
    }

    public final void resetAvailabilityAge() {
        this.availabilityAge.reset();
    }

    /**
     * @return whether this entity is currently available in-game
     */
    public final boolean isAvailable() {
        return available;
    }

    /**
     * If the availability changes or the entity does not have a constant respawn time, the availability age gets reset.
     * @param available value to set to our available property (used by an observer)
     */
    public final void setAvailable(final boolean available) {
        if (this.available != available || !fixedSpawnRate)
            resetAvailabilityAge();
        this.available = available;
    }

    /**
     * Sets the available property to the correct one
     * @return whether this entity is currently available in-game
     */
    public final boolean retrieveAvailability(final Collection<? extends L> raw) {
        final B b = builder();
        if (b != null && isWithinLoadingDistance())
            setAvailable(!b.provider(() -> new ArrayList<>(raw)).results().isEmpty());
        return available;
    }

    /**
     * @return the expiry time of this entity
     */
    public final long getExpiry() {
        return expiry;
    }

    /**
     * Set whether or not the holding instance is allowed to change its position. If not, the instance gets renewed once it leaves position.
     */
    public void setAllowMovement(final boolean allowMovement) {
        this.allowMovement = allowMovement;
    }

    /**
     * Set if the entity has a constant respawn rate. If not, the availability age will be reset on ever observation.
     */
    public void setFixedSpawnRate(final boolean fixedSpawnRate) {
        this.fixedSpawnRate = fixedSpawnRate;
    }

    @Override
    public String toString() {
        final String[] split = super.toString().split("\\.");
        return "[" + split[split.length - 1] + ": " + (available ? "Available" : "Unavailable") + " since " + getAvailabilityAge() + "ms]";
    }

    @Override
    public boolean equals(final Object obj) {
        return getClass().isInstance(obj) && position.equals((getClass().cast(obj)).getPosition());
    }

    /**
     * Determine if the position of this entity is within the loading distance of the type of LocatableEntity
     */
    public boolean isWithinLoadingDistance() {
        return true;
    }

    /*
     * Below this are a bunch of methods implemented to make our entity work like one straight from the RuneMate API
     */

    @Override
    public Coordinate.HighPrecision getHighPrecisionPosition() {
        return instance() != null && position.equals(instance.getPosition()) ? instance.getHighPrecisionPosition() : position.getHighPrecisionPosition();
    }

    @Override
    public Area.Rectangular getArea() {
        return instance() != null && position.equals(instance.getPosition()) ? instance.getArea() : position.getArea();
    }

    @Override
    public Coordinate getPosition() {
        return position;
    }

    @Override
    public boolean interact(final Pattern pattern, final Pattern pattern1) {
        return instance() != null && instance.interact(pattern, pattern1);
    }

    @Override
    public boolean click() {
        return instance() != null && instance.click();
    }

    @Override
    public InteractablePoint getInteractionPoint(final Point point) {
        return instance() != null ? instance.getInteractionPoint(point) : null;
    }

    @Override
    public boolean contains(final Point point) {
        return instance() != null && instance.contains(point);
    }

    @Override
    public boolean hover() {
        return instance() != null && instance.hover();
    }

    @Override
    public boolean isVisible() {
        return instance() != null && instance.isVisible();
    }

    @Override
    public boolean isValid() {
        return instance() != null && instance.isValid();
    }

    @Override
    public boolean hasDynamicBounds() {
        return instance() != null && instance.hasDynamicBounds();
    }

    @Override
    public double getVisibility() {
        return instance() != null ? instance.getVisibility() : 0;
    }
}
