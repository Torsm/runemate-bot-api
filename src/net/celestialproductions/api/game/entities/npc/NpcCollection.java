package net.celestialproductions.api.game.entities.npc;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Npcs;
import net.celestialproductions.api.game.entities.TimeableEntityCollection;
import net.celestialproductions.bots.fisher.spot.Spot;

import java.util.Collection;

/**
 * @author Savior
 */
public class NpcCollection extends TimeableEntityCollection<Npc, Spot> {

    @Override
    public Collection<? extends Npc> raw() {
        return Npcs.getLoaded().asList();
    }
}
