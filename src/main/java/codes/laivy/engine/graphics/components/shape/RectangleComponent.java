package codes.laivy.engine.graphics.components.shape;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RectangleComponent extends ShapeComponent {

    public RectangleComponent(@NotNull Game game, boolean filled, @NotNull Location location, @NotNull Dimension dimension) {
        super(game, new Rectangle(location.toPoint(), dimension.toSwing()), filled, location);
    }
    @Override
    public @NotNull Rectangle getShape(@NotNull Location location, @NotNull Dimension dimension) {
        return new Rectangle(location.toPoint(), dimension.toSwing());
    }
    @Override
    public @NotNull Rectangle getShape() {
        return (Rectangle) super.getShape();
    }

    @Override
    public @NotNull RectangleComponent clone() {
        return (RectangleComponent) super.clone();
    }
}
