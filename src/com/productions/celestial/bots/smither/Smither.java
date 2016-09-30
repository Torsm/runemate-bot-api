package com.productions.celestial.bots.smither;

import com.productions.celestial.bots.smither.banking.GetItems;
import com.productions.celestial.bots.smither.smelting.PlaceForge;
import com.productions.celestial.bots.smither.smelting.SmeltOre;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.script.framework.task.TaskScript;

/**
 * Created by sunny on 9/26/2016.
 */
public class Smither extends TaskScript {

    private static final Area.Rectangular AREA =
            new Area.Rectangular(new Coordinate(3150, 3474, 0), new Coordinate(3146, 3469, 0));
    private static final Coordinate RANDOM_COORDINATE = AREA.getRandomCoordinate();

    @Override
    public void onStart(final String... strings) {
        setLoopDelay(600);
        add(new GetItems(), new PlaceForge(AREA, RANDOM_COORDINATE), new SmeltOre(RANDOM_COORDINATE));
    }
}
