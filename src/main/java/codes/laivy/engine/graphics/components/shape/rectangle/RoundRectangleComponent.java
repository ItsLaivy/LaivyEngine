package codes.laivy.engine.graphics.components.shape.rectangle;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Coordinates;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (22/01/2023)
 */
public class RoundRectangleComponent extends ShapeComponent {

    private @NotNull Dimension arc;

    public RoundRectangleComponent(@NotNull GamePanel panel, boolean filled, @NotNull Location location, @NotNull Dimension dimension, @NotNull Dimension arc) {
        this(panel, filled, location, dimension, arc, 0, 0, 100);
    }
    public RoundRectangleComponent(@NotNull GamePanel panel, boolean filled, @NotNull Location location, @NotNull Dimension dimension, @NotNull Dimension arc, int offsetX, int offsetY, int opacity) {
        super(panel, new RoundRectangle2D.Float(location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight(), arc.getWidth(), arc.getWidth()), filled, location, offsetX, offsetY, opacity);
        this.arc = arc;
    }
    @Override
    @Contract("_, _ -> new")
    public @NotNull RoundRectangle2D.Float getShape(@NotNull Location location, @NotNull Dimension dimension) {
        return new RoundRectangle2D.Float(location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight(), getArc().getWidth(), arc.getHeight());
    }

    @Override
    @WindowThread
    public boolean collides(@NotNull Location location) {
        if (isAtScreen()) {
            @NotNull Coordinates sCoords = Objects.requireNonNull(getScreenCoordinates());

            double x = location.getX();
            double y = location.getY();

            double rectX = sCoords.getLocation().getX();
            double rectY = sCoords.getLocation().getY();
            double rrx1 = rectX + sCoords.getDimension().getWidth();
            double rry1 = rectY + sCoords.getDimension().getHeight();

            if (x < rectX || y < rectY || x >= rrx1 || y >= rry1) {
                return false;
            }
            double aw = Math.min(sCoords.getDimension().getWidth(), Math.abs(getArc().getWidth())) / 2.0;
            double ah = Math.min(sCoords.getDimension().getHeight(), Math.abs(getArc().getHeight())) / 2.0;

            if (x >= (rectX += aw) && x < (rectX = rrx1 - aw)) {
                return true;
            }
            if (y >= (rectY += ah) && y < (rectY = rry1 - ah)) {
                return true;
            }

            x = (x - rectX) / aw;
            y = (y - rectY) / ah;

            return (x * x + y * y <= 1.0);
        }
        return false;
    }

    public @NotNull Dimension getArc() {
        return arc;
    }
    public void setArc(@NotNull Dimension arc) {
        this.arc = arc;
    }

    @Override
    public @NotNull RoundRectangle2D.Float getShape() {
        return (RoundRectangle2D.Float) super.getShape();
    }

    @Override
    @Contract("-> new")
    public @NotNull RoundRectangleComponent clone() {
        return (RoundRectangleComponent) super.clone();
    }
}
