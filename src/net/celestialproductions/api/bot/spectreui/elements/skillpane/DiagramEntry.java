package net.celestialproductions.api.bot.spectreui.elements.skillpane;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

/**
 * @author Savior
 */
public class DiagramEntry extends Arc {
    private final Color color;

    public DiagramEntry(final Color color) {
        super(100, 100, 0, 0, 0, 0);
        this.color = color;

        setType(ArcType.ROUND);
        setFill(color);
        setStroke(BACKGROUND_COLOR);
        setStrokeWidth(2);
        radiusYProperty().bind(radiusXProperty());
    }

    public void setRadius(final double radius) {
        final Timeline t = new Timeline();
        final KeyValue v = new KeyValue(radiusXProperty(), radius);
        final KeyFrame f = new KeyFrame(Duration.millis(200), v);
        t.getKeyFrames().add(f);
        t.play();
    }

    public void setAngle(final double angle, final double length) {
        final Timeline t = new Timeline();
        final KeyValue vLength = new KeyValue(lengthProperty(), length);
        final KeyValue vAngle = new KeyValue(startAngleProperty(), angle);
        final KeyFrame f = new KeyFrame(Duration.millis(200), vLength, vAngle);
        t.getKeyFrames().add(f);
        t.play();
    }

    private final static Color BACKGROUND_COLOR = Color.rgb(24, 24, 24);
}
