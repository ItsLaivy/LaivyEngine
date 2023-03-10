package codes.laivy.engine.graphics.components.shape;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (21/01/2023)
 */
public abstract class ShapeComponent extends GameComponent {

    private @NotNull Shape shape;
    private boolean filled;

    public ShapeComponent(@NotNull GamePanel panel, @NotNull Shape shape, boolean filled, @NotNull Location location, int offsetX, int offsetY, int opacity) {
        super(panel, location, offsetX, offsetY, opacity);

        this.shape = shape;
        this.filled = filled;

        this.shape.getBounds().setLocation(location.toPoint());

        // Default dimension
        this.dimension = new Dimension(shape.getBounds().width, shape.getBounds().height);
        //
    }

    @Override
    public void setLocation(@NotNull Location location) {
        super.setLocation(location);
        java.awt.Dimension size = getShape().getBounds().getSize();
        setShape(getShape(location, new Dimension(size.width, size.height)));
    }
    @Override
    @WindowThread
    public void setDimension(@NotNull Dimension dimension) {
        super.setDimension(dimension);
        setShape(getShape(getLocation(), dimension));
    }

    @Contract("_, _ -> new")
    public abstract @NotNull Shape getShape(@NotNull Location location, @NotNull Dimension dimension);

    public boolean isFilled() {
        return filled;
    }
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public @NotNull Shape getShape() {
        return shape;
    }
    public void setShape(@NotNull Shape shape) {
        this.shape = shape;
    }

    @Override
    @Contract("-> new")
    public @NotNull ShapeComponent clone() {
        return (ShapeComponent) super.clone();
    }

}
