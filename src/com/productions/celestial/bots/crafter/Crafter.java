package com.productions.celestial.bots.crafter;

import com.productions.celestial.bots.crafter.banking.GetItems;
import com.productions.celestial.bots.crafter.crafting.MakeBattlestaff;
import com.runemate.game.api.script.framework.task.TaskScript;

/**
 * Created by sunny on 9/21/2016.
 */
public class Crafter extends TaskScript {

    @Override
    public void onStart(final String... strings) {
        setLoopDelay(300, 600);
        add(new GetItems(), new MakeBattlestaff());
    }

}
