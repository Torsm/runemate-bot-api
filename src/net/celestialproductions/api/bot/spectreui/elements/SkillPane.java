package net.celestialproductions.api.bot.spectreui.elements;

import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.extender.Mainclass;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class SkillPane<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable, SkillListener {
    @FXML
    private Button stopTrackingButton;

    private final T bot;
    private final Skill skill;

    public SkillPane(final T bot, final Skill skill) {
        this.bot = bot;
        this.skill = skill;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SkillPane.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        stopTrackingButton.setOnAction(event -> {
            bot.getEventDispatcher().removeListener(this);
            bot.spectreUI().remove(skill);
        });
        bot.getEventDispatcher().addListener(this);
    }

    @Override
    public void onExperienceGained(final SkillEvent skillEvent) {

    }
}
