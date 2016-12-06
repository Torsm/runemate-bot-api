package net.celestialproductions.api.bot.spectreui.elements.skillpane.display.diagram;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

/**
 * @author Savior
 */
public class DiagramEntry extends Arc {

    public DiagramEntry() {
        super(100, 100, 0, 0, 0, 0);
        setType(ArcType.ROUND);
        radiusYProperty().bind(radiusXProperty());
    }

    public void setRadius(final double radius) {
        final Timeline t = new Timeline();
        final KeyValue v = new KeyValue(radiusXProperty(), radius);
        final KeyFrame f = new KeyFrame(Duration.millis(200), v);
        t.getKeyFrames().add(f);
        t.play();
    }

    public void setLength(final double angle, final double length) {
        final Timeline t = new Timeline();
        final KeyValue vLength = new KeyValue(lengthProperty(), length);
        final KeyValue vAngle = new KeyValue(startAngleProperty(), angle);
        final KeyFrame f = new KeyFrame(Duration.millis(200), vLength, vAngle);
        t.getKeyFrames().add(f);
        t.play();
    }

    public void changeOpacity(final double opacity) {
        final Timeline t = new Timeline();
        final KeyValue v = new KeyValue(opacityProperty(), opacity);
        final KeyFrame f = new KeyFrame(Duration.millis(100), v);
        t.getKeyFrames().add(f);
        t.play();
    }
}
