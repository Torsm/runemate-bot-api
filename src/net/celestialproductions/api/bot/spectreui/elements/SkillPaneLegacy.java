package net.celestialproductions.api.bot.spectreui.elements;

import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.extender.Mainclass;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class SkillPaneLegacy<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable, SkillListener {

    public SkillPaneLegacy(final T bot) {
        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SkillPaneLegacy.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    @Override
    public void onExperienceGained(final SkillEvent skillEvent) {

    }
}
