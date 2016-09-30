package com.productions.celestial.bots.constructor;

import com.productions.celestial.bots.constructor.banking.GetServant;
import com.productions.celestial.bots.constructor.constructing.ConstructPrawnBroker;
import com.productions.celestial.bots.constructor.constructing.RemovePrawnBroker;
import com.runemate.game.api.script.framework.task.TaskScript;

/**
 * Created by sunny on 9/21/2016.
 */
public class Constructor extends TaskScript {
    @Override
    public void onStart(final String... strings) {
        setLoopDelay(300);
        add(new GetServant(), new ConstructPrawnBroker(), new RemovePrawnBroker());
    }


}
