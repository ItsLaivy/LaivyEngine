package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.RectangleComponent;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ResponsiveRectangleDisposition extends ResponsiveShapeDisposition {
    public ResponsiveRectangleDisposition(@NotNull RectangleComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    public void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension, @NotNull GameLayoutBounds bounds) {
        renderingGraphics.fill(getComponent().getShape(location, dimension));
    }

    @Override
    public void shape(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds) {
        graphics.draw(getComponent().getShape(location, dimension));
    }

    @Override
    @Contract(pure = true)
    public @NotNull RectangleComponent getComponent() {
        return (RectangleComponent) super.getComponent();
    }
}
