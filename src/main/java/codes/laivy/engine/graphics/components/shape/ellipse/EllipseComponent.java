package codes.laivy.engine.graphics.components.shape.ellipse;

import codes.laivy.engine.coordinates.Coordinates;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Ellipse2D;
import java.util.Objects;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (22/01/2023)
 */
public class EllipseComponent extends ShapeComponent {

    public EllipseComponent(@NotNull GamePanel panel, boolean filled, @NotNull Location location, @NotNull Dimension dimension) {
        this(panel, filled, location, dimension, 0, 0, 100);
    }
    public EllipseComponent(@NotNull GamePanel panel, boolean filled, @NotNull Location location, @NotNull Dimension dimension, int offsetX, int offsetY, int opacity) {
        super(panel, new Ellipse2D.Float(location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight()), filled, location, offsetX, offsetY, opacity);
    }

    @Override
    @Contract("_, _ -> new")
    public @NotNull Ellipse2D.Float getShape(@NotNull Location location, @NotNull Dimension dimension) {
        return new Ellipse2D.Float(location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight());
    }
    @Override
    public @NotNull Ellipse2D.Float getShape() {
        return (Ellipse2D.Float) super.getShape();
    }

    @Override
    public boolean collides(@NotNull Location location) {
        if (isAtScreen()) {
            @NotNull Coordinates sCoords = Objects.requireNonNull(getScreenCoordinates());

            int centerX = sCoords.getLocation().getX() + (sCoords.getDimension().getWidth() / 2);
            int centerY = sCoords.getLocation().getY() + (sCoords.getDimension().getHeight() / 2);

            double a = sCoords.getDimension().getWidth() / 2D; // Semi eixo maior
            double b = sCoords.getDimension().getHeight() / 2D; // Semi eixo menor

            return ((Math.pow(location.getX() - centerX, 2))/(Math.pow(a, 2)) + (Math.pow(location.getY() - centerY, 2))/(Math.pow(b, 2))) <= 1;
        }
        return false;
    }

    @Override
    @Contract("-> new")
    public @NotNull EllipseComponent clone() {
        return (EllipseComponent) super.clone();
    }
}
