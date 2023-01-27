package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.shape.RoundRectangleComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ResponsiveRoundRectangleDisposition extends ResponsiveShapeDisposition {
    public ResponsiveRoundRectangleDisposition(@NotNull RoundRectangleComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    @Contract(pure = true)
    public @NotNull RoundRectangleComponent getComponent() {
        return (RoundRectangleComponent) super.getComponent();
    }

    @Override
    public void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension, @NotNull GameLayoutBounds bounds) {
        renderingGraphics.fill(shape(location, dimension, bounds));
    }

    @Override
    public void shape(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension, @NotNull GameLayoutBounds bounds) {
        renderingGraphics.draw(shape(location, dimension, bounds));
    }

    private @NotNull RoundRectangle2D.Float shape(@NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension, @NotNull GameLayoutBounds bounds) {
        RoundRectangle2D.Float shape = getComponent().getShape(location, dimension);
        shape.archeight = calculateHeightOffset(getComponent().getArc().getHeight(), bounds);
        shape.arcwidth = calculateWidthOffset(getComponent().getArc().getWidth(), bounds);

        return shape;
    }

    @Override
    public void drawBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
        codes.laivy.engine.coordinates.dimension.Dimension temp = coordinates.getClientDimension().clone();
        Location location = coordinates.getClientLocation().clone();

        coordinates.setScreenLocation(location);
        coordinates.setScreenDimension(temp);

        Color color = component.getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);

            RoundRectangle2D.Float shape = getComponent().getShape(location, temp);
            shape.archeight = calculateHeightOffset(getComponent().getArc().getHeight(), bounds);
            shape.arcwidth = calculateWidthOffset(getComponent().getArc().getWidth(), bounds);

            backgroundGraphics.fill(shape);
        }
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords, @NotNull GameLayoutBounds bounds) {
        Dimension dimension = coords.getScreenDimension();

        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - dimension.getWidth());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getScreenLocation().getY() - dimension.getHeight());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - dimension.getWidth());
            coords.getClientLocation().setY(-coords.getScreenLocation().getY() - dimension.getHeight());
        }
    }
}
