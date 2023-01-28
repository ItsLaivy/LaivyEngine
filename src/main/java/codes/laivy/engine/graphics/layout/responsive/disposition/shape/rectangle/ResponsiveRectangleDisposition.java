package codes.laivy.engine.graphics.layout.responsive.disposition.shape.rectangle;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.rectangle.RectangleComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.shape.ResponsiveShapeDisposition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ResponsiveRectangleDisposition extends ResponsiveShapeDisposition {
    public ResponsiveRectangleDisposition(@NotNull RectangleComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    public void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension, @NotNull GameLayout.Bounds bounds) {
        renderingGraphics.fill(getComponent().getShape(location, dimension));
    }

    @Override
    public void shape(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayout.Bounds bounds) {
        graphics.draw(getComponent().getShape(location, dimension));
    }

    @Override
    @Contract(pure = true)
    public @NotNull RectangleComponent getComponent() {
        return (RectangleComponent) super.getComponent();
    }
}
