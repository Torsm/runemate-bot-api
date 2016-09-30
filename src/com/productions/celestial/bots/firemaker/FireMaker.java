package com.productions.celestial.bots.firemaker;

import com.productions.celestial.bots.firemaker.banking.GetLogs;
import com.productions.celestial.bots.firemaker.firemaking.BurnLogs;
import com.runemate.game.api.script.framework.task.TaskScript;

/**
 * Created by sunny on 9/26/2016.
 */
public class FireMaker extends TaskScript {

    @Override
    public void onStart(final String... strings) {
        setLoopDelay(300);
        add(new GetLogs(), new BurnLogs());
    }
}
