package codes.laivy.engine.graphics.layout.responsive.disposition.shape;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.ResponsiveDisposition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class ResponsiveShapeDisposition extends ResponsiveDisposition {
    public ResponsiveShapeDisposition(@NotNull ShapeComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    public abstract void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension, @NotNull GameLayoutBounds bounds);

    public abstract void shape(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension, @NotNull GameLayoutBounds bounds);

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
