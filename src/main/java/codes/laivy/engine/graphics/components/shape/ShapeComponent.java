package codes.laivy.engine.graphics.components.shape;

import codes.laivy.engine.Game;
import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public abstract class ShapeComponent extends GameComponent {

    private Shape shape;
    private boolean filled;

    public ShapeComponent(@NotNull Game game, @NotNull Shape shape, boolean filled, @NotNull Location location) {
        super(game, location);

        this.shape = shape;
        this.filled = filled;

        this.shape.getBounds().setLocation(location.toPoint());

        // Define o valor da dimensão padrão
        Dimension dimension = new Dimension(shape.getBounds().width, shape.getBounds().height);
        dimensions.put(null, dimension);
        // Define o valor da dimensão padrão
    }

    @Override
    public void setLocation(@NotNull Location location) {
        super.setLocation(location);
        setShape(getShape(location, new Dimension(getShape().getBounds().getSize())));
    }
    @Override
    @WindowThread
    public void setDimension(@Nullable GameWindow window, @NotNull Dimension dimension) {
        super.setDimension(window, dimension);
        setShape(getShape(getLocation(), dimension));
    }

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
    public @NotNull ShapeComponent clone() {
        ShapeComponent component = (ShapeComponent) super.clone();

        component.setFilled(component.isFilled());
        component.setShape(component.getShape());

        return component;
    }

}
