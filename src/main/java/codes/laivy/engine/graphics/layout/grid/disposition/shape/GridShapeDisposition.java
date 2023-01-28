package codes.laivy.engine.graphics.layout.grid.disposition.shape;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import codes.laivy.engine.graphics.layout.grid.disposition.GridDisposition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public abstract class GridShapeDisposition extends GridDisposition {
    public GridShapeDisposition(@NotNull ShapeComponent component, @NotNull GridColumn column) {
        super(component, column.getRow().getLayout(), column);

        if (!Objects.equals(getComponent().getGamePanel().getEngineLayout(), getLayout())) {
            throw new IllegalArgumentException("This game component's panel layout isn't the same as the disposition layout!");
        }
    }

    public abstract void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds);

    public abstract void shape(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds);

    @Override
    public final void drawObject(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds) {
        shape(renderingGraphics, location, dimension, bounds);
        if (getComponent().isFilled()) {
            fill(renderingGraphics, location, dimension, bounds);
        }
    }

    @Override
    @Contract(pure = true)
    public @NotNull ShapeComponent getComponent() {
        return (ShapeComponent) super.getComponent();
    }
}
