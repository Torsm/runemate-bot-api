package net.celestialproductions.api.game.location;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;

/**
 * @author Savior
 */
public enum BankLocation implements Locatable {
    NONE("None",                                         Type.NONE,    new Coordinate(0,    0,    0), new Coordinate(0,    0,    0)),
    AL_KHARID("Al Kharid",                               Type.FULL,    new Coordinate(3270, 3167, 0), new Coordinate(3270, 3167, 0)),
    APE_ATOLL_SOUTH("Ape Atoll",                         Type.DEPOSIT, new Coordinate(3047, 3237, 0), new Coordinate(3047, 3237, 0)),
    ARDOUGNE_EAST("Ardougne East",                       Type.FULL,    new Coordinate(2655, 3283, 0), new Coordinate(2655, 3283, 0)),
    BARBARIAN_OUTPOST("Barbarian outpost",               Type.FULL,    new Coordinate(2535, 3572, 0), new Coordinate(2535, 3572, 0)),
    BURGH_DE_ROTT("Burgh de Rott",                       Type.FULL,    new Coordinate(3494, 3211, 0), new Coordinate(3494, 3211, 0)),
    BURTHORPE("Burthorpe",                               Type.FULL,    new Coordinate(2888, 3535, 0), new Coordinate(0,    0,    0)),
    CATHERBY("Catherby",                                 Type.FULL,    new Coordinate(2795, 3439, 0), new Coordinate(2809, 3440, 0)),
    CASTLE_WARS("Castle Wars",                           Type.FULL,    new Coordinate(2442, 3085, 0), new Coordinate(2442, 3085, 0)),
    CLAN_CAMP_FALADOR("Clan camp Falador",               Type.FULL,    new Coordinate(3011, 3355, 0), new Coordinate(-1, -1, 0)),
    DRAYNOR_VILLAGE("Draynor Village",                   Type.FULL,    new Coordinate(3091, 3244, 0), new Coordinate(3093, 3243, 0)),
    DUEL_ARENA("Duel Arena",                             Type.SPECIAL, new Coordinate(3349, 3238, 0), new Coordinate(3349, 3238, 0)),
    EDGEVILLE("Edgeville",                               Type.FULL,    new Coordinate(3094, 3496, 0), new Coordinate(3094, 3491, 0)),
    FALADOR_EAST("Falador East",                         Type.FULL,    new Coordinate(3009, 3355, 0), new Coordinate(3009, 3355, 0)),
    FALADOR_WEST("Falador West",                         Type.FULL,    new Coordinate(2945, 3369, 0), new Coordinate(0,    0,    0)),
    FISHING_GUILD("Fishing Guild",                       Type.FULL,    new Coordinate(2584, 3422, 0), new Coordinate(2587, 3419, 0)),
    GRAND_EXCHANGE("Grand Exchange",                     Type.FULL,    new Coordinate(3151, 3480, 0), new Coordinate(3151, 3480, 0)),
    KARAMJA("Stiles",                                    Type.SPECIAL, new Coordinate(2851, 3143, 0), new Coordinate(3045, 3234, 0)),
    LIVING_ROCK_CAVERNS("Living Rock Caverns",           Type.SPECIAL, new Coordinate(3652, 5114, 0), new Coordinate(0,    0,    0)),
    LUMBRIDGE_CASTLE("Lumbridge Castle",                 Type.FULL,    new Coordinate(3208, 3219, 2), new Coordinate(3208, 3219, 2)),
    LUMBRIDGE_COMBAT_ACADEMY("Lumbridge Combat Academy", Type.FULL,    new Coordinate(3215, 3257, 0), new Coordinate(0,    0,    0)),
    PISCARILIUS("Piscarilius (Zeah)",                    Type.FULL,    new Coordinate(0,    0,    0), new Coordinate(1804, 3790, 0)),
    PISCATORIS("Piscatoris",                             Type.FULL,    new Coordinate(2330, 3690, 0), new Coordinate(2330, 3690, 0)),
    PORT_PHASMATYS("Port Phasmatys",                     Type.FULL,    new Coordinate(0,    0,    0), new Coordinate(3690, 3466, 0)),
    PORT_SARIM("Port Sarim",                             Type.DEPOSIT, new Coordinate(3045, 3234, 0), new Coordinate(3045, 3234, 0)),
    PRIFDDINAS_WATERFALL("Prifddinas Waterfall",         Type.FULL,    new Coordinate(2293, 3404, 2), new Coordinate(0,    0,    0)),
    SEERS_VILLAGE("Seer's Village",                      Type.FULL,    new Coordinate(2727, 3494, 0), new Coordinate(2725, 3492, 0)),
    SHILO_VILLAGE("Shilo Village",                       Type.FULL,    new Coordinate(2853, 2955, 0), new Coordinate(2853, 2955, 0)),
    VARROCK_EAST("Varrock East",                         Type.FULL,    new Coordinate(0,    0,    0), new Coordinate(3253, 3420, 0)),
    VARROCK_WEST("Varrock West",                         Type.FULL,    new Coordinate(3190, 3435, 0), new Coordinate(0,    0,    0)),
    WAIKO("Waiko (The Arc)",                             Type.FULL,    new Coordinate(1831, 11613,0), new Coordinate(0,    0,    0)),
    WHALES_MAW("Whale's Maw (The Arc)",                  Type.DEPOSIT, new Coordinate(2058, 11781,0), new Coordinate(0,    0,    0)),
    WOODCUTTING_GUILD("Woodcutting Guild",               Type.FULL,    new Coordinate(1592, 3476, 0), new Coordinate(1592, 3476, 0));

    private final String name;
    private final Type type;
    private final Coordinate rs3Coordinate, osrsCoordinate;

    BankLocation(final String name, final Type type, final Coordinate rs3Coordinate, final Coordinate osrsCoordinate) {
        this.name = name;
        this.type = type;
        this.rs3Coordinate = rs3Coordinate;
        this.osrsCoordinate = osrsCoordinate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Coordinate getPosition() {
        return Environment.isOSRS() ? osrsCoordinate : rs3Coordinate;
    }

    @Override
    public Area.Rectangular getArea() {
        return new Area.Rectangular(getPosition());
    }

    @Override
    public Coordinate.HighPrecision getHighPrecisionPosition() {
        return getPosition().getHighPrecisionPosition();
    }

    public Type getType() {
        return type;
    }


    public enum Type {
        NONE, DEPOSIT, FULL, SPECIAL
    }
}
