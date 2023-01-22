package codes.laivy.engine.graphics.components.shape;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Ellipse2D;

public class CircleComponent extends EllipseComponent {

    public CircleComponent(@NotNull Game game, boolean filled, @NotNull Location location, int diameter) {
        super(game, filled, location, new Dimension(diameter, diameter));
    }

    @Override
    public @NotNull CircleComponent clone() {
        return (CircleComponent) super.clone();
    }

}
