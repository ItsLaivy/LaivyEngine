package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class ResponsiveShapeDisposition extends ResponsiveDisposition {
    public ResponsiveShapeDisposition(@NotNull ShapeComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    public abstract void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension);

    public abstract void shape(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension);

    @Override
    public final void draw(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension) {
        shape(renderingGraphics, location, dimension);
        if (getComponent().isFilled()) {
            fill(renderingGraphics, location, dimension);
        }
    }

    @Override
    @Contract(pure = true)
    public @NotNull ShapeComponent getComponent() {
        return (ShapeComponent) super.getComponent();
    }
}
