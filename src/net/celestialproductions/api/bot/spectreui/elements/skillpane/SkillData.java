package net.celestialproductions.api.bot.spectreui.elements.skillpane;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.celestialproductions.api.bot.framework.extender.Mainclass;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class SkillData<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable, ListChangeListener<Node>, SkillListener {
    @FXML
    private Label skillLabel;

    private final T bot;
    private final Skill skill;
    private final SkillPane<T> skillPane;
    private final ObservableList<Node> listedEntries;
    private final BooleanProperty pinned;
    private final DiagramEntry diagramEntry;

    public SkillData(final T bot, final Skill skill, final SkillPane<T> skillPane) {
        this.bot = bot;
        this.skill = skill;
        this.skillPane = skillPane;
        this.listedEntries = skillPane.getDiagramPane().getChildren();
        this.pinned = new SimpleBooleanProperty(false);
        this.diagramEntry = new DiagramEntry(SKILL_COLOR_MAP.get(skill));

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SkillData.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        diagramEntry.setOnMouseClicked(event -> skillPane.setPinnedData(isPinned() ? null : this));

        pinnedProperty().addListener(((observable, oldValue, newValue) -> {

        }));

        diagramEntry.hoverProperty().addListener(((observable, oldValue, newValue) -> skillPane.setShownData(newValue ? this : skillPane.getPinnedData())));

        bot.getEventDispatcher().addListener(this);

        listedEntries.addListener(this);
        listedEntries.add(diagramEntry);

        skillLabel.setText(skill.name());
    }

    public void remove() {
        skillPane.remove(skill);
        if (isPinned()) {
            skillPane.setPinnedData(null);
        }
        bot.getEventDispatcher().removeListener(this);
        listedEntries.removeListener(this);
        listedEntries.remove(diagramEntry);
    }

    @Override
    public void onExperienceGained(final SkillEvent event) {
        if (event.getSkill() == skill && event.getPrevious() > 0 && event.getCurrent() > 0) {
            bot.getPlatform().invokeLater(() -> {
                final int percentage = 100 - skill.getExperienceToNextLevelAsPercent();
                final double radius = 30.0 + (percentage * 0.7);
                Platform.runLater(() -> diagramEntry.setRadius(radius));
            });
        }
    }

    @Override
    public void onChanged(final Change<? extends Node> c) {
        final double length = 360.0 / (double) listedEntries.size();
        final double angle = (listedEntries.size() - 1 - listedEntries.indexOf(diagramEntry)) * length;
        diagramEntry.setAngle(angle, length);
    }

    public boolean isPinned() {
        return pinned.get();
    }

    public void setPinned(final boolean pinned) {
        this.pinned.set(pinned);
    }

    public BooleanProperty pinnedProperty() {
        return pinned;
    }

    private final static Map<Skill, Color> SKILL_COLOR_MAP = new HashMap<Skill, Color>(){{
        //The awt colors are just for intellij to display the actual color
        put(Skill.ATTACK,        Color.web("#9A190D")); //new java.awt.Color(0x9A190D);
        put(Skill.DEFENCE,       Color.web("#3A89EF")); //new java.awt.Color(0x3A89EF);
        put(Skill.STRENGTH,      Color.web("#188C42")); //new java.awt.Color(0x188C42);
        put(Skill.CONSTITUTION,  Color.web("#C54844")); //new java.awt.Color(0xC54844);
        put(Skill.RANGED,        Color.web("#6F8A1D")); //new java.awt.Color(0x6F8A1D);
        put(Skill.PRAYER,        Color.web("#F2EFD7")); //new java.awt.Color(0xF2EFD7);
        put(Skill.MAGIC,         Color.web("#3C4CAB")); //new java.awt.Color(0x3C4CAB);
        put(Skill.COOKING,       Color.web("#572366")); //new java.awt.Color(0x572366);
        put(Skill.WOODCUTTING,   Color.web("#8B7454")); //new java.awt.Color(0x8B7454);
        put(Skill.FLETCHING,     Color.web("#044F50")); //new java.awt.Color(0x044F50);
        put(Skill.FISHING,       Color.web("#3E74D5")); //new java.awt.Color(0x3E74D5);
        put(Skill.FIREMAKING,    Color.web("#BF7100")); //new java.awt.Color(0xBF7100);
        put(Skill.CRAFTING,      Color.web("#9E7E58")); //new java.awt.Color(0x9E7E58);
        put(Skill.SMITHING,      Color.web("#6D6D6D")); //new java.awt.Color(0x6D6D6D);
        put(Skill.MINING,        Color.web("#6B6763")); //new java.awt.Color(0x6B6763);
        put(Skill.HERBLORE,      Color.web("#056A09")); //new java.awt.Color(0x056A09);
        put(Skill.AGILITY,       Color.web("#232077")); //new java.awt.Color(0x232077);
        put(Skill.THIEVING,      Color.web("#7D2E6B")); //new java.awt.Color(0x7D2E6B);
        put(Skill.SLAYER,        Color.web("#171516")); //new java.awt.Color(0x171516);
        put(Skill.FARMING,       Color.web("#74A546")); //new java.awt.Color(0x74A546);
        put(Skill.RUNECRAFTING,  Color.web("#8D8F83")); //new java.awt.Color(0x8D8F83);
        put(Skill.HUNTER,        Color.web("#767053")); //new java.awt.Color(0x767053);
        put(Skill.CONSTRUCTION,  Color.web("#A99C88")); //new java.awt.Color(0xA99C88);
        put(Skill.SUMMONING,     Color.web("#B7D2ED")); //new java.awt.Color(0xB7D2ED);
        put(Skill.DUNGEONEERING, Color.web("#4F2E0E")); //new java.awt.Color(0x4F2E0E);
        put(Skill.DIVINATION,    Color.web("#432D75")); //new java.awt.Color(0x432D75);
        put(Skill.INVENTION,     Color.web("#D3CC17")); //new java.awt.Color(0xD3CC17);
    }};
}
