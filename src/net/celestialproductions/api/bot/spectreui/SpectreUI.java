package net.celestialproductions.api.bot.spectreui;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.IndependentLoopingThread;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.core.InvalidSetupException;
import net.celestialproductions.api.bot.spectreui.elements.ProfitPane;
import net.celestialproductions.api.bot.spectreui.elements.Tab;
import net.celestialproductions.api.bot.spectreui.elements.SkillPane;
import net.celestialproductions.api.util.StringUtils;

import java.net.URL;
import java.util.*;

/**
 * @author Savior
 */
public class SpectreUI<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable, SkillListener {
    @FXML
    private Button changeOptionsButton;
    @FXML
    private Label statusLabel, runtimeLabel;
    @FXML
    private VBox tabContainer;

    private final T bot;
    private final Map<Skill, Tab> skillsListed;

    public SpectreUI(final T bot) {
        this.bot = bot;
        this.skillsListed = new HashMap<>();

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SpectreUI.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        if (bot.botConfigurator() != null) {
            changeOptionsButton.setOnAction(e -> bot.extender().botInterfaceProperty().set(new BotConfigurationUI<>(bot)));
        } else {
            changeOptionsButton.setVisible(false);
            changeOptionsButton.setDisable(true);
            try {
                bot.startButtonPerformed();
                bot.timer().start();
            } catch (InvalidSetupException e) {
                ClientUI.showAlert(e.getMessage());
                bot.stop();
            }
        }

        bot.getEventDispatcher().addListener(this);

        final ProfitPane<T> profitPane = new ProfitPane<>(bot);
        add(new Tab("Profit", profitPane, Tab.Priority.LOWEST));

        new IndependentLoopingThread("SpectreUI update thread", this::update, 1000L).start();
    }

    public void update() {
        final String time = bot.timer().getRuntimeAsString();
        final String status = bot.getStatus();
        Platform.runLater(() -> {
            runtimeLabel.setText(time);
            statusLabel.setText(status);
        });
    }

    public void add(final Tab... tabs) {
        Platform.runLater(() -> {
            final List<Tab> list = new ArrayList<>();
            tabContainer.getChildren().stream().map(node -> (Tab) node).forEach(list::add);
            list.addAll(Arrays.asList(tabs));
            list.sort(((o1, o2) -> o2.priority().ordinal() - o1.priority().ordinal()));
            tabContainer.getChildren().setAll(list);
        });

    }

    public void remove(final Tab... tabs) {
        Platform.runLater(() -> tabContainer.getChildren().removeAll(tabs));
    }

    public void remove(final Skill skill) {
        remove(skillsListed.get(skill));
        skillsListed.remove(skill);
    }

    @Override
    public void onExperienceGained(final SkillEvent skillEvent) {
        final Skill skill;
        if (skillEvent.getChange() > 0 && skillEvent.getPrevious() > 0 && !skillsListed.containsKey(skill = skillEvent.getSkill())) {
            final SkillPane<T> skillPane = new SkillPane<>(bot, skill);
            final Tab tab = new Tab(StringUtils.capitalizeFully(skill.name()), skillPane, Tab.Priority.AUTOMATIC);
            add(tab);
            skillsListed.put(skill, tab);
        }
    }
}
